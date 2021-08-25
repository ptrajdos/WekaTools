package weka.estimators.density;

import java.util.Arrays;
import java.util.Random;

import junit.framework.TestCase;
import weka.core.OptionHandler;
import weka.core.UtilsPT;
import weka.estimators.density.tools.DensityEstimatorProps;
import weka.estimators.density.tools.ROIFinder;
import weka.tools.Linspace;
import weka.tools.SerialCopier;
import weka.tools.numericIntegration.Function;
import weka.tools.numericIntegration.SimpleIntegrator;
import weka.tools.numericIntegration.SimpsonsIntegrator;
import weka.tools.tests.OptionHandlerChecker;
import weka.tools.tests.WekaGOEChecker;

/**
 * Test class for bounded density estimators
 * @author pawel trajdos
 * @since 0.11.0
 * @version 1.12.1
 *
 */
public abstract class DensEstimatorTest extends TestCase {
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected  int numVals=1000;
	protected  double eps=1e-6;
	protected double integrationEps=1E-6;
	protected  double step=0.01;
	protected int seed=0;
	protected double compareIntegrateEps=1e-2;
	protected double compareEps=1E-6;
	
	protected boolean stricEstimInterval=false;
	
	protected double[] generateUniform() {
		double[] values  = new double[numVals];
		Random rnd = new Random(seed);
		for(int i=0;i<numVals;i++)
			values[i] = rnd.nextDouble();
		return values;
	}
	
	protected double[] generateGauss(double divisor) {
		double[] values  = new double[numVals];
		Random rnd = new Random(seed);
		for(int i=0;i<numVals;i++)
			values[i] = rnd.nextGaussian()/divisor + 0.5;
		return values;
	}
	
	protected double[] generateHomogeneous(double value) {
		double[] values = new double[numVals];
		Arrays.fill(values, value);
		return values;
	}
	
	protected abstract DensityEstimator getEstimator();
	
	protected double getLower() {return 0.0;}
	
	protected double getUpper() {return 1.0;}
	
	public void checkCDF(double[] vals) {
		DensityEstimator dens = this.getEstimator();
		dens.addValues(vals, this.generateHomogeneous(1.0));
		
		double val =0;
		
		val = dens.getCDF(Double.NEGATIVE_INFINITY);
		assertTrue("-Inf", eq(val, 0));
		val = dens.getCDF(Double.POSITIVE_INFINITY);
		assertTrue("+Inf", eqIntegr(val, 1));
		
		if(this.stricEstimInterval) {
			val = dens.getCDF(this.getLower()-eps);
			assertTrue("Strict lower bound", eq(val, 0));
			val = dens.getCDF(this.getUpper()+eps);
			assertTrue("Strict upper bound", eq(val, 1));
		}
		
		
		
		double[] lins = Linspace.genLinspace(getLower(), getUpper(), step);
		double cdfL=0;
		double cdfU=0;
		for(int i=0;i< lins.length-1;i++) {
			cdfL = dens.getCDF(lins[i]);
			cdfU = dens.getCDF(lins[i+1]);
			assertTrue("Finite", Double.isFinite( cdfL ));
			assertTrue("Finite next", Double.isFinite( cdfU ));
			assertTrue("Increasing Property", cdfL<= cdfU );
		}
		
		
	}
	
	protected class Fun implements Function
	{
		
		DensityEstimator densEstim;
		
		public Fun(DensityEstimator densEstim) {
			this.densEstim = densEstim;
		}

		@Override
		public double value(double argument) {
			return this.densEstim.getPDF(argument);
		}
		
	}
	
	public void checkPDF(double[] vals) {
		DensityEstimator dens = this.getEstimator();
		double[] values = this.generateHomogeneous(1.0);
		dens.addValues(vals, values);
		
		double[] getVals = dens.getValues();
		assertTrue("Get Set arrays",UtilsPT.compareDoubleArrays(vals, getVals));
		
		double[] weights = dens.getWeights();
		assertTrue("Get Weights", weights !=null);
		
		
		double val=0;
	
		val = dens.getPDF(Double.NEGATIVE_INFINITY);
		assertTrue("-Inf", eq(val, 0));
		val=dens.getPDF(Double.POSITIVE_INFINITY);
		assertTrue("+Inf", eq(val, 0));
		
		if(this.stricEstimInterval) {
			val = dens.getPDF(this.getLower() - eps);
			assertTrue("Strict lower bound", eq(val, 0));
			val=dens.getPDF(this.getUpper() + eps);
			assertTrue("Strict upper bound", eq(val, 0));
		}
		
		double[] lins = Linspace.genLinspace(getLower(), getUpper(), step);
		double pdf=0;
		for(int i=0;i< lins.length;i++) {
			pdf = dens.getPDF(lins[i]);
			assertFalse("Not NaN", Double.isNaN(pdf));
			assertTrue("Not Inf", Double.isFinite(pdf));
			assertTrue("Greater than zero", pdf >=0);
		}
		
		Fun fun = new Fun(dens);
		SimpleIntegrator trint = new SimpsonsIntegrator();
		trint.setSequenceLength(2000);
		
		double[] roi = ROIFinder.findRoi(dens, getLower(), getUpper(), trint.getSequenceLength());
		trint.setUpperBound(roi[1]+integrationEps);
		trint.setLowerBound(roi[0]-integrationEps);
		trint.setFunction(fun);
		
		
		double integral=0;
		try {
			integral = trint.integrate();
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been thrown");
		}
		assertTrue("Integration over zero",integral>0);
		assertTrue("Integration to one: " + integral, this.eqIntegr(integral, 1.0));
		
		double lowerBound = getUpper()+integrationEps;
		double upperBound = getLower()-integrationEps;
		double expVal;
		try {
			expVal = DensityEstimatorProps.getMoment(dens, lowerBound, upperBound, 1);
			assertTrue("Finite expected value", Double.isFinite(expVal));
		} catch (Exception e) {
			fail("An exception has been caught:" + e.getMessage());
		}
		
		try {
			double var = DensityEstimatorProps.getCentralMoment(dens, lowerBound, upperBound, 2);
			assertTrue("Finite variance", Double.isFinite(var));
		} catch (Exception e) {
			fail("Excaption has been caught:"  + e.getMessage());
		}
		
		
	}
	
	public void testPdfUniform() {
		checkPDF(generateUniform());
	}
	
	public void testPdfGauss() {
		double[] divs= {10,100,1000,10000,1000000,1000000,1E7,1E8};
		for (double d : divs) {
			checkPDF(generateGauss(d));
		}
		
	}
	
	/*
	public void testPfdLower() {
		checkPDF(generateHomogeneous(this.getLower() + eps));
	}*/
	
	/*public void testPdfUpper() {
		checkPDF(generateHomogeneous(this.getUpper() - eps));
	}*/
	
	
	public void testPdfMiddle() {
		checkPDF(generateHomogeneous(0.5*(this.getUpper() + this.getLower()) ));
	}
	
	public void testCdf() {
		checkCDF(generateUniform());
	}
	
	public void testCdfGauss() {
		double[] divs= {10,100,1000,10000,1000000,1000000};
		for (double d : divs) {
			checkCDF(generateGauss(d));
		}
		
	}
	
	public void testSerializable() {
		DensityEstimator densEst = this.getEstimator();
		
		try {
			DensityEstimator estCopy = (DensityEstimator) SerialCopier.makeCopy(densEst);
		} catch (Exception e) {
			fail("An Exception has been caught: " + e.getMessage());
		}
	}
	
/*	public void testCdfLower() {
		checkCDF(generateHomogeneous(this.getLower()));
	}
	*/
	/*public void testCdfUpper() {
		checkCDF(generateHomogeneous(this.getUpper()));
	}
	*/
	
	public void testOptionsIfPresent() {
		DensityEstimator densEst = this.getEstimator();
		if(densEst instanceof OptionHandler) {
			OptionHandlerChecker.checkOptions((OptionHandler) densEst);
		}
	}
	
	public void testTipText() {
		DensityEstimator densEst = this.getEstimator();
		
		 WekaGOEChecker goe = new WekaGOEChecker();
		 goe.setObject(this.getEstimator());
		 goe.checkToolTipsCall();
		
	}
	
	public void testCdfMiddle() {
		checkCDF(generateHomogeneous(0.5*(this.getUpper() + this.getLower()) ));
	}
	
	public void testToString() {
		DensityEstimator dens = this.getEstimator();
		String desc  = dens.toString();
		assertTrue(desc!=null);
	}
	
	public void testAddSingleVals() {
		DensityEstimator dens = this.getEstimator();
		double[] values = this.generateHomogeneous(1.0);
		for (double d : values) {
			dens.addValue(d, 1.0);
		}	
	}
	
	public boolean eqIntegr(double a, double b) {
		double absDiff = Math.abs(a-b);
		if(absDiff>this.compareIntegrateEps)
			return false;
					
		return true;
	}
	
	public boolean eq(double a, double b) {
		double absDiff = Math.abs(a-b);
		if(absDiff>this.compareEps)
			return false;
					
		return true;
	}
	
	

}

package weka.estimators.density;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
import weka.tools.numericIntegration.TrapezoidalIntegrator;
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

	protected  int numVals=100;
	protected  double eps=1e-6;
	protected double integrationEps=1E-6;
	protected  double step=0.01;
	protected int seed=0;
	protected double compareIntegrateEps=1e-2;
	protected double compareEps=1E-6;
	
	protected boolean stricEstimInterval=false;
	
	protected int integrationSequenceLength=5000;
	
	protected double[] generateUniform() {
		double[] values  = new double[this.numVals];
		Random rnd = new Random(this.seed);
		for(int i=0;i<this.numVals;i++)
			values[i] = rnd.nextDouble();
		return values;
	}
	
	protected double[] generateNegUniform() {
		double[] values = this.generateUniform();
		for(int i=0;i<values.length;i++)
			values[i]-=10;
		
		return values;
		
	}
	
	protected double[] generateGauss(double divisor) {
		double[] values  = new double[this.numVals];
		Random rnd = new Random(this.seed);
		for(int i=0;i<this.numVals;i++)
			values[i] = rnd.nextGaussian()/divisor + 0.5;
		return values;
	}
	
	
	protected double[] generateHomogeneous(double value) {
		double[] values = new double[this.numVals];
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
			val = dens.getCDF(this.getLower()-this.eps);
			assertTrue("Strict lower bound", eq(val, 0));
			val = dens.getCDF(this.getUpper()+this.eps);
			assertTrue("Strict upper bound", eq(val, 1));
		}
		
		
		
		double[] lins = Linspace.genLinspace(getLower(), getUpper(), this.step);
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
			val = dens.getPDF(this.getLower() - this.eps);
			assertTrue("Strict lower bound", eq(val, 0));
			val=dens.getPDF(this.getUpper() + this.eps);
			assertTrue("Strict upper bound", eq(val, 0));
		}
		
		double[] lins = Linspace.genLinspace(getLower(), getUpper(), this.step);
		double pdf=0;
		for(int i=0;i< lins.length;i++) {
			pdf = dens.getPDF(lins[i]);
			assertFalse("Not NaN", Double.isNaN(pdf));
			assertTrue("Not Inf", Double.isFinite(pdf));
			assertTrue("Greater than zero", pdf >=0);
		}
		
		Fun fun = new Fun(dens);
		SimpleIntegrator trint = new SimpsonsIntegrator();
		trint.setSequenceLength(this.integrationSequenceLength);
		
		double[] roi = ROIFinder.findRoi(dens, getLower(), getUpper(), trint.getSequenceLength());
		trint.setUpperBound(roi[1]+this.integrationEps);
		trint.setLowerBound(roi[0]-this.integrationEps);
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
		
		double lowerBound = getUpper()+this.integrationEps;
		double upperBound = getLower()-this.integrationEps;
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
	
	public void testPdfSingleValue() {
		double[] vals = new double[]{0.5};
		checkPDF(vals);
	}
	
	public void testCdfSingleValue() {
		double[] vals = new double[]{0.5};
		checkCDF(vals);
	}
	
	public void testPdfTwoValues() {
		double[] vals = new double[]{0.0,1.0};
		checkPDF(vals);
	}
	
	public void testCdfTwoValues() {
		double[] vals = new double[]{0.0,1.0};
		checkCDF(vals);
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
	
	public void testReset() {
		DensityEstimator dens = this.getEstimator();
		double[] weights = this.generateHomogeneous(1.0);
		double[] values = this.generateGauss(1.0);
		dens.addValues(values, weights);
		
		dens.reset();
		
		try {
			double pdfVal = dens.getPDF(0);
			assertTrue("NaN value should have been returned", Double.isNaN(pdfVal));
			
			double cdfVal = dens.getCDF(0.0);
			assertTrue("NaN value should have been returned", Double.isNaN(cdfVal));
		}catch (Exception e) {
			assertTrue("Empty set ", true);
		}
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
	
	double[] createSkewedDistribution() {
		int N = this.numVals;
		double[] values = new double[N];
		Arrays.fill(values, 0.1);
		
		values[N-1] = 1.0;
		values[N-2] = 0.9;
		
		return values;
	}
	
	

}

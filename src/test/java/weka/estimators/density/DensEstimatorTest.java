package weka.estimators.density;

import java.util.Arrays;
import java.util.Random;

import junit.framework.TestCase;
import weka.core.Utils;
import weka.tools.Linspace;
import weka.tools.numericIntegration.Function;
import weka.tools.numericIntegration.SimpsonsIntegrator;

/**
 * Test class for bounded density estimators
 * @author pawel trajdos
 * @since 0.11.0
 * @version 0.13.0
 *
 */
public abstract class DensEstimatorTest extends TestCase {
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Utils.SMALL=1e-2;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		Utils.SMALL=1e-6;
	}

	protected  int numVals=1000;
	protected  double eps=1e-6;
	protected double integrationEps=1E-10;
	protected  double step=0.01;
	protected int seed=0;
	
	protected double[] generateUniform() {
		double[] values  = new double[numVals];
		Random rnd = new Random(seed);
		for(int i=0;i<numVals;i++)
			values[i] = rnd.nextDouble();
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
		
		assertTrue("-Inf", Utils.eq(dens.getCDF(getLower()-eps), 0));
		assertTrue("+Inf", Utils.eq(dens.getCDF(getUpper()+eps), 1));
		
		double[] lins = Linspace.genLinspace(getLower(), getUpper(), step);
		for(int i=0;i< lins.length-1;i++) {
			assertTrue("Finite", Double.isFinite(dens.getCDF(lins[i])));
			assertTrue("Finite", Double.isFinite(dens.getCDF(lins[i+1])));
			assertTrue("Increasing Property", dens.getCDF(lins[i])<= dens.getCDF(i+1) );
		}
		
		
	}
	
	protected class Fun implements Function
	{
		
		DensityEstimator densEstim;
		
		public Fun(DensityEstimator densEstim) {
			this.densEstim = densEstim;
		}

		@Override
		public double getValue(double argument) {
			return this.densEstim.getPDF(argument);
		}
		
	}
	
	public void checkPDF(double[] vals) {
		DensityEstimator dens = this.getEstimator();
		dens.addValues(vals, this.generateHomogeneous(1.0));
		
		assertTrue("-Inf", Utils.eq(dens.getPDF(getLower()-eps), 0));
		assertTrue("+Inf", Utils.eq(dens.getPDF(getUpper()+eps), 0));
		
		double[] lins = Linspace.genLinspace(getLower(), getUpper(), step);
		for(int i=0;i< lins.length;i++) {
			assertFalse("Not NaN", Double.isNaN(dens.getPDF(lins[i])));
			assertTrue("Not Inf", Double.isFinite(dens.getPDF(lins[i])));
			assertTrue("Greater than zero", dens.getPDF(lins[i]) >=0);
		}
		
		Fun fun = new Fun(dens);
		SimpsonsIntegrator trint = new SimpsonsIntegrator();
		trint.setUpperBound(getUpper()+integrationEps);
		trint.setLowerBound(getLower()-integrationEps);
		trint.setFunction(fun);
		trint.setSequenceLength(1000);
		
		double integral = trint.integrate();
		assertTrue("Integration", Utils.eq(integral, 1.0));
		
	}
	
	public void testPdfUniform() {
		checkPDF(generateUniform());
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
	
	public void testCdfLower() {
		checkCDF(generateHomogeneous(this.getLower()));
	}
	
	public void testCdfUpper() {
		checkCDF(generateHomogeneous(this.getUpper()));
	}
	
	public void testCdfMiddle() {
		checkCDF(generateHomogeneous(0.5*(this.getUpper() + this.getLower()) ));
	}

}

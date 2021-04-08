package weka.estimators;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.tools.data.RandomDataGenerator;
import weka.tools.numericIntegration.Function;
import weka.tools.numericIntegration.SimpleIntegrator;
import weka.tools.numericIntegration.SimpsonsIntegrator;

public abstract class MultivariateEstimatorFromInstancesTest extends TestCase {

	public abstract MultivariateEstimatorFromInstances getEstimator();
	
	@Test
	public void testEstimations() {
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances estimData = gen.generateData();
		
		MultivariateEstimatorFromInstances estim = this.getEstimator();
		
		estim.estimate(estimData);
		
		int numInstances = estimData.numInstances();
		for(int i=0;i<numInstances;i++) {
			double estimRsult = estim.density(estimData.get(i));
			double logDens = estim.logDensity(estimData.get(i));
			assertTrue("PDF Value greater than or equal  zero", estimRsult >=0);
		}
	}
	
	@Test
	public void testNoInstances() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumObjects(0);
		Instances data = gen.generateData();
		
		MultivariateEstimatorFromInstances estim = this.getEstimator();
		
		estim.estimate(data);
		
		int numInstances=50;
		gen.setNumObjects(numInstances);
		Instances filledData = gen.generateData();
		
		for(int i=0;i<numInstances;i++) {
			double estimRsult = estim.density(filledData.get(i));
			double logDens = estim.logDensity(filledData.get(i));
			assertTrue("PDF Value greater than or equal  zero", estimRsult >=0);
			assertTrue("Log PDF less or eq zero", logDens<=0);
			
			assertTrue("No instances PDF", Utils.eq(0, estimRsult));
			assertTrue("No instance log PDF", Double.isInfinite(logDens));
		}
		
	}
	
	@Test
	public void testUninitialised() {
		
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumObjects(0);
		Instances data = gen.generateData();
		
		MultivariateEstimatorFromInstances estim = this.getEstimator();
		
		
		int numInstances=50;
		gen.setNumObjects(numInstances);
		Instances filledData = gen.generateData();
		
		for(int i=0;i<numInstances;i++) {
			double estimRsult = estim.density(filledData.get(i));
			double logDens = estim.logDensity(filledData.get(i));
			assertTrue("PDF Value greater than or equal  zero", estimRsult >=0);
			assertTrue("Log PDF less or eq zero", logDens<=0);
			
			assertTrue("No instances PDF", Utils.eq(0, estimRsult));
			assertTrue("No instance log PDF", Double.isInfinite(logDens));
		}
		
	}
	
	@Test
	public void testOneDimEstimations() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNumericAttributes(1);
		gen.setNumDateAttributes(0);
		gen.setNumNominalAttributes(0);
		gen.setNumStringAttributes(0);
		
		final Instances estimData = gen.generateData();
		
		final MultivariateEstimatorFromInstances estim = this.getEstimator();
		
		estim.estimate(estimData);
		
		int numInstances = estimData.numInstances();
		for(int i=0;i<numInstances;i++) {
			double estimRsult = estim.density(estimData.get(i));
			double logDens = estim.logDensity(estimData.get(i));
			assertTrue("PDF Value greater than or equal  zero", estimRsult >=0);
		}
		
		double eps = 1E-6;
		double step =0.01;
		double lower=-1;
		double upper=2;
		
		SimpleIntegrator trint = new SimpsonsIntegrator();
		trint.setLowerBound(lower - eps);
		trint.setUpperBound(upper + eps);
		trint.setDelta(0.00001);
		
		trint.setFunction(new Function() {
			
			@Override
			public double value(double argument) {
				Instance tmpInstance = estimData.get(0);
				double[] dRep = tmpInstance.toDoubleArray();
				dRep[0]=argument;
				tmpInstance = tmpInstance.copy(dRep);
				return estim.density(tmpInstance);
			}
		});
		
		double integral = 0;
		try {
			integral= trint.integrate();
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been caught");
		}
		Utils.SMALL=1e-3;
		assertTrue("Integration to one", Utils.eq(integral, 1.0));
		Utils.SMALL=1e-6;
	}

}

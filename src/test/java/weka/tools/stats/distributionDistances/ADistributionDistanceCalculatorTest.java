package weka.tools.stats.distributionDistances;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;

import weka.core.Utils;
import weka.tools.SerialCopier;

public abstract class ADistributionDistanceCalculatorTest {

	public abstract DistributionDistanceCalculator getDistributionCalculator();
	
	@Test
	public void testSerialzation() {
		DistributionDistanceCalculator calc  =this.getDistributionCalculator();
		try {
			DistributionDistanceCalculator calc2 = (DistributionDistanceCalculator) SerialCopier.makeCopy(calc);
		} catch (Exception e) {
			fail("Serialization has been failed");
		}
	}
	
	@Test
	public void testInvalidDistributions() {
		double[] dist = {0.4,0.6};
		double[][] invDists = {{0.5,0.6},{Double.POSITIVE_INFINITY,0},{Double.NaN,0}};
		DistributionDistanceCalculator calc  =this.getDistributionCalculator();
		
		for(int i=0;i<invDists.length;i++) {
			try {
				calc.calculateDistance(dist, invDists[i]);
				fail("No exception has been caught");
			}catch(Exception e) {}
		}
	}
	
	@Test
	public void testIncompatibleLengths() {
		double[] dist = {0.4,0.6};
		double[] dist2 = {0.4,0.3,0.3};
		DistributionDistanceCalculator calc  =this.getDistributionCalculator();
		try {
			calc.calculateDistance(dist, dist2);
			fail("Incompatible lengths: No exception has been caught");
		}catch(Exception e) {
			//GOOD
		}
	}
	@Test
	public void testZeroDistributions() {
		double[] dist = {0.4,0.6};
		double[] dist2 = {0.0,0.0};
		DistributionDistanceCalculator calc  =this.getDistributionCalculator();
		try {
			calc.calculateDistance(dist, dist2);
			calc.calculateDistance(dist2, dist);
		}catch(Exception e) {
			fail("Distribution distance failed. An exception has been caught: " + e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testDistributions() {
		DistributionDistanceCalculator calc  =this.getDistributionCalculator();
		int[] numClasses = {2,3,10};
		int numReps =10;
		Random rnd = new Random(0);
		
		try {
			for(int i=0;i<numClasses.length;i++) {
				for(int j=0;j<numReps;j++) {
					double[] dist1 = this.generateRandomDistribution(numClasses[i], rnd);
					double[] dist2 = this.generateRandomDistribution(numClasses[i], rnd);
					double dist = calc.calculateDistance(dist1, dist2);
					assertTrue("Distance check", this.checkDistance(dist));
					assertTrue("The same distrib:", Utils.eq(0, calc.calculateDistance(dist1, dist1)));
					assertTrue("The same distrib:", Utils.eq(0, calc.calculateDistance(dist2, dist2)));
					
				}
			}
		}catch(Exception e) {
			fail("An exception has been caught." + e.getMessage());
		}
	}
	
	double[] generateRandomDistribution(int size, Random rnd) {
		double[] distribution = new double[size];
		for(int i=0;i<distribution.length;i++) {
			distribution[i] = rnd.nextDouble();
		}
		
		Utils.normalize(distribution);
		return distribution;
	}
	
	public boolean checkDistance(double dist) {
		
		if(dist<0)
			return false;
		if(!Double.isFinite(dist))
			return false;
		if(Double.isNaN(dist)) 
			return false;
		
		return true;
	}

}

package weka.tools.tests;

import java.util.Random;

import junit.framework.TestCase;
import weka.core.Utils;

/**
 * 
 * @author pawel trajdos 
 * @since 1.3.2
 * @version 1.3.2
 *
 */

public class DistributionCheckerTest extends TestCase {
	
	public void testNull() {
		double[] distr = null;
		assertFalse("Null distribution", DistributionChecker.checkDistribution(distr));
	}
	
	
	public void testDistributions() {
		int[] numClasses= {1,2,3,5,10,100};
		
		for(int i=0;i<numClasses.length;i++) {
			double[] distr = this.generateDistribution(numClasses[i], numClasses[i], true);
			assertTrue("Good distributions", DistributionChecker.checkDistribution(distr));
			assertTrue("Good distributions -- zeros", DistributionChecker.checkDistribution(new double[numClasses[i]]));
		}
		
		for(int i=0;i<numClasses.length;i++) {
			double[] distr = this.generateDistribution(numClasses[i], numClasses[i], false);
			assertFalse("Unnormalised distributions", DistributionChecker.checkDistribution(distr));
		}
		
		assertFalse("Negative values", DistributionChecker.checkDistribution(new double[] {1,1,-1}));
		assertFalse("Greater than zero", DistributionChecker.checkDistribution(new double[] {1.5,1.5,-2}));
		
		assertFalse("Inifinities + ", DistributionChecker.checkDistribution(new double[] {0.1,Double.POSITIVE_INFINITY}));
		assertFalse("Inifinities - ", DistributionChecker.checkDistribution(new double[] {0.1,Double.NEGATIVE_INFINITY}));
		assertFalse("NaNs", DistributionChecker.checkDistribution(new double[] {0.1,Double.NaN}));
		
		
		
	}
	
	public double[] generateDistribution(int numClasses, int seed,boolean normalise) {
		double[] distrib = new double[numClasses];
		
		Random rnd = new Random(seed);
		for(int i=0;i<distrib.length;i++) {
			distrib[i] = rnd.nextDouble();
		}
		
		if(normalise)
			Utils.normalize(distrib);
		
		return distrib;
	}

	

}

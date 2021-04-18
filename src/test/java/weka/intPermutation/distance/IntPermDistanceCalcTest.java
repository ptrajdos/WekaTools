package weka.intPermutation.distance;

import java.io.Serializable;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.Utils;
import weka.intPermutation.IntPermutation;
import weka.tools.SerializationTester;

public abstract class IntPermDistanceCalcTest extends TestCase {
	
	
	protected abstract IntPermDistanceCalc getDistanceCalc();

	@Test
	public void testMinMax() {
		IntPermDistanceCalc calc = this.getDistanceCalc();
		double minDist = calc.getMinDist();
		double maxDist = calc.getMaxDist();
		assertTrue("Min<Max", minDist  < maxDist);
		assertTrue("Min Dist>=0", Utils.grOrEq(minDist, 0));
		assertTrue("Finite max dist", Double.isFinite(maxDist));
	}
	
	@Test
	public void testDistances() {
		IntPermDistanceCalc calc = this.getDistanceCalc();
		double minDist = calc.getMinDist();
		double maxDist = calc.getMaxDist();
		
		int seed=0;
		int[] permLens= {2,3,4,10,20};
		int numReps=10;
		for(int i=0;i<permLens.length;i++) {
			IntPermutation perm1 = new IntPermutation(permLens[i]);
			IntPermutation perm2 = new IntPermutation(permLens[i]);
			for(int j=0;j<numReps;j++) {
				perm2.shufflePermutation(j);
				try {
					double distance = calc.calculateDistance(perm1, perm2);
					assertTrue("distance >= min", Utils.grOrEq(distance, minDist));
					assertTrue("distance <=max", Utils.grOrEq(maxDist, distance));
				} catch (Exception e) {
					fail("An exception has been caught: " + e.getMessage());
				}
			}
		}
		
	}
	
	@Test
	public void testSerialization() {
		IntPermDistanceCalc calc = this.getDistanceCalc();
		if(calc instanceof Serializable) {
			assertTrue("Check Serialization", SerializationTester.checkSerialization(calc));
		}
	}
	
	@Test
	public void testIncompatibleInstances() {
		
		IntPermDistanceCalc calc = this.getDistanceCalc();
		IntPermutation perm1 = new IntPermutation(5);
		IntPermutation perm2 = new IntPermutation(6);
		
		try {
			double distance = calc.calculateDistance(perm1, perm2);
			fail("Distance calculation for the incompatible permutations should have failed.");
		} catch (Exception e) {
			
		}
		
	}

}

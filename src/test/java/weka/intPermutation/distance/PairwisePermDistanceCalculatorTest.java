package weka.intPermutation.distance;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.Utils;
import weka.intPermutation.IntPermutation;
import weka.intPermutation.IntPermutationOperator;


public class PairwisePermDistanceCalculatorTest extends TestCase {

	@Test
	public void test() {
		
		PairwisePermDistanceCalculator distCalc = new PairwisePermDistanceCalculator(new UlamDistance());
		double minDist = distCalc.getMinDist();
		double maxDist  =distCalc.getMaxDist();
		
		int[] lengths= {2,3,4};
		int[] sizes= {10,20};
		
		for(int l=0;l<lengths.length;l++)
			for(int s=0;s<sizes.length;s++) {
				try {
					double avgDist1 = distCalc.calculateAverageDistance(this.permutationsA(lengths[l], sizes[s]));
					double avgDist2 = distCalc.calculateAverageDistance(this.permutationsL(lengths[l], sizes[s]));
					assertTrue("dist >= min", avgDist1>=minDist);
					assertTrue("dist <=max", avgDist1<=maxDist);
					assertTrue("Distance equals", Utils.eq(avgDist1, avgDist2));
				} catch (Exception e) {
					fail("An exception has been caught: " + e.getMessage());
				}
				
			}
		
		
		
	}
	
	protected IntPermutation[] permutationsA(int length, int size) {
		IntPermutation basePerm = new IntPermutation(length);
		IntPermutation[] permutations = new IntPermutation[size];
		permutations[0] = basePerm;
		for(int i=1;i<permutations.length;i++) {
			permutations[i] = new IntPermutation(IntPermutationOperator.shufflePermutation(basePerm.getArray(), i));
		}
		
		
		return permutations;
	}
	
	protected List<IntPermutation> permutationsL(int length, int size) {
		return Arrays.asList(this.permutationsA(length, size));
	}
	

}

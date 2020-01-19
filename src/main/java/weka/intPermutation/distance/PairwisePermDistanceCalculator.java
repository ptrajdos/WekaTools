/**
 * 
 */
package weka.intPermutation.distance;

import java.io.Serializable;
import java.util.List;

import weka.intPermutation.IntPermutation;

/**
 * Calculate pairwise distances for set of permutations
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class PairwisePermDistanceCalculator implements Serializable,IntPermSetDistance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6852468268545173206L;
	
	protected IntPermDistanceCalc distCalc;
	
	/**
	 * 
	 */
	public PairwisePermDistanceCalculator(IntPermDistanceCalc distCalc) {
		this.distCalc = distCalc;
	}

	@Override
	public double calculateAverageDistance(IntPermutation[] permutations)throws Exception {
		double result=0;
		int permNum = permutations.length;
		int pairNum = permNum*(permNum-1)/2;
		for(int i=0;i<permNum-1;i++)
			for(int  j=i+1;j<permNum;j++)
				result+= this.distCalc.calculateDistance(permutations[i], permutations[j]);
		result/=pairNum;
		return result;
	}

	@Override
	public double calculateAverageDistance(List<IntPermutation> permutations) throws Exception {
		int numPerm = permutations.size();
		IntPermutation[] tmpTable = new IntPermutation[numPerm];
		for(int i=0;i<numPerm;i++){
			tmpTable[i] = permutations.get(i);
		}
		return this.calculateAverageDistance(tmpTable);
	}

	@Override
	public double getMaxDist() {
		return this.distCalc.getMaxDist();
	}

	@Override
	public double getMinDist() {
		return this.distCalc.getMinDist();
	}

}

/**
 * 
 */
package weka.intPermutation.distance;

import java.io.Serializable;

import weka.intPermutation.IntPermutation;

/**
 * Hamming distance according to Deza, M Huang, T
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class HammingDistance implements IntPermDistanceCalc, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3298066083924955783L;

	/* (non-Javadoc)
	 * @see mLWrokHorsse.IntPermutation.distance.IntPermDistanceCalc#calculateDistance(mLWrokHorsse.IntPermutation.IntPermutation, mLWrokHorsse.IntPermutation.IntPermutation)
	 */
	@Override
	public double calculateDistance(IntPermutation perm1, IntPermutation perm2) throws Exception {
		boolean areConsistent;
		areConsistent = perm1.isConsistentWith(perm2);
		if(! areConsistent)throw new IllegalArgumentException("Permuatations are incconsistent");
		int[] p1Tab = perm1.getArray();
		int[] p2Tab = perm2.getArray();
		int len = p1Tab.length;
		
		double errCnt=0;
		for(int i=0;i<len;i++){
			if(p1Tab[i] != p2Tab[i]){
				errCnt+=1.0;
			}
		}
		return errCnt/len;
	}

	@Override
	public double getMaxDist() {
		return 1;
	}

	@Override
	public double getMinDist() {
		return 0;
	}

}

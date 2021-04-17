/**
 * 
 */
package weka.intPermutation.distance;

import java.io.Serializable;

import weka.intPermutation.IntPermutation;
import weka.intPermutation.IntPermutationOperator;

/**
 * Deviation distance according to: Sevaux, M Sörensen, K
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0 
 */
public class PositionalDistance implements IntPermDistanceCalc, Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4822620287084617488L;

	/* (non-Javadoc)
	 * @see mLWrokHorsse.IntPermutation.distance.IntPermDistanceCalc#calculateDistance(mLWrokHorsse.IntPermutation.IntPermutation, mLWrokHorsse.IntPermutation.IntPermutation)
	 */
	@Override
	public double calculateDistance(IntPermutation perm1, IntPermutation perm2) throws Exception {
		boolean areConsistent;

		areConsistent = perm1.isConsistentWith(perm2);
		if(! areConsistent)throw new IllegalArgumentException("Permuatations are incconsistent");
		
		int[] invArray1 = this.generateInverseArray(perm1);
		int[] invArray2 = this.generateInverseArray(perm2);
		
		double preDist=0;
		int len = invArray1.length;
		for(int i =0 ;i<len;i++){
			preDist+= Math.abs(invArray1[i] - invArray2[i]);
		}
		int a = (len%2==0)? 2:0;
		int NF = Math.floorDiv(len, 2);
		//double maxValue = (a + NF -1)*NF;
		double maxValue = 0;
		IntPermutation p1 = new IntPermutation(invArray1.length);
		IntPermutation p2 = new IntPermutation(IntPermutationOperator.reversePermutation(p1.getArray()));
		int[] p1A = p1.getArray();
		int[] p2A = p2.getArray();
		for(int i=0;i<invArray1.length;i++)
			maxValue+= Math.abs(p1A[i] - p2A[i]);
			
		
		double dist= preDist/maxValue;
		return dist;
	}
	
	protected int[] generateInverseArray(IntPermutation perm){
		int[] pTable = perm.getArray();
		int[] invArray = new int[pTable.length];
		int tmp=0;
		for(int i=0;i<pTable.length;i++){
			tmp = pTable[i];
			invArray[tmp]=i;
		}
		
		return invArray;
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

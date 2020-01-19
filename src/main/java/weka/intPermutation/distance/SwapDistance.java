/**
 * 
 */
package weka.intPermutation.distance;

import java.io.Serializable;

import weka.intPermutation.IntPermutation;

/**
 *Kendall's Tau, according to Sevaux, M Sörensen, K
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 */
public class SwapDistance implements IntPermDistanceCalc, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2020620189325673739L;

	/* (non-Javadoc)
	 * @see mLWrokHorsse.IntPermutation.distance.IntPermDistanceCalc#calculateDistance(mLWrokHorsse.IntPermutation.IntPermutation, mLWrokHorsse.IntPermutation.IntPermutation)
	 */
	@Override
	public double calculateDistance(IntPermutation perm1, IntPermutation perm2) throws Exception {
		boolean areConsistent;
		areConsistent = perm1.isConsistentWith(perm2);
		if(! areConsistent)throw new IllegalArgumentException("Permuatations are incconsistent");
		
		int p1Nswaps = this.getSwapNum(perm1);
		int p2Nswaps = this.getSwapNum(perm2);
		int len=perm1.getArray().length;
		double maxValue = len*(len-1);
		double dist= (p1Nswaps + p2Nswaps)/maxValue;
		return dist;
	}
	
	/**
	 * Counts the number of swaps that are needed to convert permutation into natural order 1,2,...len
	 * Implemented as bubble sort with swap counting 
	 * @param perm
	 * @return number of swaps
	 */
	protected int getSwapNum(IntPermutation perm){
		int swapNum=0;
		int[] pTable= perm.getArray();
		
		int[] pTableCopy = new int[pTable.length];
		for(int i =0;i<pTable.length;i++){
			pTableCopy[i]=pTable[i];
		}
		int tmpSwaps;
		int tmp;
		for(int i=0;i<pTable.length;i++){
			tmpSwaps=0;
			for(int j=0;j<pTable.length-1;j++){
				if(pTableCopy[j]>pTableCopy[j+1]){
					tmp=pTableCopy[j];
					pTableCopy[j]=pTableCopy[j+1];
					pTableCopy[j+1]=tmp;
					tmpSwaps++;
					swapNum++;
				}
			}
			if(tmpSwaps==0)break;
		}
		
		return swapNum;
	}

	@Override
	public double getMaxDist() {
		return 1.0;
	}

	@Override
	public double getMinDist(){ 
		return 0.0;
	}

}

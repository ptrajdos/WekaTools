/**
 * 
 */
package weka.intPermutation.distance;

import java.io.Serializable;

import weka.intPermutation.IntPermutation;

/**
 * Ulam distance of two permutations
 * 
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class UlamDistance implements IntPermDistanceCalc, Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6697146741187272994L;

	@Override
	public double calculateDistance(IntPermutation perm1, IntPermutation perm2) throws Exception {
		boolean areConsistent;
		areConsistent = perm1.isConsistentWith(perm2);
		if(! areConsistent)throw new IllegalArgumentException("Permuatations are incconsistent");
		
		IntPermutation tmp = perm2.productPermutation(perm1.getInversePermutation());
		//find the length of the longest increasing sequence in tmp
		int longestIncSeqLen = this.longestIncreasingSequence(tmp).length;
		double maxValue=perm1.getArray().length ;
		double result;
		result = (maxValue - longestIncSeqLen)/maxValue;
		
		return result;
	}
	
	private int[] longestIncreasingSequence(IntPermutation perm){
		int[] longestIncSeq = null;
		int[] X = perm.getArray();
		int pSeqLen = X.length;
		
		int[] P = new int[pSeqLen];
		int[] M = new int[pSeqLen+1];
		int L=0;
		int hi,lo,mid,newL;
		for(int i=0;i<pSeqLen;i++){
			lo=1;
			hi=L;
			while(lo<=hi){
				mid = (int) Math.ceil((hi+lo)/2.0);
				if(X[M[mid]]<X[i]){
					lo=mid+1;
				}else{
					hi=mid-1;
				}
			}
			newL=lo;
			
			P[i]= M[newL-1];
			M[newL]=i;
			
			if(newL>L){
				L=newL;
			}
		}
		longestIncSeq = new int[L];
		int k=M[L];
		for(int i=L-1;i>=0;i--){
			longestIncSeq[i]=X[k];
			k=P[k];
		}
		
		return longestIncSeq;
	}

	@Override
	public double getMaxDist() {
		return 1.0;
	}

	@Override
	public double getMinDist() {
		return 0;
	}

}

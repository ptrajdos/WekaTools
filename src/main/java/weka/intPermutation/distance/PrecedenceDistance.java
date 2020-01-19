/**
 * 
 */
package weka.intPermutation.distance;

import java.io.Serializable;

import weka.intPermutation.IntPermutation;

/**
 * Precedence distance
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class PrecedenceDistance implements IntPermDistanceCalc, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7374253828903082072L;

	/* (non-Javadoc)
	 * @see mLWrokHorsse.IntPermutation.distance.IntPermDistanceCalc#calculateDistance(mLWrokHorsse.IntPermutation.IntPermutation, mLWrokHorsse.IntPermutation.IntPermutation)
	 */
	@Override
	public double calculateDistance(IntPermutation perm1, IntPermutation perm2) throws Exception {
		boolean areConsistent;
		areConsistent = perm1.isConsistentWith(perm2);
		if(! areConsistent)throw new IllegalArgumentException("Permuatations are incconsistent");
		
		int[][] precMatrix1 = this.generatePrecedenceMatrix(perm1);
		int[][] precMatrix2 = this.generatePrecedenceMatrix(perm2);
		
		int len =perm1.getArray().length;
		double nprec = 0.0;
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				if(i!=j)
					if( precMatrix1[i][j]==1 && precMatrix2[i][j]==1){
						nprec+=1.0;
					}
			}
		}
		
		double maxVal = len*(len-1)/2.0;
		double dist = (maxVal-nprec)/maxVal;
		return dist;
	}
	
	protected int[][] generatePrecedenceMatrix(IntPermutation perm){
		int[] pTab = perm.getArray();
		int[][] precMatrix = new int[pTab.length][pTab.length];
		
		for(int i=0;i<pTab.length-1;i++)
			for(int j=i+1;j<pTab.length;j++){
				precMatrix[pTab[i]][pTab[j]]=1;
			}
		
		return precMatrix;
	}

	@Override
	public double getMaxDist() {
		return 1.0;
	}

	@Override
	public double getMinDist() {
		return 0.0;
	}

}

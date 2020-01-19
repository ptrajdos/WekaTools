/**
 * 
 */
package weka.intPermutation.distance;

import java.io.Serializable;

import weka.intPermutation.IntPermutation;

/**
 * The R distance, according to Sevaux, M Sörensen, K
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 * 
 */
public class AdjacencyDistance implements IntPermDistanceCalc, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4357013726797573177L;

	/* (non-Javadoc)
	 * @see mLWrokHorsse.IntPermutation.distance.IntPermDistanceCalc#calculateDistance(mLWrokHorsse.IntPermutation.IntPermutation, mLWrokHorsse.IntPermutation.IntPermutation)
	 */
	@Override
	public double calculateDistance(IntPermutation perm1, IntPermutation perm2) throws Exception {
		boolean areConsistent;
		areConsistent = perm1.isConsistentWith(perm2);
		if(! areConsistent)throw new IllegalArgumentException("Permuatations are incconsistent");
		
		int[][] adjMatrix1 = this.generateAdjacencyMatrix(perm1);
		int[][] adjMatrix2 = this.generateAdjacencyMatrix(perm2);
		
		
		int len = perm1.getArray().length;
		double nadj=0.0;
		
		for(int i=0;i<len;i++)
			for(int j=0;j<len;j++)
				if(i!=j)
					if(adjMatrix1[i][j]==1 && adjMatrix2[i][j]==1)
						nadj+=1.0;
			
		double maxVal = len-1;
		double dist = (maxVal-nadj)/maxVal;
		return dist;
	}
	
	protected int[][] generateAdjacencyMatrix(IntPermutation perm){
		int[] pTab= perm.getArray();
		
		int[][] adjMatrix = new int[pTab.length][pTab.length];
		for(int i=0;i<pTab.length-1;i++){
			adjMatrix[pTab[i]][pTab[i+1]]=1;
		}
			
			
		
		return adjMatrix;
		
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

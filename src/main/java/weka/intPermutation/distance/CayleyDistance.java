/**
 * 
 */
package weka.intPermutation.distance;

import java.io.Serializable;

import weka.intPermutation.IntPermutation;

/**
 * 
 *Cayley distane according to Deza, M Huang, T
 *For the reverse permuattion the distance is:
 *	(N*ceil(N/2))(N-1)
 *@author pawel trajdos
 *@since 1.1.0
 *@version 1.1.0
 */
public class CayleyDistance implements IntPermDistanceCalc, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2779821804474197715L;

	@Override
	public double calculateDistance(IntPermutation perm1, IntPermutation perm2) throws Exception {
		boolean areConsistent;
		areConsistent = perm1.isConsistentWith(perm2);
		if(! areConsistent)throw new IllegalArgumentException("Permuatations are incconsistent");
		
		IntPermutation tmp = perm2.productPermutation(perm1.getInversePermutation());
		int nCycles = tmp.findCycles().size();
		double maxValue = perm1.getArray().length-1;
		double result = (maxValue - nCycles +1.0)/maxValue;
		return result;
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

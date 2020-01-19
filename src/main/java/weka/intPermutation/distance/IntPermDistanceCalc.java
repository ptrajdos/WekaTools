/**
 * 
 */
package weka.intPermutation.distance;

import weka.intPermutation.IntPermutation;

/**
 * Interface that allows to calculate distance between two compatible permutations
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public interface IntPermDistanceCalc {
	/**
	 * Calculates normalised distance between two permutations perm1 and perm2
	 * @param perm1
	 * @param perm2
	 * @return notmalised distance
	 * @throws Exception
	 */
	double calculateDistance(IntPermutation perm1, IntPermutation perm2)throws Exception;
	
	double getMaxDist();
	
	double getMinDist();
	
}

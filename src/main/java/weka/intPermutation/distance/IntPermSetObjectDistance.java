/**
 * 
 */
package weka.intPermutation.distance;

import java.util.Collection;

import weka.intPermutation.IntPermutation;

/**
 * Calculates average distances for set of permutations
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 */
public interface IntPermSetObjectDistance {
	/**
	 * Calculates average permutation distance between the object and set 
	 * @param obj
	 * @param set
	 * @return
	 * @throws Exception
	 */
	public double calculateAverageDistance(IntPermutation obj, IntPermutation[] set)throws Exception;
	
	/**
	 * Calculates average permutation distance between the object and set
	 * @param obj
	 * @param set
	 * @return
	 * @throws Exception
	 */
	public double calculateAverageDistance(IntPermutation obj, Collection<IntPermutation> set)throws Exception;
	
	/**
	 * Returns maximum value of distance
	 * @return
	 */
	public double getMaxValue();
	/**
	 * Returns minimum value of distance
	 * @return
	 */
	public double getMinValue();

}

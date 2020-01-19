/**
 * 
 */
package weka.intPermutation.distance;

import java.util.List;

import weka.intPermutation.IntPermutation;

/**
 * Interface for an object that calculates average distance between set of permutations
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public interface IntPermSetDistance {
	
	/**
	 * Calculates average distance of set of permutations
	 * @param permutations
	 * @return
	 */
	public double calculateAverageDistance(IntPermutation[] permutations)throws Exception;
	
	public double calculateAverageDistance(List<IntPermutation> permutations)throws Exception;
	
	public double getMaxDist();
	
	public double getMinDist();

}

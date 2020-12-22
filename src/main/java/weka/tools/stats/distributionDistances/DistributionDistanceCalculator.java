/**
 * 
 */
package weka.tools.stats.distributionDistances;

/**
 * @author pawel trajdos
 * @since 1.8.0
 * @version 1.8.0
 * 
 * Interface responsible for calculating the distance between two discrete distributions defined on the class set.
 *
 */
public interface DistributionDistanceCalculator {

	/**
	 * Calculate the distance between two distributions
	 * @param dist1
	 * @param dist2
	 * @return
	 */
	public double calculateDistance(double[] dist1, double[] dist2)throws IllegalArgumentException;
}

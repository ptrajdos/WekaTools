/**
 * 
 */
package weka.tools.stats.distributionDistances;

import java.io.Serializable;

import weka.tools.tests.DistributionChecker;

/**
 * @author pawel trajdos
 * @since 1.8.0
 * @version 1.8.0
 *
 */
public abstract class ADistributionDistanceCalculator implements DistributionDistanceCalculator, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5100373973113721107L;
	


	@Override
	public double calculateDistance(double[] dist1, double[] dist2) throws IllegalArgumentException {
		if(! (DistributionChecker.checkDistribution(dist1) & DistributionChecker.checkDistribution(dist2))) {
			throw new IllegalArgumentException("One distribution is illegal");
		}
		if(dist1.length != dist2.length) {
			throw new IllegalArgumentException("One distribution length differs");
		}
		return this.calculateDistanceInternal(dist1, dist2);
	}
	
	protected abstract double calculateDistanceInternal(double[] dist1, double[] dist2);

}

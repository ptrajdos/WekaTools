/**
 * 
 */
package weka.estimators;

import weka.core.Instance;
import weka.core.Instances;

/**
 * @author pawel trajdos
 * @version 1.9.0
 * @since 1.9.0
 *
 */
public interface MultivariateEstimatorFromInstances {
	
	/**
	 * Initialize the estimator using given instances
	 * @param instances
	 */
	public void estimate(Instances instances);
	
	/**
	 * Get the probability density function value for given instance
	 * @param instance
	 * @return
	 */
	public double density(Instance instance);
	/**
	 * Get the logarithm of the probability density function value for given instance
	 * @param instance
	 * @return
	 */
	public double logDensity(Instance instance);

}

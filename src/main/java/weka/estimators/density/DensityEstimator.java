/**
 * 
 */
package weka.estimators.density;

import weka.estimators.IncrementalEstimator;

/**
 * An interface for PDF/CDF estimators
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
 *
 */
public interface DensityEstimator extends IncrementalEstimator {
	
	/**
	 * Gets Probability Density Function for given x
	 * @param x
	 * @return PDF for x
	 * @author pawel trajdos
	 * @since 0.9.0
	 * @version 0.9.0
	 */
	public double getPDF(double x);
	
	/**
	 * Gets Cumulative Density Function for given x
	 * @param x
	 * @return PDF for x
	 * @author pawel trajdos
	 * @since 0.9.0
	 * @version 0.9.0
	 */
	public double getCDF(double x);
	
	/**
	 * Adds a set ov values into the estimator
	 * @param data
	 * @param weight
	 * @author pawel trajdos
	 * @since 0.9.0
	 * @version 0.9.0
	 */
	public void addValues(double[] data, double[] weight);
	
	

}

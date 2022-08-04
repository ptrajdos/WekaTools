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
	
	/**
	 * Get values added to the estimator
	 * @return values
	 * 
	 * @author pawel trajdos
	 * @since 0.13.0
	 * @version 0.13.0
	 * 
	 */
	public double[] getValues();
	
	/**
	 * Get weights of the stored values/samples
	 * @return weights
	 * 
	 * @author pawel trajdos
	 * @since 0.13.0
	 * @version 0.13.0
	 */
	public double[] getWeights();
	
	/**
	 * Resets the estimator
	 * @since 2.0.0
	 * @version 2.0.0
	 */
	public void reset();
	
	

}

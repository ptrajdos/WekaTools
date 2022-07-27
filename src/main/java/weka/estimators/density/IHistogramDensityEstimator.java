/**
 * 
 */
package weka.estimators.density;

import java.util.List;

import weka.estimators.density.histogram.bin.IBin;

/**
 * 
 * Separate interface for HistogramDensityEstimator
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 * 
 *
 */
public interface IHistogramDensityEstimator extends DensityEstimator {
	
	public List<IBin> getBins();


}

/**
 * 
 */
package weka.estimators.density.histogram;

import weka.tools.WeightedValuesHolder;

/**
 * An interface for objects that calculate bin width for the histogram esitmator
 * @author pawel trajdos
 * @version 1.12.0
 * @since 1.12.0
 *
 */
public interface HistogramBinWidthCalculator {
	
	public double getWidth(WeightedValuesHolder valHolder);

}

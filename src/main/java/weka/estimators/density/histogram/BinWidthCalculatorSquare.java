/**
 * 
 */
package weka.estimators.density.histogram;

import java.io.Serializable;

import weka.core.Utils;
import weka.tools.WeightedValuesHolder;

/**
 * The bin width is calculated using the rule of thumb (Number of bins is proportional to the square root of sample size)
 * @author pawel trajdos
 * @since 1.12.0
 * @version 1.12.0
 *
 */
public class BinWidthCalculatorSquare implements Serializable, HistogramBinWidthCalculator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6588801106384016587L;

	@Override
	public double getWidth(WeightedValuesHolder valHolder) {
		double[] values = valHolder.getValues();
		
		double numBins = Math.ceil(Math.sqrt(values.length));
		
		double minVal = values[Utils.minIndex(values)];
		double maxVal = values[Utils.maxIndex(values)];
		double range = maxVal-minVal;
		double binWidth = range/numBins;
		return binWidth;
	}


}

/**
 * 
 */
package weka.estimators.density.histogram;

import java.io.Serializable;

import weka.estimators.density.IHistogramDensityEstimator;

/**
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */
public class BinWidthCalculatorPredefined implements HistogramBinWidthCalculator, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5345902327489097264L;
	
	private double predefinedWidth;
	/**
	 * 
	 */
	public BinWidthCalculatorPredefined(double predefinedWidth) {
		this.predefinedWidth = predefinedWidth;
	}

	@Override
	public double getWidth(IHistogramDensityEstimator histEstim) {
		return this.predefinedWidth;
	}

}

package weka.estimators.density.histogram;

import java.io.Serializable;

import weka.core.UtilsPT;
import weka.tools.WeightedValuesHolder;

public class BinWidthCalculatorFreedmanDiaconis implements HistogramBinWidthCalculator, Serializable {

	@Override
	public double getWidth(WeightedValuesHolder valHolder) {
		double[] values = valHolder.getValues();
		double iqr = UtilsPT.quantile(values, 0.75) - UtilsPT.quantile(values, 0.25);
		
		double binWidth = 2.0*iqr/ Math.pow(values.length, 1.0/3.0); 
		return binWidth;
	}

	

}

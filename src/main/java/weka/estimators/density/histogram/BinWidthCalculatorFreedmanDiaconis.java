package weka.estimators.density.histogram;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;
import weka.estimators.density.IHistogramDensityEstimator;
import weka.tools.WeightedValuesHolder;

public class BinWidthCalculatorFreedmanDiaconis implements HistogramBinWidthCalculator, Serializable, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7803905239754175325L;
	
	private double minBinWidth = 1E-5;

	@Override
	public double getWidth(IHistogramDensityEstimator histEstim) {
		double[] values = histEstim.getValues();
		double iqr = UtilsPT.quantile(values, 0.75) - UtilsPT.quantile(values, 0.25);
		
		if(iqr < 1E-9) {
			iqr  = UtilsPT.stdDev(values);
		}
		
		double binWidth = 2.0*iqr/ Math.pow(values.length, 1.0/3.0);
		if(binWidth<this.minBinWidth)
			binWidth=this.minBinWidth;
		return binWidth;
	}

	/**
	 * @return the minBinWidth
	 */
	public double getMinBinWidth() {
		return this.minBinWidth;
	}

	/**
	 * @param minBinWidth the minBinWidth to set
	 */
	public void setMinBinWidth(double minBinWidth) {
		this.minBinWidth = minBinWidth;
	}

	public String minBinWidthTipText() {
		return "Min value of the bin width";
	}

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tMin range of values to produce bins "+
		          "(default:" +1E-4 + ".\n",
			      "MBW", 1, "-MBW"));
    
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setMinBinWidth(UtilsPT.parseDoubleOption(options, "MBW", 1E-4));
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-MBW");
		options.add(""+ this.getMinBinWidth() );
		
	    return options.toArray(new String[0]);
	}

}

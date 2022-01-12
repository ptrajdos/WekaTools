/**
 * 
 */
package weka.estimators.density.histogram;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.core.UtilsPT;
import weka.tools.WeightedValuesHolder;

/**
 * The bin width is calculated using the rule of thumb (Number of bins is proportional to the square root of sample size)
 * @author pawel trajdos
 * @since 1.12.0
 * @version 1.12.0
 *
 */
public class BinWidthCalculatorSquare implements Serializable, HistogramBinWidthCalculator, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6588801106384016587L;
	
	private double minBinWidth =1E-5;

	@Override
	public double getWidth(WeightedValuesHolder valHolder) {
		double[] values = valHolder.getValues();
		
		double numBins = Math.ceil(Math.sqrt(values.length));
		
		double minVal = values[Utils.minIndex(values)];
		double maxVal = values[Utils.maxIndex(values)];
		double range = maxVal-minVal;
		double binWidth = range/numBins;
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

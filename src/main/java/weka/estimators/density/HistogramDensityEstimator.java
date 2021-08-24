/**
 * 
 */
package weka.estimators.density;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.core.UtilsPT;
import weka.estimators.density.histogram.BinWidthCalculatorFreedmanDiaconis;
import weka.estimators.density.histogram.HistogramBinWidthCalculator;
import weka.tools.WeightedValuesHolder;

/**
 * @author pawel trajdos
 * @version 1.12.0
 * @since 1.12.0
 *
 */
public class HistogramDensityEstimator implements DensityEstimator, Serializable, OptionHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7158495703357808781L;
	
	protected WeightedValuesHolder weightedValHolder = new WeightedValuesHolder();
	/**
	 * Overall count of values
	 */
	protected double overallSum;
	
	/**
	 * Bin-specific bin count
	 */
	protected double[] binCounts;
	
	protected double minVal;
	
	protected double maxVal;
	
	protected double binWidth;
	
	private double minBinWidth=1E-4;
	
	protected boolean histInitialized=false;
	
	protected HistogramBinWidthCalculator binWidthCalculator = new BinWidthCalculatorFreedmanDiaconis();
	


	@Override
	public void addValue(double data, double weight) {
		this.weightedValHolder.addValue(data, weight);
		this.histInitialized=false;

	}

	@Override
	public double getPDF(double x) {
		this.computeHist();
		if(x<this.minVal || x>this.maxVal)
			return 0;
		
		int binIndex = this.findBin(x);
		double pdf = this.binCounts[binIndex]/(this.overallSum*this.binWidth);
		return pdf;
	}

	@Override
	public double getCDF(double x) {
		this.computeHist();
		if(x<this.minVal)
			return 0;
		
		if(x>this.maxVal)
			return 1;
		
		int binIndex = this.findBin(x);
		double binSum=0;
		for(int i=0;i<=binIndex;i++)
			binSum+=this.binCounts[i];
		double cdf = binSum/(this.overallSum);
		return cdf;
	}

	@Override
	public void addValues(double[] data, double[] weight) {
		this.weightedValHolder.addValues(data, weight);
		this.histInitialized=false;

	}

	@Override
	public double[] getValues() {
		return this.weightedValHolder.getValues();
	}

	@Override
	public double[] getWeights() {
		return this.weightedValHolder.getWeights();
	}
	
	
	
	protected void computeHist() {
		
		if(this.histInitialized)
			return;
		
		this.initializeBins();
		
		double[] vals = this.getValues();
		double[] weights = this.getWeights();
		this.overallSum=0.0;
		
		int binIndex=0;
		for(int i=0;i<vals.length;i++) {
			binIndex = this.findBin(vals[i]);
			this.binCounts[binIndex]+=weights[i];
			this.overallSum+=weights[i];
		}
		this.histInitialized=true;
	}
	
	
	protected void initializeBins() {
		int nVals = this.weightedValHolder.getNumVals();
		int nBins = (int) Math.ceil(Math.sqrt(nVals));
		
		double[] vals = this.getValues();
		
		this.minVal = vals[Utils.minIndex(vals)];
		this.maxVal = vals[Utils.maxIndex(vals)];
		double range = (this.maxVal-this.minVal);
		
		if(range < this.minBinWidth) {
			this.binWidth = this.minBinWidth;
			this.minVal-=this.minBinWidth/2;
			this.maxVal+=this.minBinWidth/2;
		}else {
			this.binWidth = this.binWidthCalculator.getWidth(this.weightedValHolder);
			if(this.binWidth < this.minBinWidth)
				this.binWidth = this.minBinWidth;
		}
		
		nBins = (int) Math.max(Math.ceil(range/this.binWidth), 1) ;
		
		this.binCounts = new double[nBins];
		
	}
	
	protected int findBin(double value) {
		int index=0;
		int numBins = this.binCounts.length;
		double thrVal = this.minVal + this.binWidth;
		for(int i=0;i<numBins;i++) {
			if(value < thrVal) {
				index = i;
				break;
			}
			thrVal+=this.binWidth;
		}
		return index;
	}
	
	

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tMin bin width to use "+
		          "(default:" +1E-4 + ".\n",
			      "MBW", 1, "-MBW"));
		
		newVector.addElement(new Option(
			      "\tObject used to calculate the bin width "+
		          "(default:weka.estimators.density.histogram.BinWidthCalculatorFreedmanDiaconis"
		          + " .\n",
			      "BWC", 1, "-BWC"));
		
	    
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setBinWidthCalculator((HistogramBinWidthCalculator) UtilsPT.parseObjectOptions(options, "BWC", new BinWidthCalculatorFreedmanDiaconis(), HistogramBinWidthCalculator.class));
		
		this.setMinBinWidth(UtilsPT.parseDoubleOption(options, "MBW", 1E-4));
	
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-MBW");
		options.add(""+ this.getMinBinWidth());
		
		options.add("-BWC");
		options.add(UtilsPT.getClassAndOptions(this.getBinWidthCalculator()));
		
	    return options.toArray(new String[0]);
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
		return "Min width of bin that can be used";
	}

	/**
	 * @return the binWidthCalculator
	 */
	public HistogramBinWidthCalculator getBinWidthCalculator() {
		return this.binWidthCalculator;
	}

	/**
	 * @param binWidthCalculator the binWidthCalculator to set
	 */
	public void setBinWidthCalculator(HistogramBinWidthCalculator binWidthCalculator) {
		this.binWidthCalculator = binWidthCalculator;
	}
	
	public String binWidthCalculatorTipText() {
		return "Set object used to calculate the bin width";
	}

}

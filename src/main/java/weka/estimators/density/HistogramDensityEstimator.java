/**
 * 
 */
package weka.estimators.density;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.core.UtilsPT;
import weka.estimators.density.histogram.BinWidthCalculatorFreedmanDiaconis;
import weka.estimators.density.histogram.HistogramBinWidthCalculator;
import weka.estimators.density.histogram.bin.Bin;
import weka.estimators.density.histogram.bin.IBin;
import weka.tools.SerialCopier;
import weka.tools.WeightedValuesHolder;

/**
 * @author pawel trajdos
 * @version 2.0.0
 * @since 1.12.0
 *
 */
public class HistogramDensityEstimator implements IHistogramDensityEstimator, Serializable, OptionHandler {
	
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
	
	protected List<IBin> bins;
	
	protected double minVal;
	
	protected double maxVal;
	
	protected double binWidth;
	
	private double minRange=1E-4;
	
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
		IBin selBin = this.bins.get(binIndex);
		double pdf = selBin.getCount()/(this.overallSum*selBin.getWidth());
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
			binSum+=this.bins.get(i).getCount();
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
			
			this.bins.get(binIndex).addValue(weights[i]);
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
		
		if(range < this.minRange) {
			this.binWidth = this.minRange;
			this.minVal-=this.minRange/2;
			this.maxVal+=this.minRange/2;
		}else {
			this.binWidth = this.binWidthCalculator.getWidth(this);
		}
		
		nBins = (int) Math.max(Math.ceil(range/this.binWidth), 1) ;
		double actualMaxVal = this.minVal + this.binWidth*nBins;
		double maxDiff = actualMaxVal - this.maxVal;
		this.minVal-=maxDiff/2;
		this.maxVal+=maxDiff/2;
		
		
		this.binCounts = new double[nBins];
		
		double lowerBound=this.minVal;
		double upperBound = lowerBound + this.binWidth;
		
		this.bins = new ArrayList<IBin>(nBins);
		
		for(int i = 0; i< nBins;i++) {
			this.bins.add(new Bin(lowerBound, upperBound));
			lowerBound+= this.binWidth;
			upperBound+= this.binWidth;
		}
		
	}
	
	protected int findBin(double value) {
		int index=0;
		int numBins = this.bins.size();
		
		if(value < this.bins.get(0).getLowerBound())
			return 0;
		
		for(int i=0;i<numBins;i++) {
			index = i;
			if( this.bins.get(i).isValueInBin(value) ) {
				break;
			}
			
		}
		return index;
	}
	
	

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tMin range of values to produce bins "+
		          "(default:" +1E-4 + ".\n",
			      "MRA", 1, "-MRA"));
		
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
		
		this.setMinRange(UtilsPT.parseDoubleOption(options, "MRA", 1E-4));
	
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-MRA");
		options.add(""+ this.getMinRange());
		
		options.add("-BWC");
		options.add(UtilsPT.getClassAndOptions(this.getBinWidthCalculator()));
		
	    return options.toArray(new String[0]);
	}

	/**
	 * @return the minBinWidth
	 */
	public double getMinRange() {
		return this.minRange;
	}

	/**
	 * @param minRange the minBinWidth to set
	 */
	public void setMinRange(double minRange) {
		this.minRange = minRange;
	}
	
	public String minRangeTipText() {
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

	@Override
	public List<IBin> getBins() {
		try {
			this.computeHist();
			return (List<IBin>) SerialCopier.makeCopy(this.bins);
		} catch (Exception e) {
			return null;
		}
	}

}

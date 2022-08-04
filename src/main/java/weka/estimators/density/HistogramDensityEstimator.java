/**
 * 
 */
package weka.estimators.density;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.Utils;
import weka.core.UtilsPT;
import weka.estimators.density.histogram.BinWidthCalculatorFreedmanDiaconis;
import weka.estimators.density.histogram.HistogramBinWidthCalculator;
import weka.estimators.density.histogram.bin.Bin;
import weka.estimators.density.histogram.bin.IBin;

/**
 * @author pawel trajdos
 * @version 2.0.0
 * @since 1.12.0
 *
 */
public class HistogramDensityEstimator extends AHistogramDensityEstimator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7158495703357808781L;
	
	
	protected HistogramBinWidthCalculator binWidthCalculator = new BinWidthCalculatorFreedmanDiaconis();


	private double binWidth;
	
	
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
		
		
		double lowerBound=this.minVal;
		double upperBound = lowerBound + this.binWidth;
		
		this.bins = new ArrayList<IBin>(nBins);
		
		for(int i = 0; i< nBins;i++) {
			this.bins.add(new Bin(lowerBound, upperBound));
			lowerBound+= this.binWidth;
			upperBound+= this.binWidth;
		}
		
	}
	
		

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
	
		
		newVector.addElement(new Option(
			      "\tObject used to calculate the bin width "+
		          "(default:weka.estimators.density.histogram.BinWidthCalculatorFreedmanDiaconis"
		          + " .\n",
			      "BWC", 1, "-BWC"));
		
		newVector.addAll(Collections.list(super.listOptions()));
		
	    
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setBinWidthCalculator((HistogramBinWidthCalculator) UtilsPT.parseObjectOptions(options, "BWC", new BinWidthCalculatorFreedmanDiaconis(), HistogramBinWidthCalculator.class));
		super.setOptions(options);
	
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		
		
		options.add("-BWC");
		options.add(UtilsPT.getClassAndOptions(this.getBinWidthCalculator()));
		
		Collections.addAll(options, super.getOptions());
		
	    return options.toArray(new String[0]);
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

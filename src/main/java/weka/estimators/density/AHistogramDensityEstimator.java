/**
 * 
 */
package weka.estimators.density;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import weka.core.KhanKleinSummator;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;
import weka.estimators.density.histogram.bin.IBin;
import weka.tools.SerialCopier;
import weka.tools.WeightedValuesHolder;

/**
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */
public abstract class AHistogramDensityEstimator implements IHistogramDensityEstimator, Serializable, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5591211580917926610L;
	
	protected WeightedValuesHolder weightedValHolder = new WeightedValuesHolder();
	/**
	 * Overall count of values
	 */
	protected double overallSum;
		
	protected List<IBin> bins;
	
	protected double minVal;
	
	protected double maxVal;
	
	
	protected double minRange=1E-4;
	
	protected boolean histInitialized=false;
	

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
		
		KhanKleinSummator binSumK = new KhanKleinSummator();
		
		for(int i=0;i<binIndex;i++)
			binSumK.addToSum(this.bins.get(i).getCount());
		
		IBin selectedBin = this.bins.get(binIndex);
		
		binSumK.addToSum((x -  selectedBin.getLowerBound())* this.getPDF(x));
		
		
		double cdf = binSumK.getSum()/(this.overallSum);
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
	
	
	protected abstract void initializeBins();
	
	protected int findBin(double value) {
		int index=0;
		int numBins = this.bins.size();
		
		if(value <= this.bins.get(0).getLowerBound())
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
		
	    
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setMinRange(UtilsPT.parseDoubleOption(options, "MRA", 1E-4));
	
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-MRA");
		options.add(""+ this.getMinRange());
		
	    return options.toArray(new String[0]);
	}

	/**
	 * @return the min range
	 */
	public double getMinRange() {
		return this.minRange;
	}

	/**
	 * @param minRange the min range to set
	 */
	public void setMinRange(double minRange) {
		this.minRange = minRange;
	}
	
	public String minRangeTipText() {
		return "Min width of bin that can be used";
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

	@Override
	public void reset() {
		this.weightedValHolder.reset();
		this.histInitialized = false;
	}
	
	


}

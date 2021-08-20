/**
 * 
 */
package weka.estimators.density;

import java.io.Serializable;

import weka.core.Utils;
import weka.core.UtilsPT;
import weka.tools.WeightedValuesHolder;

/**
 * @author pawel trajdos
 * @version 1.12.0
 * @since 1.12.0
 *
 */
public class HistogramDensityEstimator implements DensityEstimator, Serializable {
	
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
		double[] vals = new double[this.weightedValHolder.getNumVals()];
		for(int i=0;i<vals.length;i++)
			vals[i] = this.weightedValHolder.getValue(i);
		return vals;
	}

	@Override
	public double[] getWeights() {
		double[] weights = new double[this.weightedValHolder.getNumVals()];
		for(int i=0;i<weights.length;i++)
			weights[i] = this.weightedValHolder.getWeight(i);
		return weights;
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
		
		double iqr = UtilsPT.quantile(vals, 0.75) - UtilsPT.quantile(vals, 0.25);
		
		this.binWidth = 2.0*iqr/Math.pow(nVals, 1.0/3.0);
		
		if(this.binWidth<this.minBinWidth) {
			this.binWidth=this.minBinWidth;
			if(range<this.minBinWidth) {
				this.minVal-=this.minBinWidth/2;
				this.maxVal+=this.minBinWidth/2;
			}
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

}

/**
 * 
 */
package weka.estimators.density;

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import weka.core.Utils;
import weka.core.UtilsPT;
import weka.estimators.density.histogram.bin.Bin;
import weka.estimators.density.histogram.bin.IBin;
import weka.tools.Linspace;

/**
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */
public class EqualProbabilityHistogramDensityEstimator extends AHistogramDensityEstimator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6404220124865228065L;


	@Override
	protected void initializeBins() {

		double[] vals = this.getValues();
		
		
		this.minVal = vals[Utils.minIndex(vals)];
		this.maxVal = vals[Utils.maxIndex(vals)];
		double range = (this.maxVal-this.minVal);
		
		this.bins = new LinkedList<IBin>();
		
		if(range <= this.minRange) {
			this.minVal-=this.minRange/2;
			this.maxVal+=this.minRange/2;
			this.bins.add(new Bin(this.minVal, this.maxVal));
			return;
		}
		
		int nBins = (int) Math.floor( Math.sqrt(vals.length));
		
		if(nBins == 1) {
			this.bins.add( new Bin(this.minVal, this.maxVal) );
			return;
		}
		
		double[] quantiles = Linspace.genLinspace(0, 1, nBins);
		
		double[] quantiles_vals = new double[quantiles.length];
		
		
		//TODO prune quantiles
		for(int i = 0;i<quantiles_vals.length;i++) {
			quantiles_vals[i] = UtilsPT.quantile(vals, quantiles[i]);
		}
		
		quantiles_vals = pruneQuantiles(quantiles_vals);
		
		if(quantiles_vals.length <= 2) {
			this.bins.add( new Bin(this.minVal, this.maxVal) );
			return;
		}
		
		
		//TODO connect some bins if too narrow
		double lastVal = this.minVal;
		boolean extendingBin = false;
		for(int i=0;i<quantiles_vals.length -1;i++) {
			 
			if(! extendingBin) {
				
				double tmpMinVal = quantiles_vals[i]; 
				double tmpMaxVal = quantiles_vals[i+1];
				double rangeBin = tmpMaxVal - tmpMinVal;
				
				if (rangeBin >= this.minRange) {
					this.bins.add(new Bin(quantiles_vals[i], quantiles_vals[i+1]) );
				}
				else {
					lastVal = tmpMinVal;
					extendingBin = true;
				}
				
			}else {
				
				double tmpMinVal = lastVal; 
				double tmpMaxVal = quantiles_vals[i+1];
				double rangeBin = tmpMaxVal - tmpMinVal;
				if(rangeBin >= this.minRange) {
					this.bins.add(new Bin(lastVal, quantiles_vals[i+1]) );
					extendingBin = false;
				}
				
			}
		}
		
		return;
	}
	
	protected double[] pruneQuantiles(double[] quantiles) {
		
		Set<Double> quantileSet = new TreeSet<Double>();
		
		for (double double1 : quantiles) {
			quantileSet.add(double1);
		}
		
		Double[] effQuantilesD = new Double[quantileSet.size()];
		quantileSet.toArray(effQuantilesD);
		
		double[] effQuantiles  = new double[effQuantilesD.length];
		for(int i=0;i<effQuantilesD.length;i++)
			effQuantiles[i] = effQuantilesD[i];
		
		
		
		return effQuantiles;
	}

}

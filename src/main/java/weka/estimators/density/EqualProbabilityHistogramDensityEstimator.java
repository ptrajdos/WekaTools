/**
 * 
 */
package weka.estimators.density;

import java.util.LinkedList;

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

//	TODO do not integrate to one
//	Why?
	@Override
	protected void initializeBins() {
		
		
		double[] vals = this.getValues();
		
		
		this.minVal = vals[Utils.minIndex(vals)];
		this.maxVal = vals[Utils.maxIndex(vals)];
		double range = (this.maxVal-this.minVal);
		
		this.bins = new LinkedList<IBin>();
		
		if(range < this.minRange) {
			this.minVal-=this.minRange/2;
			this.maxVal+=this.minRange/2;
			this.bins.add(new Bin(this.minVal, this.maxVal));
			return;
		}
		
		int nBins = (int) Math.floor( Math.sqrt(vals.length));//TODO Finad a way to estimate this.
		
		if(nBins == 1) {
			this.bins.add( new Bin(this.minVal, this.maxVal) );
			return;
		}
		
		double[] quantiles = Linspace.genLinspace(0, 1, nBins);
		
		double[] quantiles_vals = new double[quantiles.length];
		
		for(int i = 0;i<quantiles_vals.length;i++) {
			quantiles_vals[i] = UtilsPT.quantile(vals, quantiles[i]);
		}
		
		
		
		for(int i=0;i<quantiles_vals.length -1;i++) {
			this.bins.add(new Bin(quantiles_vals[i], quantiles_vals[i+1]));
		}
		
		return;
	}

}

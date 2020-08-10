/**
 * 
 */
package weka.estimators.density.bandwidthFinders;

import weka.core.UtilsPT;

/**
 * Implements Silverman's bandwidth selection rule
 * @author pawel trajdos
 * @since 0.13.0
 * @version 1.5.3
 *
 */
public class SilvermanBandwidthSelectionKernel extends SimpleBandwidthFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6985984437409051201L;
	


	/* (non-Javadoc)
	 * @see weka.estimators.density.BandwidthSelectionKernelEstimator#findBandwidth()
	 */
	@Override
	protected void findBandwidth() {
		double[] vals = this.getValues();
		double sd = UtilsPT.stdDev(vals);
		double iqr = (UtilsPT.quantile(vals, 0.75) - UtilsPT.quantile(vals, 0.25))/1.34;
		double h = this.scaleFactor*0.9*Math.min(sd, iqr) * Math.pow(vals.length, -1/5);
		h = Math.max(h, minH);
		this.kernEstim.setBandwidth(h);
	}

	
	/**
	 * 
	 */
	public String toString() {
		return "Kernel estimator with kernel bandwidth found using Silverman's rule:\n" + this.kernEstim.toString();
	}
	

}

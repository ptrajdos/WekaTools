/**
 * 
 */
package weka.estimators.density.tools;

import java.io.Serializable;

import weka.core.Utils;
import weka.estimators.density.DensityEstimator;
import weka.tools.Linspace;

/**
 * @author pawel trajdos
 * @since 0.14.0
 * @version 0.14.0
 *
 */
public class ROIFinder implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5460027473493917508L;

	/**
	 * Finds Region of Interest -- region with non-zero PDF
	 * @param estimator used to find ROI
	 * @return [lower_bound, upper_bound]
	 */
	public static double[] findRoi(DensityEstimator estim, double lowerSearchBound , double upperSearchBound, int numSamples) {
		double[] roi = new double[2];
		double[] seq = Linspace.genLinspace(lowerSearchBound, upperSearchBound, numSamples);
		roi[0]=lowerSearchBound;
		roi[1]=upperSearchBound;
		boolean upperFound=false;
		double tmpCdf=0;
		for(int i=0;i<seq.length;i++) {
			tmpCdf = estim.getCDF(seq[i]);
			if(Utils.eq(tmpCdf, 0))
				roi[0]=seq[i];
			if(Utils.eq(tmpCdf,1) & !upperFound) {
				roi[1]=seq[i];
				upperFound=true;
			}
				
				
		}
		return roi;
	}

}

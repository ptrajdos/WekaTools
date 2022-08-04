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
 * @version 2.0.0
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
		
		double delta = 1.0/numSamples;
		double realLowerBound=lowerSearchBound;
		double realUppoerBound=upperSearchBound;
		
		while(!Utils.eq(estim.getCDF(realLowerBound), 0)) {
			realLowerBound-=delta;
		}
		
		while(!Utils.eq(estim.getCDF(realUppoerBound), 1)) {
			realUppoerBound+=delta;
		}
		
		
		double[] seq = Linspace.genLinspace(realLowerBound, realUppoerBound, numSamples);
		double h = (realUppoerBound - realLowerBound)/numSamples;
		roi[0]=realLowerBound;
		roi[1]=realUppoerBound;
		boolean upperFound=false;
		double tmpCdf=0;
		for(int i=0;i<seq.length;i++) {
			tmpCdf = estim.getCDF(seq[i]);
			if(Utils.eq(tmpCdf, 0))
				roi[0]=seq[Math.max(i-1,0)];
			
			if(Utils.eq(tmpCdf,1) & !upperFound) {
				roi[1]=seq[Math.min(i+1,seq.length-1)];
				upperFound=true;
			}
			
			if(Utils.eq(roi[0], roi[1])) {
				roi[0]-=h;
				roi[1]+=h;
				
			}
			if(upperFound)
				break;
				
		}
		return roi;
	}
	
	public static class RoI implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 8161532791060580667L;
		
		private double lowerBound;
		private double upperBound;
		
		public RoI(double lowerBound, double upperBound) {
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
		}

		/**
		 * @return the lowerBound
		 */
		public double getLowerBound() {
			return this.lowerBound;
		}

		/**
		 * @return the upperBound
		 */
		public double getUpperBound() {
			return this.upperBound;
		}
		
		public double intersectionLength(RoI otherRoI) {
			
			double rightmost = Math.min(this.upperBound, otherRoI.upperBound);
			double leftmost = Math.max(this.lowerBound, otherRoI.lowerBound);
			
			double area = Math.max(0, rightmost - leftmost);
			
			return area;
		}
		
		public boolean isIntersecting(RoI otherRoi) {
			return this.intersectionLength(otherRoi)>0;
		}
		
		public RoI getIntersection(RoI otherRoI){
			
			if(! this.isIntersecting(otherRoI))
				return null;
			
			double rightmost = Math.min(this.upperBound, otherRoI.upperBound);
			double leftmost = Math.max(this.lowerBound, otherRoI.lowerBound);
			
			return new RoI(leftmost, rightmost);
			
			
		}
		
		
	}
	
	public static RoI findRoi2(DensityEstimator estim, double lowerSearchBound , double upperSearchBound, int numSamples) {
		double[] dRoi = ROIFinder.findRoi(estim, lowerSearchBound, upperSearchBound, numSamples);
		RoI roi = new RoI(dRoi[0], dRoi[1]);
		return roi;
	}
	
	

}

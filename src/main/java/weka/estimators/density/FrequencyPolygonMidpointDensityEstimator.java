/**
 * 
 */
package weka.estimators.density;

/**
 * @author pawel trajdos
 * @since 1.12.0
 * @version 1.12.0
 *
 */
public class FrequencyPolygonMidpointDensityEstimator extends HistogramDensityEstimator {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7641774786253074286L;

	@Override
	public double getPDF(double x) {
		this.computeHist();
		
		
		if(x<= (this.minVal - this.binWidth/2) || x>=(this.maxVal + this.binWidth/2))
			return 0;
		
		if(this.binCounts.length==1)
			return super.getPDF(x);
		
		int binIndex = this.findBin(x);
		double binUpperThresh = this.minVal + this.binWidth*(binIndex + 1);
		double binMidPoint = binUpperThresh - this.binWidth/2.0;
		
		double pdf=0; 
		
		
		if(binIndex==0 && x<=binMidPoint) {
			double newMid = binMidPoint - this.binWidth;
			pdf = this.binCounts[binIndex]*((x-newMid)/this.binWidth)/(this.overallSum*this.binWidth);
			return pdf;
		}
		
		if(binIndex == (this.binCounts.length -1) && x >= binMidPoint) {
			double newMid=this.maxVal;
			pdf = this.binCounts[binIndex]*(1-(x-newMid)/this.binWidth )/(this.overallSum*this.binWidth);
			return pdf;
		}
		
		int lowIdx;
		int highIdx;
		
		if(x>binMidPoint) {
			lowIdx = binIndex;
			highIdx = lowIdx+1;
		}else {
			highIdx=binIndex;
			lowIdx = highIdx-1;
			binMidPoint-=this.binWidth;
		}
		
		pdf = (this.binCounts[lowIdx] + (this.binCounts[highIdx] - this.binCounts[lowIdx])*((x-binMidPoint)/this.binWidth))/(this.overallSum*this.binWidth);
		
		
		
		return pdf;
	}

	@Override
	public double getCDF(double x) {
		
		
		this.computeHist();
		
		if(x<(this.minVal-this.binWidth/2))
			return 0;
		
		if(x> (this.maxVal + this.binWidth/2))
			return 1;
		
		if(this.binCounts.length==1)
			return super.getCDF(x);
		
		
		double currentMidPoint = this.minVal + this.binWidth/2;
		
		double integral=0;
		
		while(x>currentMidPoint) {
			integral+=0.5*(this.getPDF(currentMidPoint-this.binWidth) + this.getPDF(currentMidPoint))*this.binWidth;
			currentMidPoint+=this.binWidth;
		}
		
		integral+= 0.5*(this.getPDF(currentMidPoint - this.binWidth) + this.getPDF(x))*(x-(currentMidPoint - this.binWidth));
		
		
		integral= integral>1? 1:integral;
		
		return integral;
		
	}



}

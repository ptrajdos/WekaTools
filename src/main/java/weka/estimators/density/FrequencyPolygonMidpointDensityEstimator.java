/**
 * 
 */
package weka.estimators.density;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;
import weka.estimators.density.histogram.bin.IBin;
import weka.tools.numericIntegration.Function;
import weka.tools.numericIntegration.TrapezoidalIntegrator;

/**
 * @author pawel trajdos
 * @since 1.12.0
 * @version 2.0.0
 *
 */
public class FrequencyPolygonMidpointDensityEstimator implements IHistogramDensityEstimator, Serializable, OptionHandler {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7641774786253074286L;
	
	protected IHistogramDensityEstimator internalHistogramEstimator = new HistogramDensityEstimator();
	protected double minVal=0;
	protected double maxVal=0;
	protected double overallSum = 0;
	protected double normalizingFactor = 1.0;
	
	protected boolean isInitialised = false;
	
	protected void computeHist() {
		
		if (this.isInitialised)
			return;
		
		
		this.internalHistogramEstimator.getPDF(0);
		
		
		
		List<IBin> bins = this.internalHistogramEstimator.getBins();
		
		this.minVal = bins.get(0).getLowerBound();
		this.maxVal = bins.get(bins.size() - 1 ).getUpperBound();
		
		
		this.overallSum=0;
		for (IBin iBin : bins) {
			this.overallSum+= iBin.getCount();
		}
				
		this.isInitialised = true;
		
	}
	


	@Override
	public double getPDF(double x) {
		this.computeHist();
		
		List<IBin> bins = this.internalHistogramEstimator.getBins();
		int nBins = bins.size();
		
		
		double lowerT = bins.get(0).getWidth()/2.0;
		double upperT = bins.get( bins.size() -1 ).getWidth()/2.0;
		
		if(x<= (this.minVal -  lowerT) || x>= ( this.maxVal + upperT ) )
			return 0;
		
		if(bins.size() == 1)
			return this.internalHistogramEstimator.getPDF(x);
		
		int binIndex = this.findBin(x);
		IBin tmpBin = bins.get(binIndex);
		double binMidPoint = tmpBin.getBinCenter();
		
		double pdf=0; 
		
		
		if(binIndex==0 && x<=binMidPoint) {
			double newMid = binMidPoint - tmpBin.getWidth();
			pdf = tmpBin.getCount()*((x-newMid)/tmpBin.getWidth())/(this.overallSum*tmpBin.getWidth());
			return pdf;
		}
		
		if(binIndex == (nBins -1) && x >= binMidPoint) {
			double newMid=binMidPoint;
			pdf = tmpBin.getCount()*(1-(x-newMid)/tmpBin.getWidth() )/(this.overallSum*tmpBin.getWidth());
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
		}
		
		IBin lowIdxBin = bins.get(lowIdx);
		IBin highIdxBin = bins.get(highIdx);
		binMidPoint = lowIdxBin.getBinCenter();
		
		double t = x- binMidPoint;
		double wl = lowIdxBin.getWidth();
		double wh = highIdxBin.getWidth();
		double lc = lowIdxBin.getCount();
		double hc = highIdxBin.getCount();
		
		double a = (2.0/(wl + wh) ) * (  (hc*wl - lc*wh)/(this.overallSum*wl*wh) );
		double b = lc/(this.overallSum*wl);
		
		pdf = a*t + b;
		
		return pdf;

	}

	@Override
	public double getCDF(double x) {
		
		
		this.computeHist();
		
		List<IBin> bins = this.internalHistogramEstimator.getBins();
		int nBins = bins.size();
		
		double lowerT = this.minVal -  bins.get(0).getWidth()/2.0;
		double upperT = this.maxVal +  bins.get( bins.size() -1 ).getWidth()/2.0;
		
		if(x<lowerT )
			return 0;
		
		if(x>  upperT)
			return 1;
		
		if(nBins ==1 )
			return this.internalHistogramEstimator.getCDF(x);
		
		if (x < bins.get(0).getBinCenter() ) {
			double integral = 0.5*(x - lowerT)*this.getPDF(x);
			return integral;
		}
		
		double integral = 0.5*(bins.get(0).getBinCenter() - lowerT)*this.getPDF(bins.get(0).getBinCenter()); 
		int index=0;
		for(int i=1;i<bins.size();i++) {
			index=i;
			double currCenter =bins.get(index).getBinCenter();
			double prevCenter = bins.get(index-1).getBinCenter();
			
			if(x> currCenter) {
				integral += 0.5*(currCenter - prevCenter)*( this.getPDF(prevCenter) + this.getPDF(currCenter));
				
				if( index == bins.size() -1 ) {
					integral += 0.5*(x - currCenter)*( this.getPDF(currCenter) + this.getPDF(x) );
				}

			}
			else {
				integral += 0.5*(x - prevCenter)*(this.getPDF(prevCenter) + this.getPDF(x));
				break;
			}
				
		}
				
		return integral;
	}

	@Override
	public void addValues(double[] data, double[] weight) {
		this.internalHistogramEstimator.addValues(data, weight);
	}

	@Override
	public double[] getValues() {
		this.isInitialised = false;
		return this.internalHistogramEstimator.getValues();
	}

	@Override
	public double[] getWeights() {
		return this.internalHistogramEstimator.getWeights();
	}

	@Override
	public void addValue(double data, double weight) {
		this.isInitialised = false;
		this.internalHistogramEstimator.addValue(data, weight);
	}

	@Override
	public List<IBin> getBins() {
		return this.internalHistogramEstimator.getBins();
	}
	
	protected int findBin(double value) {
		int index=0;
		List<IBin> bins = this.internalHistogramEstimator.getBins();
		int numBins = bins.size();
		
		if(value < bins.get(0).getLowerBound())
			return 0;
		
		for(int i=0;i<numBins;i++) {
			index = i;
			if( bins.get(i).isValueInBin(value) ) {
				break;
			}
			
		}
		return index;
	}

	/**
	 * @return the internalHistogramEstimator
	 */
	public IHistogramDensityEstimator getInternalHistogramEstimator() {
		return this.internalHistogramEstimator;
	}

	/**
	 * @param internalHistogramEstimator the internalHistogramEstimator to set
	 */
	public void setInternalHistogramEstimator(IHistogramDensityEstimator internalHistogramEstimator) {
		this.internalHistogramEstimator = internalHistogramEstimator;
	}
	
	public String internalHistogramEstimatorTipText() {
		return "Internal Histogram-based estimator that is used to construct frequency polygons.";
	}

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tHistogram density Estimator to use. "+
		          "(default:" + "weka.estimators.density.HistogramDensityEstimator" + ".\n",
			      "HIE", 1, "-HIE"));
		
		
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setInternalHistogramEstimator((IHistogramDensityEstimator) UtilsPT.parseObjectOptions(options, "HIE", new HistogramDensityEstimator(), IHistogramDensityEstimator.class));
		
	}

	@Override
	public String[] getOptions() {

		Vector<String> options = new Vector<String>();
		
		options.add("-HIE");
		options.add(UtilsPT.getClassAndOptions(this.getInternalHistogramEstimator()) );
		
	    return options.toArray(new String[0]);
	}



	@Override
	public void reset() {
		this.internalHistogramEstimator.reset();
		this.isInitialised = false;
	}
	
	
	

}

/**
 * 
 */
package weka.classifiers.evaluation;

/**
 * Two class stats with safety checks to avoid NaNs.
 * @author pawel trajdos
 * @since 1.13.0
 * @version 1.13.0
 *
 */
public class TwoClassStatsPT extends TwoClassStats {
	
	private static double EPS=1E-6;

	/** Statistics of a two-class classification problem
	 * @param tp
	 * @param fp
	 * @param tn
	 * @param fn
	 */
	public TwoClassStatsPT(double tp, double fp, double tn, double fn) {
		super(tp, fp, tn, fn);
	}
	
	@Override
	public double getPrecision() {
		
		double tp = this.getTruePositive();
		double fp = this.getFalsePositive();
		double fn = this.getFalseNegative();
		
		if(tp+fp+fn < EPS)
			return 1.0;
		
		if (tp < EPS && fp <EPS)
			return 0;
		
		double precision = tp/ (tp+fp);
		 
		return precision;
	}
	
	@Override
	public double getFMeasure() {
		double tp = this.getTruePositive();
		double fp = this.getFalsePositive();
		double fn = this.getFalseNegative();
		
		if(tp+fp+fn < EPS)
			return 1.0;
		
		double fMeasure = 2.0*tp/(tp+fp+fn);
		
		return fMeasure;
	}
	
	@Override
	public double getTruePositiveRate() {
		
		double tp = this.getTruePositive();
		double fp = this.getFalsePositive();
		double fn = this.getFalseNegative();
		
		if(tp+fp+fn < EPS)
			return 1.0;
		
		if (tp < EPS && fn < EPS)
			return 0.0;
		
		double tpr = tp/ (tp+fn);
		 
		return tpr;
	}
	
	@Override
	public double getFalsePositiveRate() {
		double fp = this.getFalsePositive();
		double fn = this.getFalseNegative();
		double tn = this.getTrueNegative();
		
		
		if(tn + fp + fn <EPS)
			return 1.0;
		
		if(tn < EPS && fp<EPS)
			return 0.0;
		
		double fpr = tn/(tn+fp);
		return fpr;
	}
	
	@Override
	public double getFallout() {
		double fp = this.getFalsePositive();
		double fn = this.getFalseNegative();
		double tn = this.getTrueNegative();
		
		if(tn + fp + fn <EPS)
			return 1.0;
		
		if(tn < EPS && fp<EPS)
			return 0.0;
		
		double fallOut = fp/(fp+tn);
		
		
		return fallOut;
	}
	
	

}

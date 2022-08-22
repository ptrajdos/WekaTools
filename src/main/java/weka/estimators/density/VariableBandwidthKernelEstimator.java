/**
 * 
 */
package weka.estimators.density;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;

/**
 * Variable bandwidth kernel estimator.
 * Implemented according to:
 * 
 * @article{Van_Kerm_2003,
	doi = {10.1177/1536867x0300300204},
	url = {https://doi.org/10.1177%2F1536867x0300300204},
	year = 2003,
	month = {jun},
	publisher = {{SAGE} Publications},
	volume = {3},
	number = {2},
	pages = {148--156},
	author = {Philippe Van Kerm},
	title = {Adaptive Kernel Density Estimation},
	journal = {The Stata Journal: Promoting communications on statistics and Stata}
}

 * @author pawel trajdos
 * @since 1.13.0
 * @version 2.0.0
 *
 */
public class VariableBandwidthKernelEstimator extends WeightedKernelEstimator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3212214582295420249L;
	
	/**
	 * Point-specific multipliers of bandwidths
	 */
	private double[] lambdas;
	
	private boolean areLambdasInitialized = false;
	
	private double alpha=0.5;

	
	private void initializeLambdas() {
		if(this.areLambdasInitialized)
			return;
		
		double[] points = this.getValues();
		double[] pilotEstims = new double[points.length];
		
		for(int i=0;i<pilotEstims.length;i++) {
			pilotEstims[i] = super.getPDF(points[i]);
		}
		
		double pilotGMean = UtilsPT.geometricMean(pilotEstims);
		
		this.lambdas = new double[points.length];
		
		for(int i=0;i<this.lambdas.length;i++)
			this.lambdas[i] = Math.pow(pilotGMean/pilotEstims[i], this.alpha);
		
		
		this.areLambdasInitialized = true;
	}
	
	@Override
	public void addValue(double data, double weight) {
		super.addValue(data, weight);
		this.areLambdasInitialized = false;
	}
	
	@Override
	public void addValues(double[] data, double[] weight) {
		super.addValues(data, weight);
		this.areLambdasInitialized = false;
	}
	
	@Override
	public double getPDF(double x) {
		this.initializeLambdas();
		
		int numVals = this.valHolder.getNumVals();
		
		if( numVals == 0)
			return Double.NaN;
		
		double estimation =0;
		for(int i=0;i<numVals;i++) {
			estimation+=this.kernel.getKernelPDFValue(  (x-this.valHolder.getValue(i))/(this.bandwidth*this.lambdas[i])  )
					*this.valHolder.getWeight(i)/(this.lambdas[i]*this.bandwidth);
			
		}
		return estimation;
	}
	
	@Override
	public double getCDF(double x) {
		this.initializeLambdas();
		
		int numVals = this.valHolder.getNumVals();
		
		if( numVals == 0)
			return Double.NaN;
		
		double estimation =0;
		for(int i=0;i<numVals;i++)
			estimation+=this.kernel.getKernelCDFValue(  (x-this.valHolder.getValue(i))/(this.bandwidth*this.lambdas[i])  )*this.valHolder.getWeight(i);
		
		return estimation;
	}

	/**
	 * @return the alpha
	 */
	public double getAlpha() {
		return this.alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(double alpha) {
		if (alpha <0 ) {
			this.alpha =0;
			return;
		}
			
		
		if(alpha >1) {
			this.alpha =1.0;
			return;
		}
			
		
		this.alpha = alpha;
	}
	
	public String alphaTipText() {
		return "The sensitivity parameter";
	}

	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tThe sensitivity parameter"+
		          "(default:" + 0.5 + ").\n",
			      "ASE", 1, "-ASE"));
		 
		 
		 newVector.addAll(Collections.list(super.listOptions()));
		    
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setAlpha(UtilsPT.parseDoubleOption(options, "ASE", 0.5));
		
		super.setOptions(options);
	}

	@Override
	public String[] getOptions() {
		
		Vector<String> options = new Vector<String>();

	    options.add("-ASE");
	    options.add(""+this.getAlpha());
	    
	    Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);

	}
	
	


}

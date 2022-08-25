/**
 * 
 */
package weka.estimators.density;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.KhanKleinSummator;
import weka.core.Option;
import weka.core.UtilsPT;

/**
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */
public class VariableBandwidthKernelDensityEstimator2 extends NestedBasicKernelDensityEstimator {

	private static final long serialVersionUID = 2108024791848284867L;

	/**
	 * 
	 */
	public VariableBandwidthKernelDensityEstimator2() {
		// TODO Auto-generated constructor stub
	}

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
			pilotEstims[i] =  this.kernEstim.getPDF(points[i]);
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
		
		int numVals = this.kernEstim.getValues().length;
		
		if( numVals == 0)
			return Double.NaN;
		
		
		Kernel kernel = this.getKernEstim().getKernel();
		double[] values = this.getKernEstim().getValues();
		double[] weights = this.getKernEstim().getWeights();
		double bandwidth = this.getKernEstim().getBandwidth();
		
		KhanKleinSummator estimationSum = new KhanKleinSummator();
		
		for(int i=0;i<numVals;i++) {
			estimationSum.addToSum(kernel.getKernelPDFValue(  (x- values[i] )/(bandwidth*this.lambdas[i])  )
					*weights[i]/(this.lambdas[i]*bandwidth));
		}
		return estimationSum.getSum();
	}
	
	@Override
	public double getCDF(double x) {
		this.initializeLambdas();
		
		int numVals = this.kernEstim.getValues().length;
		
		if( numVals == 0)
			return Double.NaN;
		
		Kernel kernel = this.getKernEstim().getKernel();
		double[] values = this.getKernEstim().getValues();
		double[] weights = this.getKernEstim().getWeights();
		double bandwidth = this.getKernEstim().getBandwidth();
		

		KhanKleinSummator estimationSum = new KhanKleinSummator();
		for(int i=0;i<numVals;i++)
			estimationSum.addToSum(kernel.getKernelCDFValue(  ( x- values[i] )/(bandwidth*this.lambdas[i])  )*weights[i]);

		
		return estimationSum.getSum();
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

/**
 * 
 */
package weka.estimators.density;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;

/**
 * Estimator with boundary correction based on the reflection approach
 * @author pawel trajdos
 * @since 0.9.0
 * @version 2.0.0
 *
 */
public class BoundedEstimator implements DensityEstimator, Serializable, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7156208703546768815L;
	
	protected DensityEstimator kernEstim; 
	
	protected double lowerBound = 0;
	protected double upperBound = 1;

	/**
	 * 
	 */
	public BoundedEstimator() {
		this.kernEstim = new SimpleKernelEstimator();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.IncrementalEstimator#addValue(double, double)
	 */
	@Override
	public void addValue(double data, double weight) {
		this.kernEstim.addValue(data, weight);
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getPDF(double)
	 */
	@Override
	public double getPDF(double x) {
		
		if(x < this.lowerBound)return 0;
		if(x > this.upperBound)return 0;
		
		return this.kernEstim.getPDF(x) +  this.kernEstim.getPDF(2*this.lowerBound -x) + this.kernEstim.getPDF(2*this.upperBound -x);
		
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getCDF(double)
	 */
	@Override
	public double getCDF(double x) {
		
		if(x < this.lowerBound)return 0;
		if(x > this.upperBound)return 1;
		
		
		return this.kernEstim.getCDF(x) 
				-  this.kernEstim.getCDF(2*this.lowerBound -x)
				+ this.kernEstim.getCDF(2*this.upperBound - this.lowerBound) - this.kernEstim.getCDF(2*this.upperBound-x);
	}
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
	
		newVector.addElement(new Option(
			      "\tEstimator object to use "+
		          "(default: weka.estimators.density.SimpleKernelEstimator).\n",
			      "KES", 1, "-KES"));
		
		newVector.addElement(new Option(
			      "\tLower Bound to use "+
		          "(default: 1).\n",
			      "LB", 1, "-LB"));
		
		newVector.addElement(new Option(
			      "\tUpper Bound to use "+
		          "(default: 1).\n",
			      "UB", 1, "-UB"));
		
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {

		this.setKernEstim(
				(DensityEstimator)
				UtilsPT.parseObjectOptions(options, "KES", new SimpleKernelEstimator(), DensityEstimator.class));
		
		this.setLowerBound(UtilsPT.parseDoubleOption(options, "LB", 0.0));
		this.setUpperBound(UtilsPT.parseDoubleOption(options, "UB", 1.0));
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		
		options.add("-KES");
		options.add(UtilsPT.getClassAndOptions(this.getKernEstim()));
		
		options.add("-LB");
		options.add(""+ this.getLowerBound());
		
		options.add("-UB");
		options.add(""+ this.getUpperBound());
		
	    return options.toArray(new String[0]);
	}

	public String kernEstimTipText() {
		return "The kernel estimator used inside the bounded estimator";
	}
	/**
	 * @return the kernEstim
	 */
	public DensityEstimator getKernEstim() {
		return this.kernEstim;
	}

	/**
	 * @param densityEstimator the kernEstim to set
	 */
	public void setKernEstim(DensityEstimator densityEstimator) {
		this.kernEstim = densityEstimator;
	}

	public String lowerBoundTipText() {
		return "Lower bound of the distribution.";
	}
	/**
	 * @return the lowerBound
	 */
	public double getLowerBound() {
		return this.lowerBound;
	}

	
	/**
	 * @param lowerBound the lowerBound to set
	 */
	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}
	
	public String upperBoundTipText() {
		return "Upper bound of the distribution.";
	}

	/**
	 * @return the upperBound
	 */
	public double getUpperBound() {
		return this.upperBound;
	}

	/**
	 * @param upperBound the upperBound to set
	 */
	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}

	@Override
	public void addValues(double[] data, double[] weight) {
		this.kernEstim.addValues(data, weight);
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("Bounded Estimator \n");
		strBuild.append("Lower Bound: " + this.getLowerBound()+ "\n");
		strBuild.append("Upper Bound: " + this.getUpperBound()+ "\n");
		strBuild.append(this.kernEstim.toString()+ "\n");
		return strBuild.toString();
	}

	@Override
	public double[] getValues() {
		return this.kernEstim.getValues();
	}

	@Override
	public double[] getWeights() {
		return this.kernEstim.getWeights();
	}

	@Override
	public void reset() {
		this.kernEstim.reset();
	}
	
	
	
	

}

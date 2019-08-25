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
import weka.estimators.density.kernels.EpanechnikovKernel;

/**
 * Estimator with boundary correction based on the reflection approach
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
 *
 */
public class BoundedKernelEstimator implements KernelDensityEstimator, Serializable, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7156208703546768815L;
	
	protected KernelDensityEstimator kernEstim; 
	
	protected double lowerBound = 0;
	protected double upperBound = 1;

	/**
	 * 
	 */
	public BoundedKernelEstimator() {
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
			      "\tBandwidth to use "+
		          "(default: 1).\n",
			      "BW", 1, "-BW"));
		
		newVector.addElement(new Option(
			      "\tKernel object to use "+
		          "(default: weka.estimators.density.kernels.EpanechnikovKernel).\n",
			      "KE", 1, "-KE"));
		
		newVector.addElement(new Option(
			      "\tKernel Estimator object to use "+
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
		this.setKernel((Kernel) 
				UtilsPT.parseObjectOptions(options, "KE", new EpanechnikovKernel(), Kernel.class));
		
		this.setBandwidth(UtilsPT.parseDoubleOption(options, "BW", 0.1));
		
		this.setKernEstim(
				(KernelDensityEstimator)
				UtilsPT.parseObjectOptions(options, "KES", new SimpleKernelEstimator(), KernelDensityEstimator.class));
		
		this.setLowerBound(UtilsPT.parseDoubleOption(options, "LB", 0.0));
		this.setUpperBound(UtilsPT.parseDoubleOption(options, "UB", 1.0));
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-BW");
		options.add(""+ this.getBandwidth());
		
		options.add("-KE");
		options.add(UtilsPT.getClassAndOptions(this.getKernel()));
		
		options.add("-KES");
		options.add(UtilsPT.getClassAndOptions(this.getKernEstim()));
		
		options.add("-LB");
		options.add(""+ this.getLowerBound());
		
		options.add("-UB");
		options.add(""+ this.getUpperBound());
		
	    return options.toArray(new String[0]);
	}

	@Override
	public void setKernel(Kernel kernel) {
		this.kernEstim.setKernel(kernel);
	}

	@Override
	public Kernel getKernel() {
		return this.kernEstim.getKernel();
	}

	@Override
	public void setBandwidth(double bandwidth) {
		this.kernEstim.setBandwidth(bandwidth);
	}

	@Override
	public double getBandwidth() {
		return this.kernEstim.getBandwidth();
	}

	/**
	 * @return the kernEstim
	 */
	public KernelDensityEstimator getKernEstim() {
		return this.kernEstim;
	}

	/**
	 * @param kernEstim the kernEstim to set
	 */
	public void setKernEstim(KernelDensityEstimator kernEstim) {
		this.kernEstim = kernEstim;
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
	
	

}

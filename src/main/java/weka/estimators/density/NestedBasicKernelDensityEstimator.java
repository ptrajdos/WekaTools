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
 * Kernel with nested BasicKernelDensityEstimator
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */
public abstract class NestedBasicKernelDensityEstimator implements BasicKernelDensityEstimator, Serializable, OptionHandler {

	
	
	private static final long serialVersionUID = -5224055180063504611L;
	
	protected BasicKernelDensityEstimator kernEstim;
	protected boolean isModified=true;

	/**
	 * 
	 */
	public NestedBasicKernelDensityEstimator() {
		this.kernEstim = new SimpleKernelEstimator();
	}


	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#addValues(double[], double[])
	 */
	@Override
	public void addValues(double[] data, double[] weight) {
		this.kernEstim.addValues(data, weight);
		this.isModified=true;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getValues()
	 */
	@Override
	public double[] getValues() {
		return this.kernEstim.getValues();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getWeights()
	 */
	@Override
	public double[] getWeights() {
		return this.kernEstim.getWeights();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.IncrementalEstimator#addValue(double, double)
	 */
	@Override
	public void addValue(double data, double weight) {
		this.kernEstim.addValue(data, weight);
		this.isModified=true;

	}

	/**
	 * @return the kernEstim
	 */
	public BasicKernelDensityEstimator getKernEstim() {
		return this.kernEstim;
	}

	/**
	 * @param kernEstim the kernEstim to set
	 */
	public void setKernEstim(BasicKernelDensityEstimator kernEstim) {
		this.kernEstim = kernEstim;
		this.isModified=true;
	}
	
	public String kernEstimTipText() {
		return "Kernel estimator to use";
	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#listOptions()
	 */
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tKernel object to use "+
		          "(default: weka.estimators.density.SimpleKernelEstimator).\n",
			      "KES", 1, "-KES"));
		
	    
		return newVector.elements();
	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		this.setKernEstim((KernelDensityEstimator) 
				UtilsPT.parseObjectOptions(options, "KES", new SimpleKernelEstimator(), KernelDensityEstimator.class));
		
	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#getOptions()
	 */
	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		
		options.add("-KES");
		options.add(UtilsPT.getClassAndOptions(this.kernEstim));
		
	    return options.toArray(new String[0]);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bandwidth Selection Kernel Estimator:\n" + this.kernEstim.toString();
	}

	@Override
	public void reset() {
		this.kernEstim.reset();
		
	}

	@Override
	public Kernel getKernel() {
		return this.kernEstim.getKernel();
	}

	@Override
	public double getBandwidth() {
		return this.kernEstim.getBandwidth();
	}
	

}

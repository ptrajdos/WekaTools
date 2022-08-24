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
 * Kernel estimator with automatic bandwidth selection
 * @author pawel trajdos
 * @since 0.13.0
 * @version 0.13.0
 *
 */
public abstract class BandwidthSelectionKernelEstimator implements DensityEstimator, Serializable, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5735185710445258136L;
	
	protected KernelDensityEstimator kernEstim;
	protected boolean isModified=true;

	/**
	 * 
	 */
	public BandwidthSelectionKernelEstimator() {
		this.kernEstim = new SimpleKernelEstimator();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getPDF(double)
	 */
	@Override
	public double getPDF(double x) {
		this.setBandwidthInternal();
		return this.kernEstim.getPDF(x);
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getCDF(double)
	 */
	@Override
	public double getCDF(double x) {
		this.setBandwidthInternal();
		return this.kernEstim.getCDF(x);
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
	 * Finds and sets bandwidth to the kernel
	 * 
	 */
	protected abstract void findBandwidth();
	
	private void setBandwidthInternal() {
		if(!this.isModified)
			return;
		findBandwidth();
		this.isModified=false;
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
	
	
	
	

}

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
import weka.estimators.density.kernels.GaussianKernel;
import weka.tools.WeightedValuesHolder;

/**
 * Abstract Class for kernel density estimators
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
 *
 */
public abstract class AbstractKernelEstimator implements KernelDensityEstimator,DensityEstimator, Serializable, OptionHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7610749246727072220L;

	protected double bandwidth=0.1;
	
	protected Kernel kernel;
	
	protected WeightedValuesHolder valHolder;
	
	protected double weightSum=0;
	

	/**
	 * 
	 */
	public AbstractKernelEstimator() {
		this.kernel = new GaussianKernel();
		this.valHolder = new WeightedValuesHolder();
	}

	
	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#addValues(double[], double[])
	 */
	@Override
	public void addValues(double[] data, double[] weight) {
		this.valHolder.addValues(data, weight);
	}

	/* (non-Javadoc)
	 * @see weka.estimators.IncrementalEstimator#addValue(double, double)
	 */
	@Override
	public void addValue(double data, double weight) {
		this.valHolder.addValue(data, weight);
	}

	public String kernelTipText() {
		return "Kernel used with the kernel estimator";
	}
	/* (non-Javadoc)
	 * @see weka.estimators.density.KernelDensityEstimator#setKernel(weka.estimators.density.Kernel)
	 */
	@Override
	public void setKernel(Kernel kernel) {
		this.kernel = kernel;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.KernelDensityEstimator#getKernel()
	 */
	@Override
	public Kernel getKernel() {
		return this.kernel;
	}

	public String bandwidthTipText() {
		return "Bandwidth (smoothing) paremeter for the kernel estimator";
	}
	/* (non-Javadoc)
	 * @see weka.estimators.density.KernelDensityEstimator#setBandwidth(double)
	 */
	@Override
	public void setBandwidth(double bandwidth) {
		this.bandwidth=bandwidth;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.KernelDensityEstimator#getBandwidth()
	 */
	@Override
	public double getBandwidth() {
		return this.bandwidth;
	}


	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#listOptions()
	 */
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tBandwidth to use "+
		          "(default: 1).\n",
			      "BW", 1, "-BW"));
		
		newVector.addElement(new Option(
			      "\tKernel object to use "+
		          "(default: weka.estimators.density.kernels.GaussianKernel).\n",
			      "KE", 1, "-KE"));
		
	    
		return newVector.elements();
	}


	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		this.setKernel((Kernel) 
				UtilsPT.parseObjectOptions(options, "KE", new GaussianKernel(), Kernel.class));
		
		this.setBandwidth(UtilsPT.parseDoubleOption(options, "BW", 0.1));
		
	}


	/* (non-Javadoc)
	 * @see weka.core.OptionHandler#getOptions()
	 */
	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-BW");
		options.add(""+ this.getBandwidth());
		
		options.add("-KE");
		options.add(UtilsPT.getClassAndOptions(this.getKernel()));
		
	    return options.toArray(new String[0]);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder strBuild  = new StringBuilder();
		strBuild.append("Kernel Estimator \n");
		strBuild.append("Kernel: " + this.kernel.toString() + "\n");
		strBuild.append("Number of samples: " + this.valHolder.getNumVals() + "\n");
		strBuild.append("Bandwidth: " + this.bandwidth + "\n");
		return strBuild.toString();
	}
	
	

}

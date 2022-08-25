package weka.estimators.density;

/**
 * An interface for Kernel Density Estimator.
 * This estimator allows modyfying kernel and/or bandwidth
 *
 * @author pawel trajdos
 * @since 0.9.0
 * @version 2.0.0
 * 
 *
 */
public interface KernelDensityEstimator extends BasicKernelDensityEstimator {
	
	
	/**
	 * Set the kernel to be used with the estimator.
	 * @param kernel
	 */
	public void setKernel(Kernel kernel);
	
	/**
	 * Sets the bandwidth to be used with the estimator.
	 * @param bandwidth
	 */
	public void setBandwidth(double bandwidth);
	

}

package weka.estimators.density;

/**
 * An interface for basic kernel estimator.
 * It allows only to read estimator properties (Kernel, bandwidth)
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */

public interface BasicKernelDensityEstimator extends DensityEstimator {
	
	/**
	 * Gets the kernel used by the estimator
	 * @return
	 */
	public Kernel getKernel();
	
	/**
	 * Gets the bandwidth value used by the estimator.
	 * @return
	 */
	public double getBandwidth();

}

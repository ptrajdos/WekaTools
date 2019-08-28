package weka.estimators.density;
/**
 * Kernel function for kernel estimators
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
 */
public interface Kernel {
	
	/**
	 * Returns the kernel PDF value for given x.
	 * This is the density kernel
	 * @param x
	 * @return kernel value
	 * @author pawel trajdos
	 * @since 0.9.0
	 * @version 0.9.0
	 */
	public double getKernelPDFValue(double x);

	
	
	/**
	 * Returns the kernel CDF value for given x.
	 * This is the cdf kernel value. The result of integration of pdf ernel value from minus infinity to x.
	 * @param x
	 * @return kernel value
	 * @author pawel trajdos
	 * @since 0.9.0
	 * @version 0.9.0
	 */
	public double getKernelCDFValue(double x);

}

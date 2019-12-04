/**
 * 
 */
package weka.estimators.density.kernels;

import java.io.Serializable;

import weka.estimators.density.Kernel;

/**
 * Sigmoid Kernel
 * @author pawel trajdos
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class SigmoidKernel implements Kernel, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5582578875977199494L;
	private double twoOverPi=2.0/Math.PI;
	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelPDFValue(double)
	 */
	@Override
	public double getKernelPDFValue(double x) {
		return this.twoOverPi/(Math.exp(x) + Math.exp(-x));
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelCDFValue(double)
	 */
	@Override
	public double getKernelCDFValue(double x) {
		return this.twoOverPi*Math.atan(Math.exp(x));
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#supportLower()
	 */
	@Override
	public double supportLower() {
		return -14;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#supportUpper()
	 */
	@Override
	public double supportUpper() {
		return 14;
	}

}

/**
 * 
 */
package weka.estimators.density.kernels;

import java.io.Serializable;

import weka.estimators.density.Kernel;

/**
 * Logistic kernel
 * @author pawel trajdos
 * @version 1.0.0
 *
 */
public class LogisticKernel implements Kernel, Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1805998036203043501L;

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelPDFValue(double)
	 */
	@Override
	public double getKernelPDFValue(double x) {
		return 1.0/(2+ Math.exp(x) + Math.exp(-x));
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelCDFValue(double)
	 */
	@Override
	public double getKernelCDFValue(double x) {
		return 1.0 - 1.0/(Math.exp(x)+1);
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

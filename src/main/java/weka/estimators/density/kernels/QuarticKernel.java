/**
 * 
 */
package weka.estimators.density.kernels;

import java.io.Serializable;

import weka.estimators.density.Kernel;

/**
 * Quartic kernel
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.13.0
 *
 */
public class QuarticKernel implements Kernel, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6943481377417404445L;

	

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelPDFValue(double)
	 */
	@Override
	public double getKernelPDFValue(double x) {
		if(x >-1 & x< 1)
			return 0.9375*(1-x*x)*(1-x*x);
		return 0;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelCDFValue(double)
	 */
	@Override
	public double getKernelCDFValue(double x) {
		if(x<=-1) return 0;
		if(x>-1 & x<=1)
			return 0.5 + (15.0/16.0)*x - (10.0/16.0)*x*x*x + (3.0/16)*x*x*x*x*x;
		return 1;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Quartic Kernel";
	}

	@Override
	public double supportLower() {
		return -1;
	}

	@Override
	public double supportUpper() {
		return 1;
	}

}

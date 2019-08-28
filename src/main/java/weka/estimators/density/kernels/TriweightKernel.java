/**
 * 
 */
package weka.estimators.density.kernels;

import java.io.Serializable;

import weka.estimators.density.Kernel;

/**
 * Triweight kernel
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
 *
 */
public class TriweightKernel implements Kernel, Serializable {

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
			return (35.0/32.0)*(1-x*x)*(1-x*x)*(1-x*x);
		return 0;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelCDFValue(double)
	 */
	@Override
	public double getKernelCDFValue(double x) {
		if(x<=-1) return 0;
		if(x>-1 & x<=1)
			return 0.5 - (35.0/32.0)*x + (1.0/32.0)*x*x*x - (21.0/32.0)*x*x*x*x*x + (5.0/32.0)*x*x*x*x*x*x*x;
		return 1;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Triweight Kernel";
	}

}

/**
 * 
 */
package weka.estimators.density.kernels;

import java.io.Serializable;

import weka.estimators.density.Kernel;

/**
 * Gaussian kernel with faster CDF estimation
 * @author pawel trajdos
 * @since 0.12.0
 * @version 0.13.0
 *
 */
public class GaussianKernelFast implements Kernel, Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4439818887117815184L;
	
	private double a = (8*(Math.PI-3))/(3*Math.PI*(4-Math.PI));

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelPDFValue(double)
	 */
	@Override
	public double getKernelPDFValue(double x) {
		return Math.pow(Math.sqrt(2*Math.PI), -1)* Math.exp(-0.5*x*x);
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelCDFValue(double)
	 */
	@Override
	public double getKernelCDFValue(double x) {
		
		double alpha = (4.0/Math.PI + a*x*x)/(1 + a*x*x);
		double erf = Math.signum(x)*Math.sqrt(1-Math.exp(-alpha*x*x));
				 
		return erf;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Gaussian Kernel";
	}

	@Override
	public double supportLower() {
		return -4;
	}

	@Override
	public double supportUpper() {
		return 4;
	}
	
	

}

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
 * @version 1.0.0
 *
 */
public class GaussianKernelFast implements Kernel, Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4439818887117815184L;
	
	private static double a = (8.0*(Math.PI-3.0))/(3.0*Math.PI*(4.0-Math.PI));
	private double sqrtTwo = Math.sqrt(2.0);
	private static double b = Math.pow(Math.sqrt(2.0*Math.PI), -1.0);

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelPDFValue(double)
	 */
	@Override
	public double getKernelPDFValue(double x) {
		return GaussianKernelFast.b* Math.exp(-0.5*x*x);
	}
	
	/**
	 * ERF approximation from:
	 * @article{winitzki2008handy,
  		title={A handy approximation for the error function and its inverse},
  		author={Winitzki, Sergei},
  		journal={A lecture note obtained through private communication},
  		year={2008}
		}
	 * @param x
	 * @return erf estimation
	 */
	
	private double erfEstim(double x) {
		double xs = x*x;
		double axs = a*xs;
		double alpha = ((4.0/Math.PI) + axs)/(1.0 + axs);
		double erf = Math.signum(x)*Math.sqrt(1.0-Math.exp(-alpha*xs));
				 
		return erf;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelCDFValue(double)
	 */
	@Override
	public double getKernelCDFValue(double x) {
		double val = 0.5*(1.0 + this.erfEstim(x/this.sqrtTwo));
		return val;
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
		return -5.1;
	}

	@Override
	public double supportUpper() {
		return 5.1;
	}
	
	

}

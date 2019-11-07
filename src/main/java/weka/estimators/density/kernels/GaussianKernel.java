/**
 * 
 */
package weka.estimators.density.kernels;

import java.io.Serializable;

import org.apache.commons.math3.special.Erf;

import weka.estimators.density.Kernel;

/**
 * @author pawel
 *
 */
public class GaussianKernel implements Kernel, Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4439818887117815184L;

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
		return 0.5*(1+ Erf.erf(x/Math.sqrt(2.0)));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Gaussian Kernel";
	}
	
	

}

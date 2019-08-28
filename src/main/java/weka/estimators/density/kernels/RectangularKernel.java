/**
 * 
 */
package weka.estimators.density.kernels;

import java.io.Serializable;

import weka.estimators.density.Kernel;

/**
 * Rectangle kernel -- Uniform distribution on [0;1]
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
 *
 */
public class RectangularKernel implements Kernel, Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6604400066275755865L;

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelValue(double)
	 */
	@Override
	public double getKernelPDFValue(double x) {
		if(x >=-1 & x <=1) return 0.5;
		return 0;
	}

	@Override
	public double getKernelCDFValue(double x) {
		if(x<-1)return 0;
		if(x>1) return 1;
		return 0.5*(x+1);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rectangular Kernel";
	}

}

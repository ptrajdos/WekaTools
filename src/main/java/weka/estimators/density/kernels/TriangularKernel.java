/**
 * 
 */
package weka.estimators.density.kernels;

import java.io.Serializable;

import weka.estimators.density.Kernel;

/**
 * Triangular kernel
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
 *
 */
public class TriangularKernel implements Kernel, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5856076517421016587L;

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelPDFValue(double)
	 */
	@Override
	public double getKernelPDFValue(double x) {
		if(x >=-1 &  x<=1)
			return 1 - Math.abs(x);
		return 0;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelCDFValue(double)
	 */
	@Override
	public double getKernelCDFValue(double x) {
		if(x<-1) return 0;
		if(x>=-1 & x< 0)
			return ((x+1)*(x+1))/2.0;
		if(x>=0 & x < 1)
			return 1- ((1-x)*(1-x))/2.0;
		
		return 1;
	}

}

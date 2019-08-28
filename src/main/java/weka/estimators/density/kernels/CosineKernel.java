/**
 * 
 */
package weka.estimators.density.kernels;

import java.io.Serializable;

import weka.estimators.density.Kernel;

/**
 * Cosine kernel
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
 *
 */
public class CosineKernel implements Kernel, Serializable {

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
			return (Math.PI/4.0)*Math.cos((Math.PI/2)*x);
		return 0;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.Kernel#getKernelCDFValue(double)
	 */
	@Override
	public double getKernelCDFValue(double x) {
		if(x<=-1) return 0;
		if(x>-1 & x<=1)
			return 0.5 + 0.5*Math.sin((Math.PI/2)*x);
		return 1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cosine Kernel";
	}
	
	

}

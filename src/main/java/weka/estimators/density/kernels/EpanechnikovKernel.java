package weka.estimators.density.kernels;

import java.io.Serializable;

import weka.estimators.density.Kernel;

/**
 * Epanechnikov Kernel
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.13.0
 *
 */
public class EpanechnikovKernel implements Kernel, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3522344228132893688L;

	@Override
	public double getKernelPDFValue(double x) {
		if(x >-1 & x < 1)
			return 0.75*(1-x*x);
		return 0;
	}

	@Override
	public double getKernelCDFValue(double x) {
		if(x<=-1) return 0;
		if(x>-1 & x<=1)
			return -0.25*x*x*x + 0.75*x +0.5;
		return 1;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Epanechnikov Kernel";
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

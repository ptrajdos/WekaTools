package weka.estimators.density.kernels;

import java.io.Serializable;

import weka.estimators.density.Kernel;

/**
 * Epanechnikov Kernel
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
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

}

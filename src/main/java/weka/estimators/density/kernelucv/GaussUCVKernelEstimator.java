/**
 * 
 */
package weka.estimators.density.kernelucv;

import weka.estimators.density.Kernel;
import weka.estimators.density.kernels.GaussianKernel;

/**
 * @author pawel trajdos
 * @version 1.12.0
 * @since 1.12.0
 *
 */
public class GaussUCVKernelEstimator extends GeneralUCVKernelEstimator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8548717763688614732L;

	private static double squaredIntegral = 1.0/(2.0*Math.sqrt(Math.PI));
	
	private static double twoSqrtPi = 2.0*Math.sqrt(Math.PI);

	
	@Override
	protected Kernel getKernel() {
		return new GaussianKernel();
	}

	@Override
	protected double getSquaredKernelIntegral() {
		return squaredIntegral;
	}

	@Override
	protected double getKernelProductIntegral(double h, double xi, double xj) {
		
		double ximxj = xi-xj;
		double val = h/twoSqrtPi * Math.exp(- (ximxj*ximxj) /  (4.0*h*h)); 
		return val;
	}

}

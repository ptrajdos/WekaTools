/**
 * 
 */
package weka.estimators.density.kernelucv;

import weka.estimators.density.Kernel;
import weka.estimators.density.kernels.RectangularKernel;

/**
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */
public class RectangleUCVKernelEstimator extends SymmetricFiniteSupportUCVKernelEstimator {

	private static final long serialVersionUID = -4257066606781993298L;

	

	@Override
	protected Kernel getKernel() {
		return new RectangularKernel();
	}

	@Override
	protected double getSquaredKernelIntegral() {
		return 0.5;
	}

	@Override
	protected double getKernelProductIntegral(double h, double xi, double xj) {
		double c = (xi-xj)/h;
		
		if(Math.abs(c)>2)
			return 0;
		
		double val =0;
		
		if(c<0)
			val = 0.5 *( 2.0 + c);
		else
			val = 0.5* ( 2.0 - c);
		
		return val;
	}

}

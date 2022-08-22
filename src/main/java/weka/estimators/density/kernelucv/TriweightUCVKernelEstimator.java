/**
 * 
 */
package weka.estimators.density.kernelucv;

import weka.estimators.density.Kernel;
import weka.estimators.density.kernels.TriweightKernel;

/**
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */
public class TriweightUCVKernelEstimator extends SymmetricFiniteSupportUCVKernelEstimator {



	private static final long serialVersionUID = 6549124084395892799L;
	private static final double squareIntegral = 350.0/429.0;

	@Override
	protected Kernel getKernel() {
		return new TriweightKernel();
	}

	@Override
	protected double getSquaredKernelIntegral() {
		return squareIntegral;
	}

	@Override
	protected double getKernelProductIntegral(double h, double xi, double xj) {
		double c = (xi-xj)/h;
		
		if(Math.abs(c)>2)
			return 0;
		
		double val=0;
		
		if(c<0) {
			val = (175.0*Math.pow(c, 13.0) - 5460*Math.pow(c, 11.0) + 80080*Math.pow(c, 9.00) - 960960*Math.pow(c, 7) - 1921920*Math.pow(c, 6.0) + 2562560*Math.pow(c, 4.0) - 2795520*c*c + 1433600)/1757184.0;
		}else {
			val = (175.0*Math.pow(c, 13.0) - 5460*Math.pow(c, 11.0) + 80080*Math.pow(c, 9.00) - 960960*Math.pow(c, 7) + 1921920*Math.pow(c, 6.0) - 2562560*Math.pow(c, 4.0) + 2795520*c*c - 1433600)/1757184.0;
		}
		
		return val;
	}

}

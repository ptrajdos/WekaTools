/**
 * 
 */
package weka.estimators.density.kernelucv;

import weka.estimators.density.Kernel;
import weka.estimators.density.kernels.EpanechnikovKernel;

/**
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */
public class EpanechnikovUCVKernelEstimator extends SymmetricFiniteSupportUCVKernelEstimator {
	
	//TODO Fails with gaussian distribution with high div (small variance)
	//TODO pdfMiddle also fails -- integrated value is equal zero
//	Bandwidth is minimal -- probable cause of the integration to zero


	private static final long serialVersionUID = -5588207555757668023L;

	@Override
	protected Kernel getKernel() {
		return new EpanechnikovKernel();
	}

	@Override
	protected double getSquaredKernelIntegral() {
		return 0.6;
	}

	@Override
	protected double getKernelProductIntegral(double h, double xi, double xj) {
		double c = (xi-xj)/h;
		
		if(Math.abs(c)>2)
			return 0;
		
		if (c<=0) {
			double val =(3.0*Math.pow(c, 5.0) - 60.0*Math.pow(c, 3.0) - 120.0*c*c + 96.0)/160.0;
			return val;
			 
		}
		
		double val = -(3.0*Math.pow(c, 5.0) - 60.0*Math.pow(c, 3.0) + 120.0*c*c - 96.0)/160.0;
		return val;
		
	}

}

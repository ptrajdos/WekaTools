package weka.estimators.density.kernelucv;

import weka.core.KhanKleinSummator;

/**
 * UCV Kernel estimator for symmetric, finite support kernels.
 * Implemented according to:
 * @article{Scott1987,
  doi = {10.1080/01621459.1987.10478550},
  url = {https://doi.org/10.1080/01621459.1987.10478550},
  year = {1987},
  month = dec,
  publisher = {Informa {UK} Limited},
  volume = {82},
  number = {400},
  pages = {1131--1146},
  author = {David W. Scott and George R. Terrell},
  title = {Biased and Unbiased Cross-Validation in Density Estimation},
  journal = {Journal of the American Statistical Association}
}
	Formula 3.11 in the paper.
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */

public abstract class SymmetricFiniteSupportUCVKernelEstimator extends GeneralUCVKernelEstimator {


	private static final long serialVersionUID = -615893742190025753L;

	@Override
	protected double getUCV(double h) {
		
		double[] vals =  this.weiValHold.getValues();
		int numPoints = this.weiValHold.getNumVals();
		
		double RK = this.getSquaredKernelIntegral()/(numPoints*h);
		
		
		
		KhanKleinSummator ktmpSum = new KhanKleinSummator();
		for(int i=0;i<numPoints;i++)
			for(int j=i+1;j<numPoints;j++) {
				
					ktmpSum.addToSum(this.getKernelProductIntegral(h, vals[i], vals[j]));
					ktmpSum.addToSum(-2.0*this.kernel.getKernelPDFValue((vals[i]- vals[j])/h));
			}
		
		double ucv = RK + 2.0*ktmpSum.getSum()/(numPoints*numPoints*h);
		
		return ucv;
		
	}

	

}

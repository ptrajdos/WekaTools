/**
 * 
 */
package weka.estimators.density.bandwidthFinders;

import weka.core.UtilsPT;
import weka.estimators.density.Kernel;
import weka.tools.numericIntegration.Function;
import weka.tools.numericIntegration.Integrator;
import weka.tools.numericIntegration.SimpsonsIntegrator;

/**
 * The class implements bandwidth selection using Maximal Smoothing principle.
 * Implementation according to paper
 * 
 * @article{Terrell1990,
  doi = {10.1080/01621459.1990.10476223},
  url = {https://doi.org/10.1080/01621459.1990.10476223},
  year = {1990},
  month = jun,
  publisher = {Informa {UK} Limited},
  volume = {85},
  number = {410},
  pages = {470--477},
  author = {George R. Terrell},
  title = {The Maximal Smoothing Principle in Density Estimation},
  journal = {Journal of the American Statistical Association}

 * @author pawel trajdos
 * @since 0.13.0
 * @version 0.13.0
 *
 */
public class MaximalSmoothingPrincipleBandwidthSelectionKernel extends SimpleBandwidthFinder {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2498101140294446576L;

	/* (non-Javadoc)
	 * @see weka.estimators.density.BandwidthSelectionKernelEstimator#findBandwidth()
	 */
	@Override
	protected void findBandwidth() {
		double[] sample = this.kernEstim.getValues();
		double sampleSD  = UtilsPT.stdDev(sample);
		int sampleSize = sample.length;
		double kernelSD = this.findKernelSD();
		double kernelSQ = this.findKernelSquareIntegral();
		
		double h = this.scaleFactor* 3.0 * Math.pow(35*sampleSize, -0.2) *sampleSD * Math.pow(kernelSD, -0.8) * Math.pow(kernelSQ, 0.2);
		if(h<this.minH)
			h=this.minH;
		this.kernEstim.setBandwidth(h);

	}
	
	private double findKernelSD() {
		Function fun = new Function() {
			@Override
			public double getValue(double argument) {
				Kernel kern = kernEstim.getKernel();
				return argument*argument*kern.getKernelPDFValue(argument);
			}
		};
		
		Integrator integr  = new SimpsonsIntegrator();
		integr.setFunction(fun);
		
		Kernel kern = this.kernEstim.getKernel();
		integr.setLowerBound(kern.supportLower());
		integr.setUpperBound(kern.supportUpper());
		return integr.integrate();
	}
	
	private double findKernelSquareIntegral() {
		Function fun = new Function() {
			@Override
			public double getValue(double argument) {
				Kernel kern = kernEstim.getKernel();
				double val = kern.getKernelPDFValue(argument);
				return val*val;
			}
		};
		
		Integrator integr  = new SimpsonsIntegrator();
		integr.setFunction(fun);
		
		Kernel kern = this.kernEstim.getKernel();
		integr.setLowerBound(kern.supportLower());
		integr.setUpperBound(kern.supportUpper());
		return integr.integrate();
	}

}

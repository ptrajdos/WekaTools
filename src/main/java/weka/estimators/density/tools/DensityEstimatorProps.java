/**
 * 
 */
package weka.estimators.density.tools;

import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;

import weka.estimators.density.DensityEstimator;
import weka.tools.numericIntegration.Function;
import weka.tools.numericIntegration.Integrator;
import weka.tools.numericIntegration.SimpsonsIntegrator;

/**
 * Calculates various properties of density estimators
 * @author pawel trajdos
 *
 */
public class DensityEstimatorProps {

	public static int NUM_SAMPLES=1000;
	

	private static Integrator getIntegr(double lower, double upper) {
		SimpsonsIntegrator inte = new SimpsonsIntegrator();
		inte.setSequenceLength(NUM_SAMPLES);
		inte.setLowerBound(lower);
		inte.setUpperBound(upper);
		return inte;
	}
	
	public static double getMoment(final DensityEstimator estim, double lower, double upper, final double order) throws Exception {
		Integrator inte = getIntegr(lower, upper);
		inte.setFunction(new Function() {
			
			@Override
			public double value(double x) {
				return Math.pow(x, order)*estim.getPDF(x);
			}
		});
		
		return inte.integrate();	
	}
	
	public static double getCentralMoment(final DensityEstimator estim, double lower, double upper, final double order) throws Exception {
		Integrator inte = getIntegr(lower, upper);
		final double expV = getExpectedValue(estim, lower, upper);
		inte.setFunction(new Function() {
			
			@Override
			public double value(double x) {
				return Math.pow(x-expV, order)*estim.getPDF(x);
			}
		});
		
		return inte.integrate();	
	}
	
	

	public static double getStandarizedMoment(final DensityEstimator estim, double lower, double upper, final double order) throws Exception {
		Integrator inte = getIntegr(lower, upper);
		final double expV = getExpectedValue(estim, lower, upper);
		final double sdev = Math.sqrt(getVariance(estim, lower, upper));
		inte.setFunction(new Function() {
			
			@Override
			public double value(double x) {
				return Math.pow((x-expV)/sdev , order)*estim.getPDF(x);
			}
		});
		
		return inte.integrate();	
	}
	
	
	
	public static double getExpectedValue(final DensityEstimator estim, double lower, double upper) throws Exception {
		
		Integrator inte = getIntegr(lower, upper);
		inte.setFunction(new Function() {
			
			@Override
			public double value(double x) {
				return x*estim.getPDF(x);
			}
		});
		
		return inte.integrate();
		
	}
	
	public static double getSecMoment(final DensityEstimator estim, double lower, double upper) throws Exception {
	
		double expVal = getExpectedValue(estim, lower, upper);
		Integrator inte = getIntegr(lower, upper);
		inte.setFunction(new Function() {
			
			@Override
			public double value(double x) {
				return x*x*estim.getPDF(x);
			}
		});
		
		double secMom = inte.integrate();
		return secMom;
	}
	
	public static double getVariance(final DensityEstimator estim, double lower, double upper) throws Exception {
		
		double secM = getSecMoment(estim, lower, upper);
		double expVal = getExpectedValue(estim, lower, upper);
		double var = secM - expVal*expVal;
		return var;
		
	}
	

}

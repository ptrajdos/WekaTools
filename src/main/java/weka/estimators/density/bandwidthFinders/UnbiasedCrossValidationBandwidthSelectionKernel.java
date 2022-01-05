/**
 * 
 */
package weka.estimators.density.bandwidthFinders;

import weka.core.Utils;
import weka.core.UtilsPT;
import weka.estimators.density.Kernel;
import weka.tools.Linspace;
import weka.tools.numericIntegration.Function;
import weka.tools.numericIntegration.Integrator;
import weka.tools.numericIntegration.SimpsonsIntegrator;

/**
 * The class implements bandwidth selection using the Unbiased Cross-validation technique.
 * Implementation according to:
 * @article{scott1987biased,
  	title={Biased and unbiased cross-validation in density estimation},
  	author={Scott, David W and Terrell, George R},
  	journal={Journal of the american Statistical association},
  	volume={82},
  	number={400},
  	pages={1131--1146},
  	year={1987},
  	publisher={Taylor \& Francis}
}
 * 
 * @author pawel trajdos
 * @since 1.12.0
 * @version 1.12.0
 * 
 * WARNING: Uses numerical integration  -- method is very slow
 *
 */
public class UnbiasedCrossValidationBandwidthSelectionKernel extends SimpleBandwidthFinder {

	/**
	 * For serialization 
	 */
	private static final long serialVersionUID = -5239366383396532356L;
	
	protected double kernelSquareIntegral=0;
	
	protected double xMinRoi=0;
	protected double xMaxRoi=0;
	
	protected int valsToCheck = 100;


	@Override
	protected void findBandwidth() {
		double defaultBandwidth = this.defaultBandwidth();
		try {
			this.kernelSquareIntegral = this.findKernelSquareIntegral();
			this.findROI();
			
			double[] hValToCheck = Linspace.genLinspace(this.minH, defaultBandwidth, this.valsToCheck);
			double[] ucvs = this.getUcvs(hValToCheck);
			
			double bestH = hValToCheck[Utils.minIndex(ucvs)];
			this.kernEstim.setBandwidth(bestH);
			
		} catch (Exception e) {
			this.kernEstim.setBandwidth(defaultBandwidth);
		}

	}
	

	/**
	 * calculates the default bandwidth using Silvermann rule of thumb.
	 */
	protected double defaultBandwidth() {
		double[] vals = this.getValues();
		double sd = UtilsPT.stdDev(vals);
		double iqr = (UtilsPT.quantile(vals, 0.75) - UtilsPT.quantile(vals, 0.25))/1.34;
		double h = this.scaleFactor*0.9*Math.min(sd, iqr) * Math.pow(vals.length, -1/5);
		h = Math.max(h, this.minH);
		
		return h;
	}
	
	
	protected void findROI() {
		double[] vals = this.getValues();
		Kernel kern = this.kernEstim.getKernel();
		
		this.xMinRoi = vals[Utils.minIndex(vals)];
		this.xMinRoi-= Math.abs(kern.supportLower());
		
		this.xMaxRoi = vals[Utils.maxIndex(vals)];
		this.xMaxRoi+= Math.abs(kern.supportUpper());
		
	}
	
	double[] getUcvs(double[] hValsToCheck)throws Exception {
		double[] ucvs = new double[hValsToCheck.length];
		for(int i =0;i<ucvs.length;i++)
			ucvs[i] = this.ucv(hValsToCheck[i]);
		
		return ucvs;
	}
	
	protected double ucv(double h)throws Exception {
		
		double[] values = this.kernEstim.getValues();
		int pointNum = values.length;
		
		double RK = this.kernelSquareIntegral/(pointNum*h);
		
		double tmpIntegr=0;
		double ucv=0;
		for(int i=0;i<pointNum;i++)
			for(int j=i;j<pointNum;j++) {
				tmpIntegr = this.kernProdIntegrate(values[i], values[j]);
				tmpIntegr/= pointNum*pointNum*h*h;
				ucv+= tmpIntegr - 
						(2.0/(pointNum*(pointNum-1)*h)*this.kernEstim.getKernel().getKernelPDFValue((values[i] - values[j])/h ));
			}
		
		ucv+=RK;
		
		return ucv;
	}
	
	private Function createIntegrationFunction(final double xi, final double xj) {
		Function fun = new Function() {
			@Override
			public double value(double argument) {
				Kernel kern = kernEstim.getKernel();
				double val = kern.getKernelPDFValue(argument -xi)*kern.getKernelPDFValue(argument -xj);
				return val;
			}
		};
		return fun;
		
	}
	
	private double kernProdIntegrate(final double xi, final double xj) throws Exception {
		Function fun =createIntegrationFunction(xi, xj);
		
		SimpsonsIntegrator integr  = new SimpsonsIntegrator();
		integr.setFunction(fun);
		integr.setSequenceLength(100);
		
		
		integr.setLowerBound(this.xMinRoi);
		integr.setUpperBound(this.xMaxRoi);
		return integr.integrate();
		
	}
	
	private double findKernelSquareIntegral() throws Exception {
		Function fun = new Function() {
			@Override
			public double value(double argument) {
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


	/**
	 * @return the valsToCheck
	 */
	public int getValsToCheck() {
		return valsToCheck;
	}


	/**
	 * @param valsToCheck the valsToCheck to set
	 */
	public void setValsToCheck(int valsToCheck) {
		this.valsToCheck = valsToCheck;
	}
	
	public String valsToCheckTipText() {
		return "Number of values to check during crossvalidation";
	}

}

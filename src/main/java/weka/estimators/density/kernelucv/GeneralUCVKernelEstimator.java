/**
 * 
 */
package weka.estimators.density.kernelucv;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.KhanKleinSummator;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.core.UtilsPT;
import weka.estimators.density.BasicKernelDensityEstimator;
import weka.estimators.density.DensityEstimator;
import weka.estimators.density.Kernel;
import weka.tools.Linspace;
import weka.tools.WeightedValuesHolder;

/**
 * Kernel that uses unbiased cross-validation approach. 
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
 * @author pawel trajdos
 * @since 1.12.0
 * @version 1.12.0
 *
 */
public abstract class GeneralUCVKernelEstimator implements BasicKernelDensityEstimator, Serializable, OptionHandler {
	
	private static final long serialVersionUID = -6943481377417404445L;
	
	/**
	 * Kernel used to estimate
	 */
	protected Kernel kernel;
	/**
	 * Stores data points and its weights
	 */
	protected WeightedValuesHolder weiValHold = new WeightedValuesHolder();
	
	protected double bandwidth = 1.0;
	
	protected boolean needBWCalc = true;

	/**
	 * 
	 */
	public GeneralUCVKernelEstimator() {
		this.kernel = this.getKernel();
	}
	
	
	protected abstract double getSquaredKernelIntegral();
	
	protected abstract double getKernelProductIntegral(double h,double xi, double xj);
	
	protected int numBandwidthsToTest = 100;
	
	protected double getMinBandwidth() {
		return 1E-6;
	}
	
	protected double getMaxBandwidth() {
		double[] vals = this.getValues();
		if(vals.length < 1) {
			return 1.0;
		}
		double sd = UtilsPT.stdDev(vals);
		double iqr = (UtilsPT.quantile(vals, 0.75) - UtilsPT.quantile(vals, 0.25))/1.34;
		double h = 0.9*Math.min(sd, iqr) * Math.pow(vals.length, -1.0/5.0);
		h = Math.max(h, this.getMinBandwidth());
		return 10.0 * h;
	}
	
	protected double getUCV(double h) {
		
		
		double[] vals =  this.weiValHold.getValues();
		int numPoints = this.weiValHold.getNumVals();
		
		double RK = this.getSquaredKernelIntegral()/(numPoints*h);
		
		
		
		KhanKleinSummator ktmpSum = new KhanKleinSummator();
		for(int i=0;i<numPoints;i++)
			for(int j=0;j<numPoints;j++) {
				if (i==j)
					continue;
					ktmpSum.addToSum(this.getKernelProductIntegral(h, vals[i], vals[j])/(numPoints*numPoints*h*h));
					ktmpSum.addToSum(-2.0/(numPoints*(numPoints-1)*h)*this.kernel.getKernelPDFValue((vals[i]- vals[j])/h));
			}
		
		double ucv = RK + ktmpSum.getSum();
		
		return ucv;
	}
	
	protected void findBandwidth() {
		
		double[] bandwidthsToTest = Linspace.genLinspace(this.getMinBandwidth(), this.getMaxBandwidth(), this.numBandwidthsToTest);
		double[] ucvs = new double[bandwidthsToTest.length];
		
		for(int i=0;i<ucvs.length;i++)
			ucvs[i] = this.getUCV(bandwidthsToTest[i]);
		
		this.bandwidth = bandwidthsToTest[Utils.minIndex(ucvs)];
		
	}

	@Override
	public void addValue(double data, double weight) {
		this.weiValHold.addValue(data, weight);
		this.needBWCalc=true;
	}


	@Override
	public double getPDF(double x) {
		
		if(this.needBWCalc) {
			this.findBandwidth();
			this.needBWCalc = false;
		}
		
		int numVals = this.weiValHold.getNumVals();
		
		KhanKleinSummator kEstimation = new KhanKleinSummator();
		for(int i=0;i<numVals;i++) {
			kEstimation.addToSum(this.kernel.getKernelPDFValue(  (x-this.weiValHold.getValue(i))/this.bandwidth  ));
		}
		double estimation = kEstimation.getSum();
		estimation/=numVals*this.bandwidth;
		return estimation;
	}

	@Override
	public double getCDF(double x) {
		
		if(this.needBWCalc) {
			this.findBandwidth();
			this.needBWCalc = false;
		} 
		
		int numVals = this.weiValHold.getNumVals();
		KhanKleinSummator kEstimation = new KhanKleinSummator();
		
		for(int i=0;i<numVals;i++)
			kEstimation.addToSum(this.kernel.getKernelCDFValue(  (x-this.weiValHold.getValue(i))/this.bandwidth  ));
		
		double estimation =kEstimation.getSum();
		estimation/=numVals;
		
		return estimation;
	}

	@Override
	public void addValues(double[] data, double[] weight) {
		this.weiValHold.addValues(data, weight);
	}

	@Override
	public double[] getValues() {
		return this.weiValHold.getValues();
	}

	@Override
	public double[] getWeights() {
		return this.weiValHold.getWeights();
	}
	/**
	 * @return the numBandwidthsToTest
	 */
	public int getNumBandwidthsToTest() {
		return numBandwidthsToTest;
	}
	/**
	 * @param numBandwidthsToTest the numBandwidthsToTest to set
	 */
	public void setNumBandwidthsToTest(int numBandwidthsToTest) {
		this.numBandwidthsToTest = numBandwidthsToTest;
	}
	
	public String numBandwidthsToTestTipText() {
		return "The number of bandwidth values to test";
	}
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tBandwidth to use "+
		          "(default: 100).\n",
			      "NBW", 1, "-NBW"));
		
	    
		return newVector.elements();
	}
	@Override
	public void setOptions(String[] options) throws Exception {
		this.setNumBandwidthsToTest(UtilsPT.parseIntegerOption(options, "NBW", 100));
		
		this.needBWCalc = true;
		
	}
	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-NBW");
		options.add(""+ this.getNumBandwidthsToTest());
		
		
	    return options.toArray(new String[0]);
	}
	
	@Override
	public void reset() {
		this.weiValHold.reset();
	}
	
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		
		strb.append("UCVKernelEstimator:\n");
		strb.append("Selected Bandwidth: " + this.bandwidth);
		
		return strb.toString();
	}
	
	@Override
	public double getBandwidth() {
		return this.bandwidth;
	}
	
	
	
	

}

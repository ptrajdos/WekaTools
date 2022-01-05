/**
 * 
 */
package weka.estimators.density;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;

/**
 * Estimator with cached values of PDFs and CDFs.
 * Needed for speed reasons
 * @author pawel trajdos
 * @since 0.13.0
 * @version 0.13.0
 *
 */
public class CachedEstimator implements DensityEstimator, Serializable, OptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5064176625064529202L;
	
	protected DensityEstimator densEstim;
	
	protected HashMap<Double, Double> pdfHash;
	
	protected HashMap<Double, Double> cdfHash;
	

	/**
	 * 
	 */
	public CachedEstimator() {
		this.densEstim = new SimpleKernelEstimator();
		this.pdfHash = new HashMap<Double, Double>();
		this.cdfHash = new HashMap<Double, Double>();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.IncrementalEstimator#addValue(double, double)
	 */
	@Override
	public void addValue(double data, double weight) {
		this.densEstim.addValue(data, weight);
		this.pdfHash.clear();
		this.cdfHash.clear();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getPDF(double)
	 */
	@Override
	public double getPDF(double x) {
		Double pdfO = this.pdfHash.get(x);
		double pdf =0;
		if(pdfO == null) {
			pdf = this.densEstim.getPDF(x);
			this.pdfHash.put(x, pdf);
			return pdf;
		}
		return pdfO.doubleValue();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getCDF(double)
	 */
	@Override
	public double getCDF(double x) {
		Double cdfO = this.cdfHash.get(x);
		double cdf =0;
		if(cdfO == null) {
			cdf = this.densEstim.getCDF(x);
			this.cdfHash.put(x, cdf);
			return cdf;
		}
		return cdfO.doubleValue();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#addValues(double[], double[])
	 */
	@Override
	public void addValues(double[] data, double[] weight) {
		this.densEstim.addValues(data, weight);
		this.pdfHash.clear();
		this.cdfHash.clear();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getValues()
	 */
	@Override
	public double[] getValues() {
		return this.densEstim.getValues();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getWeights()
	 */
	@Override
	public double[] getWeights() {
		return this.densEstim.getWeights();
	}

	/**
	 * @return the densEstim
	 */
	public DensityEstimator getDensEstim() {
		return this.densEstim;
	}

	/**
	 * @param densEstim the densEstim to set
	 */
	public void setDensEstim(DensityEstimator densEstim) {
		this.densEstim = densEstim;
	}
	
	public String densEstimTipText() {
		return "Returns the density estimator used.";
	}

	@Override
	public Enumeration<Option> listOptions() {
Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tDensity Estimator to use "+
		          "(default: weka.estimators.density.SimpleKernelEstimator).\n",
			      "EST", 1, "-EST"));

		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		this.setDensEstim((DensityEstimator) UtilsPT.parseObjectOptions(options, "EST", new SimpleKernelEstimator(), DensityEstimator.class));
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-EST");
		options.add(UtilsPT.getClassAndOptions(this.getDensEstim()));
		
	    return options.toArray(new String[0]);
	}
	
	

}

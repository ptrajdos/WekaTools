/**
 * 
 */
package weka.estimators.density.bandwidthFinders;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.UtilsPT;
import weka.estimators.density.BandwidthSelectionKernelEstimator;

/**
 * @since 0.13.0
 * @version 0.13.0
 * @author pawel trajdos
 *
 */
public abstract class SimpleBandwidthFinder extends BandwidthSelectionKernelEstimator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9143463588428060204L;
	/**
	 * Min allowed bandwidth
	 */
	protected double minH =0.01;
	/**
	 * The scale factor used to sale Silverman's bandwidth
	 */
	protected double scaleFactor=1.0;
	
	/**
	 * Returns min allowed bandwidth
	 * @return the minH
	 */
	public double getMinH() {
		return this.minH;
	}
	/**
	 * Sets min allowed bandwidth
	 * @param minH the minH to set
	 */
	public void setMinH(double minH) {
		this.minH = minH;
	}
	
	public String minHTipText() {
		return "Minimal value of bandwidth";
	}
	/**
	 * Gets the scale factor used to sale the bandwidth found
	 * @return the scaleFactor
	 */
	public double getScaleFactor() {
		return this.scaleFactor;
	}
	/**
	 * Sets the scale factor used to sale the bandwidth found
	 * @param scaleFactor the scaleFactor to set
	 */
	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}
	
	public String scaleFactorTipText() {
		return "The scale factor used to sale the bandwidth found";
	}
	
	/* (non-Javadoc)
	 * @see weka.estimators.density.BandwidthSelectionKernelEstimator#listOptions()
	 */
	@Override
	public Enumeration<Option> listOptions() {
		Vector<Option> newVector = new Vector<Option>(1);
		
		 newVector.addElement(new Option(
			      "\tThe min value of bandwidth"+
		          "(default:" +0.01 +  ").\n",
			      "MH", 1, "-MH"));
		 
		 newVector.addElement(new Option(
			      "\tScale factor to use with the Silverman rule"+
		          "(default:" +1.0 +  ").\n",
			      "SF", 1, "-SF"));
		 
		 newVector.addAll(Collections.list(super.listOptions()));
		    
		return newVector.elements();
	}


	/* (non-Javadoc)
	 * @see weka.estimators.density.BandwidthSelectionKernelEstimator#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setMinH(UtilsPT.parseDoubleOption(options, "MH", 0.01));
		this.setScaleFactor(UtilsPT.parseDoubleOption(options, "SF", 1.0));
		super.setOptions(options);
	}


	/* (non-Javadoc)
	 * @see weka.estimators.density.BandwidthSelectionKernelEstimator#getOptions()
	 */
	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
	    

	    options.add("-MH");
	    options.add(""+ this.getMinH());
	    
	    options.add("-SF");
	    options.add(""+ this.getScaleFactor());
	    
	    Collections.addAll(options, super.getOptions());
	    
	    return options.toArray(new String[0]);
	}
	
	
}

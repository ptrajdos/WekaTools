/**
 * 
 */
package weka.estimators.density.histogram.bin;

/**
 * An interface for a bin used in histogram
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */
public interface IBin {
	
	/**
	 * Gets Bin lower bound
	 * @return
	 */
	public double getLowerBound();
	
	/**
	 * Gets Bin upper bound
	 * @return
	 */
	
	public double getUpperBound();
	
	/**
	 * Get number of points/sum of weights in bin
	 * @return
	 */
	
	public double getCount();
	
	/**
	 * Gets the width of the bin.
	 * @return
	 */
	
	public double getWidth();
	
	/**
	 * Gets the center of the bin
	 * @return
	 */
	
	public double getBinCenter();
	
	
	/**
	 * Increments the bin cound by given value
	 * @param val
	 */
	
	public void addValue(double val);
	
	public boolean isValueInBin(double val);

}

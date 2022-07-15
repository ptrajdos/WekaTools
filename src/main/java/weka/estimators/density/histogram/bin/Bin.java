/**
 * 
 */
package weka.estimators.density.histogram.bin;

import java.io.Serializable;

/**
 * A class implementing IBin interface.
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */
public class Bin implements IBin, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4700928926631031009L;
	
	private double lower;
	private double upper;
	private double count = 0.0;

	/**
	 * 
	 */
	public Bin(double lower, double upper) {
		this.lower = lower;
		this.upper = upper;
	}

	@Override
	public double getLowerBound() {
		return this.lower;
	}

	@Override
	public double getUpperBound() {
		return this.upper;
	}

	@Override
	public double getCount() {
		return this.count;
	}

	@Override
	public double getWidth() {
		return this.upper - this.lower;
	}

	@Override
	public double getBinCenter() {
		return 0.5*(this.lower + this.upper);
	}

	@Override
	public void addValue(double val) {
		this.count+=val;
	}

	@Override
	public boolean isValueInBin(double val) {
		if(val > this.lower && val <= this.upper)
			return true;
		return false;
	}

}

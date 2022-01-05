/**
 * 
 */
package weka.tools.data;

import java.io.Serializable;
import java.util.Random;

/**
 * @author pawel trajdos
 * @since 1.10.0
 * @version 1.10.0
 *
 */
public abstract class RandomDoubleGenerator implements IRandomDoubleGenerator, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -310796918290664577L;
	private int seed=0;
	protected Random rnd = new Random(seed); 
	protected double offset=0;
	protected double divisor=1;

	

	@Override
	public void setSeed(int seed) {
		this.seed = seed;
		this.rnd.setSeed(seed);
	}

	@Override
	public int getSeed() {
		return this.seed;
	}

	/**
	 * @return the offset
	 */
	public double getOffset() {
		return this.offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(double offset) {
		this.offset = offset;
	}

	/**
	 * @return the divisor
	 */
	public double getDivisor() {
		return this.divisor;
	}

	/**
	 * @param divisor the divisor to set
	 */
	public void setDivisor(double divisor) {
		this.divisor = divisor;
	}
	
	

}

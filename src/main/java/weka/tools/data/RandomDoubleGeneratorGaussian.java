/**
 * 
 */
package weka.tools.data;

/**
 * @author pawel trajdos
 * @since 1.10.0
 * @version 1.10.0
 *
 */
public class RandomDoubleGeneratorGaussian extends RandomDoubleGenerator {


	@Override
	public double getNextDouble() {
		return this.rnd.nextGaussian()/this.divisor + this.offset;
	}

}

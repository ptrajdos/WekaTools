package weka.tools.data;
/**
 * 
 * @author pawel trajdos
 * @since 1.10.0
 * @version 1.10.0
 *
 */
public class RandomDoubleGeneratorUniform extends RandomDoubleGenerator {


	@Override
	public double getNextDouble() {
		return this.rnd.nextDouble()/this.divisor + this.offset;
	}

}

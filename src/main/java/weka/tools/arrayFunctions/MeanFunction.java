/**
 * 
 */
package weka.tools.arrayFunctions;

import weka.core.Utils;

/**
 * @author pawel trajdos
 * @version 2.1.0
 * @since 2.1.0
 *
 */
public class MeanFunction extends AFunction {

	private static final long serialVersionUID = 7726415699095299636L;

	@Override
	public double value(double[] point) {
		return Utils.mean(point);
	}

}

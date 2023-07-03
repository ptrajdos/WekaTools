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
public class MinFunction extends AFunction {

	private static final long serialVersionUID = -2394672736145447480L;

	@Override
	public double value(double[] point) {
		int minIdx = Utils.minIndex(point);
		return point[minIdx];
	}

}

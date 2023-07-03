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
public class MaxFunction extends AFunction {

	private static final long serialVersionUID = 7400478138967938795L;

	@Override
	public double value(double[] point) {
		int maxIdx = Utils.maxIndex(point);
		return point[maxIdx];
	}

}

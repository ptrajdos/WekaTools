/**
 * 
 */
package weka.tools.arrayFunctions;

import weka.core.Utils;
import weka.core.UtilsPT;

/**
 * @author pawel trajdos
 * @version 2.1.0
 * @since 2.1.0
 *
 */
public class SoftMinFunction extends AFunction {

	private static final long serialVersionUID = -1463926107417877316L;

	@Override
	public double value(double[] point) {
		double[] spoint = UtilsPT.softMin(point);
		int minIdx = Utils.minIndex(spoint);
		return spoint[minIdx];
	}

}

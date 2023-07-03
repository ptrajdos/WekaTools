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
public class SoftMaxFunction extends AFunction {

	private static final long serialVersionUID = -4223740007997453498L;

	@Override
	public double value(double[] point) {
		double[] spoint = UtilsPT.softMax(point);
		int maxIdx = Utils.maxIndex(spoint);
		return spoint[maxIdx];
	}

}

/**
 * 
 */
package weka.tools.arrayFunctions;

import weka.core.UtilsPT;

/**
 * @author pawel trajdos
 * @version 2.1.0
 * @since 2.1.0
 * 
 *
 */
public class MedianFunction extends AFunction {

	private static final long serialVersionUID = -3807699575717388254L;

	@Override
	public double value(double[] point) {
		return UtilsPT.median(point);
	}

}

/**
 * 
 */
package weka.tools.numericIntegration;

import java.io.Serializable;

/**
 * Dummy function
 * @author pawel trajdos
 * @since 0.10.0
 * @version 0.10.0
 *
 */
public class DummyFunction implements Function, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8131414675483062329L;

	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.Function#getValue(double)
	 */
	@Override
	public double value(double argument) {
		return 1.0;
	}

}

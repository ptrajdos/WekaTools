/**
 * 
 */
package weka.tools.numericIntegration;

/**
 * An interface for functions to be integrated
 * @author pawel trajdos
 * @since 0.10.0
 * @version 0.10.0
 *
 */
public interface Function {
	
	/**
	 * Value of the function
	 * @param argument
	 * @return
	 */
	public double getValue(double argument);

}

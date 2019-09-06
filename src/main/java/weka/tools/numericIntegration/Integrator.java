/**
 * 
 */
package weka.tools.numericIntegration;

/**
 * An interface for classes performing numeric integration
 * @author pawel trajdos
 * @since 0.10.0
 * @version 0.10.0
 *
 */
public interface Integrator {
	
	/**
	 * Set the lower bound for the integration
	 * @param lower
	 */
	public void setLowerBound(double lower);
	
	/**
	 * Get the lower bound for the integration
	 * @return
	 */
	public double getLowerBound();
	
	/**
	 * Set the upper bound for the integration
	 * @param lower
	 */
	public void setUpperBound(double upper);
	
	/**
	 * Get the upper bound for the integration
	 * @return
	 */
	public double getUpperBound();
	
	/**
	 * Set the function to integrate;
	 * @param f
	 * @return
	 */
	public void setFunction(Function f);
	
	/**
	 * Get the integration function
	 * @return
	 */
	public Function getFunction();
	
	/**
	 * Calculate the integral \int_{lower_bound}^{upper_bound}function(x)dx
	 * @return
	 */
	public double integrate();

}

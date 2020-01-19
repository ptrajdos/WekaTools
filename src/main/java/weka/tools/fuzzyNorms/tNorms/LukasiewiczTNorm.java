/**
 * 
 */
package weka.tools.fuzzyNorms.tNorms;

/**
 * Calculate the Lukasiewicz norm for two values
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class LukasiewiczTNorm extends FuzzyTNorm {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4969287739183694328L;

	/* (non-Javadoc)
	 * @see mLWorkhorse.Tools.FuzzyNorms.FuzzyNorm#calculateNorm(double, double)
	 */
	@Override
	public double calculateNorm(double a, double b) {
		return Math.max(0, a+b-1);
	}

}

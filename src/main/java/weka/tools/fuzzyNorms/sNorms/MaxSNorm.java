/**
 * 
 */
package weka.tools.fuzzyNorms.sNorms;

/**
 * Calculate the Max norm for two values
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class MaxSNorm extends FuzzySNorm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1120209647451954374L;

	/**
	 * 
	 */
	public MaxSNorm() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see mLWorkhorse.Tools.FuzzyNorms.FuzzyNorm#calculateNorm(double, double)
	 */
	@Override
	public double calculateNorm(double a, double b) {
		return Math.max(a, b);
	}

}

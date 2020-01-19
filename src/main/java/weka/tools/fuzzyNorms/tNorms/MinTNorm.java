/**
 * 
 */
package weka.tools.fuzzyNorms.tNorms;

/**
 * Calculate the Min norm for two values
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class MinTNorm extends FuzzyTNorm {

	/**
	 * 
	 */
	public MinTNorm() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see mLWorkhorse.Tools.FuzzyNorms.FuzzyNorm#calculateNorm(double, double)
	 */
	@Override
	public double calculateNorm(double a, double b) {
		return Math.min(a, b);
	}

}

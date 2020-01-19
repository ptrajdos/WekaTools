/**
 * 
 */
package weka.tools.fuzzyNorms.sNorms;

/**
 * Calculate the Lukasiewicz norm for two values
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class LukasiewiczSNorm extends FuzzySNorm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4054006299871045921L;

	/**
	 * 
	 */
	public LukasiewiczSNorm() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see mLWorkhorse.Tools.FuzzyNorms.FuzzyNorm#calculateNorm(double, double)
	 */
	@Override
	public double calculateNorm(double a, double b) {
		return Math.min(1, a+b);
	}

}

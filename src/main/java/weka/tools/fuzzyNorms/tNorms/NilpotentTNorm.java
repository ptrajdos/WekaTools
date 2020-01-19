/**
 * 
 */
package weka.tools.fuzzyNorms.tNorms;

/**
 * Calculate the Nilpotent norm for two values
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class NilpotentTNorm extends FuzzyTNorm {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5156002534900503709L;

	/* (non-Javadoc)
	 * @see mLWorkhorse.Tools.FuzzyNorms.FuzzyNorm#calculateNorm(double, double)
	 */
	@Override
	public double calculateNorm(double a, double b) {
		if(a+b>1)return Math.min(a, b);
		return 0;
	}

}

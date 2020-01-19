/**
 * 
 */
package weka.tools.fuzzyNorms.tNorms;

/**
 * Calculate the product norm for two values
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class ProdTNorm extends FuzzyTNorm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2896635676216609089L;

	/**
	 * 
	 */
	public ProdTNorm() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see mLWorkhorse.Tools.FuzzyNorms.FuzzyNorm#calculateNorm(double, double)
	 */
	@Override
	public double calculateNorm(double a, double b) {
		return a*b;
	}

}

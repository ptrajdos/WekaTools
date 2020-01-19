/**
 * 
 */
package weka.tools.fuzzyNorms.sNorms;

/**
 * Calculate the Hamaher norm for two values
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class HamaherSNorm extends FuzzySNorm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3785079969151819167L;

	/**
	 * 
	 */
	public HamaherSNorm() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see mLWorkhorse.Tools.FuzzyNorms.FuzzyNorm#calculateNorm(double, double)
	 */
	@Override
	public double calculateNorm(double a, double b) {
		double denominator = 1-a*b;
		if(denominator <eps)return 1.0;
		
		
		return (a+b-2*a*b)/denominator;
	}

}

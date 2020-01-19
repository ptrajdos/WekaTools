package weka.tools.fuzzyNorms.tNorms;

/**
 * Calculate the Hamaher norm for two values
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */

public class HamaherTNorm extends FuzzyTNorm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -574475415421420092L;

	@Override
	public double calculateNorm(double a, double b) {
		if((a<eps) & (b <eps))return 0;
		return(a*b)/(a+b-a*b);
	}

}

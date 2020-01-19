/**
 * 
 */
package weka.tools.fuzzyNorms.tNorms;

/**
 * @author pawel
 *
 */
public class DrasticTNorm extends FuzzyTNorm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5352389944539892895L;

	/* (non-Javadoc)
	 * @see mLWorkhorse.Tools.FuzzyNorms.FuzzyNorm#calculateNorm(double, double)
	 */
	@Override
	public double calculateNorm(double a, double b) {
		if(1-a < eps){return b;}
		if(1-b < eps){return a;}
		return 0.0;
	}

}

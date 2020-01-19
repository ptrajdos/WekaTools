package weka.tools.fuzzyNorms;

/**
 * Interface for calculating fuzzy norms of two values
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public interface FuzzyNorm {
	
	/**
	 * Calculates fuzzy norm of two values
	 * @param a - first value
	 * @param b - second value
	 * @return
	 */
	public double calculateNorm(double a, double b);

}

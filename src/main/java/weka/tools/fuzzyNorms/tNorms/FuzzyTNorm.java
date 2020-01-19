/**
 * 
 */
package weka.tools.fuzzyNorms.tNorms;

import java.io.Serializable;

import weka.tools.fuzzyNorms.FuzzyNorm;

/**
 * Base Class for fuzzy t-norms
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public abstract class FuzzyTNorm implements FuzzyNorm, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6313903438701399138L;

	protected static final double eps=1E-6;

}

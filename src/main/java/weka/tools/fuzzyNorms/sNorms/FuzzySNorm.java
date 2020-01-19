/**
 * 
 */
package weka.tools.fuzzyNorms.sNorms;

import java.io.Serializable;

import weka.tools.fuzzyNorms.FuzzyNorm;

/**
 * Base class for the fuzzy s-norm
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public abstract class FuzzySNorm implements FuzzyNorm, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -538234287083715662L;

	protected static final double eps=1E-6;
}

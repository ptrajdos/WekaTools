package weka.tools.data;

import weka.core.Randomizable;

/**
 * Interface for random string generators
 * @author pawel trajdos
 * @since 1.10.0
 * @version 1.10.0
 */
public interface IRandomStringGenerator extends Randomizable {
	
	/**
	 * Generate next string
	 * @param length
	 * @return next random string of given length
	 */
	public String generateNextString(int length);
	
	/**
	 * Alphabet used to generate the string
	 * @return
	 */
	public String getAlphabet();
	
	/**
	 * Set alphabet used to generate the string
	 * @param alphabet
	 */
	public void setAlphabet(String alphabet);

}

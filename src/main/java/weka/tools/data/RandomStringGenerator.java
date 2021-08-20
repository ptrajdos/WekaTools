/**
 * 
 */
package weka.tools.data;

import java.io.Serializable;
import java.util.Random;

/**
 * @author pawel trajdos
 * @since 1.7.0
 * @version 1.7.0
 *
 */
public class RandomStringGenerator implements IRandomStringGenerator, Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7153165065883210285L;
	
	protected int seed=0;
	protected Random rnd = new Random(this.seed);
	protected String alphabet =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            						+ "0123456789"
            						+ "abcdefghijklmnopqrstuvxyz"; 

	
	public String generateNextString(int length) {
		
		StringBuilder builder = new StringBuilder(length);
		for(int i=0;i<length;i++) {
			int idx = (int) (this.alphabet.length() * this.rnd.nextDouble());
			builder.append(this.alphabet.charAt(idx));
		}
		
		return builder.toString();
	}


	@Override
	public void setSeed(int seed) {
		this.seed = seed;
		this.rnd.setSeed(seed);

	}

	@Override
	public int getSeed() {
		return this.seed;
	}


	/**
	 * @return the alphabet
	 */
	public String getAlphabet() {
		return this.alphabet;
	}
	/**
	 * @param alphabet the alphabet to set
	 */
	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}
	
	

}

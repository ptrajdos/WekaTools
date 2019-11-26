/**
 * 
 */
package weka.tools.numericIntegration;

import java.util.Random;

import weka.core.Randomizable;

/**
 * Common class to the randomised integration
 * 
 * @author pawel trajdos
 * @since 0.14.0
 * @version 0.14.0
 *
 */
public abstract class RandomisedIntegrator extends AIntegrator implements Randomizable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6166149418462830498L;
	
	private int seed=0;
	Random rndGen = new Random(this.seed);
	private int numSamples=100;
	

	/**
	 * 
	 */
	public RandomisedIntegrator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see weka.core.Randomizable#setSeed(int)
	 */
	@Override
	public void setSeed(int seed) {
		this.seed = seed;
		rndGen.setSeed(seed);
	}

	/* (non-Javadoc)
	 * @see weka.core.Randomizable#getSeed()
	 */
	@Override
	public int getSeed() {
		return this.seed;
	}
	
	
	
	/**
	 * @return the numSamples
	 */
	public int getNumSamples() {
		return this.numSamples;
	}

	/**
	 * @param numSamples the numSamples to set
	 */
	public void setNumSamples(int numSamples) {
		this.numSamples = numSamples;
	}

	protected double generateRandomArgument() {
		double baseRandom = this.rndGen.nextDouble();
		double scaledRandom= baseRandom*(this.getUpperBound() - this.getLowerBound()) + this.getLowerBound();
		return scaledRandom;
	}
	
	

}

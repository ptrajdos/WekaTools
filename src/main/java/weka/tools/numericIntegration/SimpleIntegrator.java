/**
 * 
 */
package weka.tools.numericIntegration;

import weka.tools.Linspace;

/**
 * Simple integration with a uniform sequence.
 * @author pawel trajdos
 * @since 0.10.0
 * @version 0.10.0
 *
 */
public abstract class SimpleIntegrator extends AIntegrator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7365946408256340706L;
	
	protected double delta = 0.01;

	/**
	 * @return the delta
	 */
	public double getDelta() {
		return this.delta;
	}

	/**
	 * @param delta the delta to set
	 */
	public void setDelta(double delta) {
		this.delta = delta;
	}

	protected double[] generateSequence() {
		return Linspace.genLinspace(getLowerBound(), getUpperBound(), this.delta);
	}
	


}

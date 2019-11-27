/**
 * 
 */
package weka.tools.numericIntegration;

import weka.tools.Linspace;

/**
 * Simple integration with a uniform sequence.
 * @author pawel trajdos
 * @since 0.10.0
 * @version 0.14.0
 *
 */
public abstract class SimpleIntegrator extends AIntegrator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7365946408256340706L;
	
	protected double delta = 1.0/1001.0;
	protected int sequenceLength= 1001;

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
		this.sequenceLength = (int) Math.ceil((this.getUpperBound() - this.getLowerBound())/this.delta);
	}
	
	

	/**
	 * @return the sequenceLength
	 */
	public int getSequenceLength() {
		return this.sequenceLength;
	}

	/**
	 * @param sequenceLength the sequenceLength to set
	 */
	public void setSequenceLength(int sequenceLength) {
		this.sequenceLength = sequenceLength;
		this.delta = (this.getUpperBound() - this.getLowerBound())/sequenceLength;
	}
	

	protected double[] generateSequence() {
		return Linspace.genLinspace(getLowerBound(), getUpperBound(), this.sequenceLength);
	}

	

	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.AIntegrator#setLowerBound(double)
	 */
	@Override
	public void setLowerBound(double lower) {
		super.setLowerBound(lower);
		this.setSequenceLength(this.sequenceLength);
	}

	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.AIntegrator#setUpperBound(double)
	 */
	@Override
	public void setUpperBound(double upper) {
		super.setUpperBound(upper);
		this.setSequenceLength(this.sequenceLength);
	}
	


}

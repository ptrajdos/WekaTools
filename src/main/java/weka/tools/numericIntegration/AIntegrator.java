/**
 * 
 */
package weka.tools.numericIntegration;

import java.io.Serializable;

import weka.tools.Linspace;

/**
 * Abstract class for numeric integration
 * @author pawel trajdos
 * @since 0.10.0
 * @version 0.10.0
 *
 */
public abstract class AIntegrator implements Integrator, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 786287142063634441L;
	
	private double lowerBound =0.0;
	
	private double upperBound=1.0;
	
	private Function function;

	/**
	 * 
	 */
	public AIntegrator() {
		this.function = new DummyFunction();
	}

	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.Integrator#setLowerBound(double)
	 */
	@Override
	public void setLowerBound(double lower) {
		this.lowerBound = lower;

	}

	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.Integrator#getLowerBound()
	 */
	@Override
	public double getLowerBound() {
		return this.lowerBound;
	}

	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.Integrator#setUpperBound(double)
	 */
	@Override
	public void setUpperBound(double upper) {
		this.upperBound=upper;

	}

	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.Integrator#getUpperBound()
	 */
	@Override
	public double getUpperBound() {
		return this.upperBound;
	}

	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.Integrator#setFunction(weka.tools.numericIntegration.Function)
	 */
	@Override
	public void setFunction(Function f) {
		this.function = f;
	}

	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.Integrator#getFunction()
	 */
	@Override
	public Function getFunction() {
		return this.function;
	}


}

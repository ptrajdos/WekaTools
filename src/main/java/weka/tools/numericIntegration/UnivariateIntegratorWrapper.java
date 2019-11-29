/**
 * 
 */
package weka.tools.numericIntegration;

import java.io.Serializable;

import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;

/**
 * @author pawel
 *
 */
public class UnivariateIntegratorWrapper extends AIntegrator implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3602460319766984740L;
	private transient UnivariateIntegrator integrator;
	private int maxEval=1000;

	/**
	 * 
	 */
	public UnivariateIntegratorWrapper() {
		this.integrator = new SimpsonIntegrator();
	}

	@Override
	public double integrate() throws Exception {
		return this.integrator.integrate(this.maxEval, this.getFunction(), this.getLowerBound(), this.getUpperBound());
	}

	/**
	 * @return the integrator
	 */
	public UnivariateIntegrator getIntegrator() {
		return this.integrator;
	}

	/**
	 * @param integrator the integrator to set
	 */
	public void setIntegrator(UnivariateIntegrator integrator) {
		this.integrator = integrator;
	}

	/**
	 * @return the maxEval
	 */
	public int getMaxEval() {
		return this.maxEval;
	}

	/**
	 * @param maxEval the maxEval to set
	 */
	public void setMaxEval(int maxEval) {
		this.maxEval = maxEval;
	}
	
	



}

/**
 * 
 */
package weka.tools.numericIntegration;

import weka.core.KhanKleinSummator;

/**
 * Trapezoidal integration
 * 
 * @author pawel trajdos
 * @since 0.10.0
 * @version 0.10.0
 *
 */
public class TrapezoidalIntegrator extends SimpleIntegrator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5739537913950460267L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see weka.tools.numericIntegration.Integrator#integrate()
	 */
	@Override
	public double integrate() throws Exception {
		double[] sequence = this.generateSequence();
		double delta = 0;
		double tmpVal = 0;
		
		KhanKleinSummator sumVal = new KhanKleinSummator();
		
		for (int i = 0; i < sequence.length - 1; i++) {
			delta = sequence[i + 1] - sequence[i];
			tmpVal = 0.5 * (this.getFunction().value(sequence[i + 1]) + this.getFunction().value(sequence[i]));
			sumVal.addToSum(tmpVal * delta);
		}
		return sumVal.getSum();
	}

}

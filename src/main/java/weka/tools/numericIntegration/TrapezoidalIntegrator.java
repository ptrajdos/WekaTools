/**
 * 
 */
package weka.tools.numericIntegration;

/**
 * Trapezoidal integration
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


	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.Integrator#integrate()
	 */
	@Override
	public double integrate() {
		double value=0;
		double[] sequence = this.generateSequence();
		double delta =0;
		double tmpVal =0;
		for(int i=0;i<sequence.length -1;i++) {
			delta = sequence[i+1] - sequence[i];
			tmpVal = 0.5*(this.getFunction().getValue(sequence[i+1]) + this.getFunction().getValue(sequence[i]));
			value+=tmpVal*delta;
		}
		return value;
	}

}

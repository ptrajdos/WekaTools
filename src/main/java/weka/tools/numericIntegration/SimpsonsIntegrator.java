/**
 * 
 */
package weka.tools.numericIntegration;

/**
 * Performs numerical integration using Simpsons rule
 * @author pawel trajdos
 * @since 0.13.0
 * @version 0.13.0
 *
 */
public class SimpsonsIntegrator extends SimpleIntegrator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7081303797285256324L;

	

	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.Integrator#integrate()
	 */
	@Override
	public double integrate() throws Exception {
		double value=0;
		double[] sequence = this.generateSequence();
		
		double tmpVal =0;
		value += this.delta/3.0 * (this.getFunction().value(sequence[0]) +
				this.getFunction().value(sequence[sequence.length-1]));
		
		for(int i=1;i<sequence.length -1;i++) {
			if(i%2==0) {
				tmpVal= 2.0*this.getFunction().value(sequence[i]);
			}else {
				tmpVal= 4.0*this.getFunction().value(sequence[i]);
			}
			value+= tmpVal*this.delta/3.0;
		}
		return value;
	}

}

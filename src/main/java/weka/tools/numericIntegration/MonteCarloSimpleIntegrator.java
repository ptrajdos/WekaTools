/**
 * 
 */
package weka.tools.numericIntegration;

/**
 * Monte Carlo integratoin
 * @author pawel trajdos
 * @since 0.14.0
 * @version 0.14.0
 *
 */
public class MonteCarloSimpleIntegrator extends RandomisedIntegrator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1930838323512361449L;

	

	/* (non-Javadoc)
	 * @see weka.tools.numericIntegration.Integrator#integrate()
	 */
	@Override
	public double integrate() throws Exception {
		
		
		double x=0;
		double y=0;
		double sum=0;
		int counter=0;
		int numSamples = this.getNumSamples();
		 while(counter<numSamples){
			x=this.generateRandomArgument();
			y=this.getFunction().value(x);
			
			if( !Double.isInfinite(y) && !Double.isNaN(y) ) { 
				sum+=y;
				counter++;
			}
				
			
		}
		
		double result  = ((this.getUpperBound() - this.getLowerBound())/numSamples ) * sum;
		return result;
	}

}

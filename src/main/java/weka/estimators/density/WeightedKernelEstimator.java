/**
 * 
 */
package weka.estimators.density;

/**
 * Weighted kernel estimator
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
 *
 */
public class WeightedKernelEstimator extends AbstractKernelEstimator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5896746843853707517L;

	
	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getPDF(double)
	 */
	@Override
	public double getPDF(double x) {
		int numVals = this.valHolder.getNumVals();
		double estimation =0;
		for(int i=0;i<numVals;i++) {
			estimation+=this.kernel.getKernelPDFValue(  (x-this.valHolder.getValue(i))/this.bandwidth  )*this.valHolder.getWeight(i);
		}
		estimation/=this.bandwidth;
		return estimation;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensityEstimator#getCDF(double)
	 */
	@Override
	public double getCDF(double x) {
		int numVals = this.valHolder.getNumVals();
		double estimation =0;
		for(int i=0;i<numVals;i++)
			estimation+=this.kernel.getKernelCDFValue(  (x-this.valHolder.getValue(i))/this.bandwidth  )*this.valHolder.getWeight(i);
		
		return estimation;
	}

}

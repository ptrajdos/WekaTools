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


	@Override
	public double[] getValues() {
		int numVals = this.valHolder.getNumVals();
		double[] vals = new double[numVals];
		for(int i=0;i<numVals;i++) {
			vals[i] = this.valHolder.getValue(i);
		}
		return vals;
	}

	@Override
	public double[] getWeights() {
		int numVals = this.valHolder.getNumVals();
		double[] weights = new double[numVals];
		for(int i=0;i<numVals;i++) {
			weights[i] = this.valHolder.getWeight(i);
		}
		return weights;
	}

}

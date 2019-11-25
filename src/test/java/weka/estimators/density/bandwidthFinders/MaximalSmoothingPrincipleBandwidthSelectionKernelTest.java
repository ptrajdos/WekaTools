package weka.estimators.density.bandwidthFinders;

import weka.estimators.density.DensEstimatorTest;
import weka.estimators.density.DensityEstimator;

public class MaximalSmoothingPrincipleBandwidthSelectionKernelTest extends DensEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new MaximalSmoothingPrincipleBandwidthSelectionKernel();
	}
	
	/* (non-Javadoc)
	 * @see weka.estimators.density.DensEstimatorTest#getLower()
	 */
	@Override
	protected double getLower() {
		return super.getLower()-0.5;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensEstimatorTest#getUpper()
	 */
	@Override
	protected double getUpper() {
		return super.getUpper()+0.5;
	}


}

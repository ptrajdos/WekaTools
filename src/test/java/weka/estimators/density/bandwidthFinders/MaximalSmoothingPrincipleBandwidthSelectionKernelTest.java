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
		return super.getLower();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensEstimatorTest#getUpper()
	 */
	@Override
	protected double getUpper() {
		return super.getUpper();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensEstimatorTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.eps=0.5;
		this.integrationEps=0.5;
	}

	

}

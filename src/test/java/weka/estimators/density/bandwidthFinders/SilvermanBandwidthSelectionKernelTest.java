package weka.estimators.density.bandwidthFinders;

import weka.estimators.density.DensEstimatorTest;
import weka.estimators.density.DensityEstimator;

public class SilvermanBandwidthSelectionKernelTest extends DensEstimatorTest{

	@Override
	protected DensityEstimator getEstimator() {
		return new SilvermanBandwidthSelectionKernel();
	}

		
	/* (non-Javadoc)
	 * @see weka.estimators.density.DensEstimatorTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.eps=1.0;
		this.integrationEps=1.0;
		this.compareEps=1E-4;
	}

	

}

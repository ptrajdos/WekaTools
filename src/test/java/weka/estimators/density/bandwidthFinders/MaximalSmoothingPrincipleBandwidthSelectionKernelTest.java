package weka.estimators.density.bandwidthFinders;

import weka.estimators.density.DensEstimatorTest;
import weka.estimators.density.DensityEstimator;

public class MaximalSmoothingPrincipleBandwidthSelectionKernelTest extends DensEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new MaximalSmoothingPrincipleBandwidthSelectionKernel();
	}


}

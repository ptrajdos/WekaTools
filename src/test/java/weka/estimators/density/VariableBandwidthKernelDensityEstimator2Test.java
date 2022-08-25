package weka.estimators.density;

public class VariableBandwidthKernelDensityEstimator2Test extends BasicKernelDensityEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new VariableBandwidthKernelDensityEstimator2();
	}

}

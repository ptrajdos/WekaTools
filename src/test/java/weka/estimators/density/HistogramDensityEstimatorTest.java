package weka.estimators.density;

public class HistogramDensityEstimatorTest extends DensEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new HistogramDensityEstimator();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.compareIntegrateEps=9E-2;
	}
}

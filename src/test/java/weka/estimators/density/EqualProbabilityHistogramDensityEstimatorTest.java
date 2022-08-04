package weka.estimators.density;

public class EqualProbabilityHistogramDensityEstimatorTest extends HistogramDensityEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new EqualProbabilityHistogramDensityEstimator();
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.compareIntegrateEps=1E-2;
		this.eps=1E-6;
	}



}

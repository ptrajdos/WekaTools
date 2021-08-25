package weka.estimators.density;

public class FrequencyPolygonMidpointDensityEstimatorTest extends HistogramDensityEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new FrequencyPolygonMidpointDensityEstimator();
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.compareIntegrateEps=9E-2;
		this.eps=1E-3;
	}



}

package weka.estimators.density;

public class CachedEstimatorTest extends DensEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new CachedEstimator();
	}



}

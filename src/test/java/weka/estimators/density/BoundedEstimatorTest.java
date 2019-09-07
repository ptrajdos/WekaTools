package weka.estimators.density;

public class BoundedEstimatorTest extends DensEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new BoundedEstimator();
	}

	

}

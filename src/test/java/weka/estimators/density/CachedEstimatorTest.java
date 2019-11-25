package weka.estimators.density;

public class CachedEstimatorTest extends DensEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		CachedEstimator cEstim = new CachedEstimator();
		cEstim.setDensEstim(new BoundedEstimator());
		return cEstim;
	}



}

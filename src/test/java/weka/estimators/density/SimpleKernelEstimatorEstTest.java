package weka.estimators.density;

public class SimpleKernelEstimatorEstTest extends DensEstimatorTest{

	@Override
	protected DensityEstimator getEstimator() {
		return new SimpleKernelEstimator();
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensEstimatorTest#getLower()
	 */
	@Override
	protected double getLower() {
		return -0.5;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensEstimatorTest#getUpper()
	 */
	@Override
	protected double getUpper() {
		return 1.5;
	}

	/* (non-Javadoc)
	 * @see weka.estimators.density.DensEstimatorTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.eps=0.5;
	}

	
	

}

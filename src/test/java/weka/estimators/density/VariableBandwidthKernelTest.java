package weka.estimators.density;

import weka.core.Utils;

public class VariableBandwidthKernelTest extends DensEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new VariableBandwidthKernelEstimator();
	}
	
	public void testAlpha() {
		VariableBandwidthKernelEstimator est = (VariableBandwidthKernelEstimator) this.getEstimator();
		
		est.setAlpha(-1);
		assertTrue("Lowel limit of alpha is 0", Utils.eq(est.getAlpha(), 0.0));
		
		est.setAlpha(2.0);
		assertTrue("Upper limit of alpha is 1", Utils.eq(est.getAlpha(), 1.0));
		
	}


}

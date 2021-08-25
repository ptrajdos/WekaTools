package weka.estimators.density;

import weka.tools.tests.WekaGOEChecker;

public class BoundedEstimatorTest extends DensEstimatorTest {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.stricEstimInterval=true;
	}

	@Override
	protected DensityEstimator getEstimator() {
		return new BoundedEstimator();
	}
	
	
	public void testTipTexts() {
		 WekaGOEChecker goe = new WekaGOEChecker();
		 goe.setObject(this.getEstimator());
		 goe.checkToolTipsCall();
	}
	

	

}

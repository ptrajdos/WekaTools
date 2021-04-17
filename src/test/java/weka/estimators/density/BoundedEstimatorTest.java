package weka.estimators.density;

import weka.tools.tests.WekaGOEChecker;

public class BoundedEstimatorTest extends DensEstimatorTest {

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

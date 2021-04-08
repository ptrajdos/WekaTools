package weka.estimators;

import junit.framework.Test;
import junit.framework.TestSuite;
import weka.core.OptionHandler;
import weka.tools.tests.OptionHandlerChecker;
import weka.tools.tests.SerializationChecker;
import weka.tools.tests.WekaGOEChecker;


public class MultivariateEstimatorFromInstancesWrapperTest extends MultivariateEstimatorFromInstancesTest {

	@Override
	public MultivariateEstimatorFromInstances getEstimator() {
		return new MultivariateEstimatorFromInstancesWrapper();
	}
	
	public static Test suite() {
	    return new TestSuite(MultivariateEstimatorFromInstancesWrapperTest.class);
	}
	
	 
	public void testSerialization() {
		MultivariateEstimatorFromInstances estim = this.getEstimator();
		assertTrue("Serialization check",  SerializationChecker.checkSerializationCopy(estim));
	}
	
	public void testOptions() {
		OptionHandlerChecker.checkOptions((OptionHandler) this.getEstimator());	    	
	}
	
	
	public void testGOE() {
		MultivariateEstimatorFromInstances estim = this.getEstimator();
		WekaGOEChecker checker = new WekaGOEChecker();
		checker.setObject(estim);
		assertTrue("Global info",checker.checkGlobalInfo());
		assertTrue("Global info call",checker.checkCallGlobalInfo());
		assertTrue("Global toolTips",checker.checkToolTips());
		assertTrue("Global toolTips call",checker.checkToolTipsCall());

	}
	
	public static void main(String[] args){
	    junit.textui.TestRunner.run(suite());
}
}

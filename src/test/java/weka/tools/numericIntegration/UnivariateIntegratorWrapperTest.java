package weka.tools.numericIntegration;

import weka.tools.SerializationTester;

public class UnivariateIntegratorWrapperTest extends IntegratorTest {

	@Override
	protected Integrator getIntegrator() {
		return new UnivariateIntegratorWrapper();
	}
	
	public void testSerialization() {
		assertTrue("Serialization",SerializationTester.checkSerialization(getIntegrator()));
	}


}

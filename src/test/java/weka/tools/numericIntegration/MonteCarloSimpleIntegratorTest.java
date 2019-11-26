package weka.tools.numericIntegration;

import weka.core.Utils;

public class MonteCarloSimpleIntegratorTest extends IntegratorTest {

	@Override
	protected Integrator getIntegrator() {
		MonteCarloSimpleIntegrator integrator =new MonteCarloSimpleIntegrator(); 
		integrator.setNumSamples(1000);
		return integrator;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Utils.SMALL=1e-1;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		Utils.SMALL=1e-6;
	}

	

}

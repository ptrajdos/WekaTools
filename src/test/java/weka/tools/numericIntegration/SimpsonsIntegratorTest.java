package weka.tools.numericIntegration;

public class SimpsonsIntegratorTest extends IntegratorTest {

	@Override
	protected Integrator getIntegrator() {
		return new SimpsonsIntegrator();
	}



}

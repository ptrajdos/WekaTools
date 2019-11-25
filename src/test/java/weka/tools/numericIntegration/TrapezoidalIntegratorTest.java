package weka.tools.numericIntegration;

public class TrapezoidalIntegratorTest extends IntegratorTest {

	@Override
	protected Integrator getIntegrator() {
		return new TrapezoidalIntegrator();
	}

	

}

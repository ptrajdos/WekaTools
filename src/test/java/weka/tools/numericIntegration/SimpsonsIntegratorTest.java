package weka.tools.numericIntegration;

import static org.junit.Assert.*;

import org.junit.Test;

import weka.core.Utils;

public class SimpsonsIntegratorTest extends IntegratorTest {

	@Override
	protected Integrator getIntegrator() {
		return new SimpsonsIntegrator();
	}



}

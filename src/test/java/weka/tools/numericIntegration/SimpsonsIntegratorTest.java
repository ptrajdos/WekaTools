package weka.tools.numericIntegration;

import static org.junit.Assert.*;

import org.junit.Test;

import weka.core.Utils;

public class SimpsonsIntegratorTest {

	@Test
	public void test() {
		SimpsonsIntegrator integr = new SimpsonsIntegrator();
		assertTrue("Default Parameter integration", Utils.eq(integr.integrate(), 1));
	}
	@Test
	public void testSin() {
		SimpsonsIntegrator integr = new SimpsonsIntegrator();
		Function func = new Function() {
			
			@Override
			public double getValue(double argument) {
				return Math.sin(argument);
			}
		};
		integr.setFunction(func);
		integr.setUpperBound(2*Math.PI);
		assertTrue("Sinus integration", Utils.eq(integr.integrate(), 0));
	}

}

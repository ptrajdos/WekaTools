package weka.tools.numericIntegration;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import weka.core.Utils;

public class TrapezoidalIntegratorTest {

	@Test
	public void test() {
		TrapezoidalIntegrator integr = new TrapezoidalIntegrator();
		assertTrue("Default Parameter integration", Utils.eq(integr.integrate(), 1));
	}
	@Test
	public void testSin() {
		TrapezoidalIntegrator integr = new TrapezoidalIntegrator();
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

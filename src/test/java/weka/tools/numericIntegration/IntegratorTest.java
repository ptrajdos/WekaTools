/**
 * 
 */
package weka.tools.numericIntegration;

import junit.framework.TestCase;
import weka.core.Utils;

/**
 * @author pawel trajdos
 * @version 0.13.0
 * @since 0.13.0
 *
 */
public abstract class IntegratorTest extends TestCase {

	
	protected abstract Integrator getIntegrator();
	
	public void testDefaults() {
		Integrator  integr = this.getIntegrator();
		try {
			assertTrue("Default integration to 1", Utils.eq(integr.integrate(), 1));
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been caught");
		}
	}
	
	public void testSinus() {
		Integrator integr = this.getIntegrator();
		Function func = new Function() {
			
			@Override
			public double value(double argument) {
				return Math.sin(argument);
			}
		};
		integr.setFunction(func);
		integr.setUpperBound(2*Math.PI);
		double intVal=0;
		try {
			intVal = integr.integrate();
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been caught");
		}
		assertTrue("Sinus integration", Utils.eq(intVal, 0));
	}
	
	public void testSquare() {
		Integrator integr = this.getIntegrator();
		Function func = new Function() {
			
			@Override
			public double value(double argument) {
				if(argument < -1 | argument > 1)
					return 0.0;
				
				return 0.5;
			}
		};
		integr.setFunction(func);
		integr.setLowerBound(-1.0);
		integr.setUpperBound(1.0);
		double intVal = 0;
		try {
			intVal=integr.integrate();
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been caught");
		}
		assertTrue("Square integration", Utils.eq(intVal, 1));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Utils.SMALL=1e-2;
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

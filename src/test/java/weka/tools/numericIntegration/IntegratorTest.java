/**
 * 
 */
package weka.tools.numericIntegration;

import static org.junit.Assert.assertTrue;

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
		assertTrue("Default integration to 1", Utils.eq(integr.integrate(), 1));
	}
	
	public void testSinus() {
		Integrator integr = this.getIntegrator();
		Function func = new Function() {
			
			@Override
			public double getValue(double argument) {
				return Math.sin(argument);
			}
		};
		integr.setFunction(func);
		integr.setUpperBound(2*Math.PI);
		double intVal = integr.integrate();
		assertTrue("Sinus integration", Utils.eq(intVal, 0));
	}
	
	public void testSquare() {
		Integrator integr = this.getIntegrator();
		Function func = new Function() {
			
			@Override
			public double getValue(double argument) {
				if(argument < -1 | argument > 1)
					return 0.0;
				
				return 0.5;
			}
		};
		integr.setFunction(func);
		integr.setLowerBound(-1.0);
		integr.setUpperBound(1.0);
		double intVal = integr.integrate();
		assertTrue("Square integration", Utils.eq(intVal, 1));
	}

}

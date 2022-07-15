/**
 * 
 */
package weka.estimators.density.histogram.bin;

import junit.framework.TestCase;

/**
 * Tests for all classes that implements IBin interface
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 */
public abstract class IBinTest extends TestCase {

	public abstract IBin getBin(double lower, double upper);
	
	public void testBin() {
		
		double lower = 0.0;
		double upper = 1.0;
		
		IBin bin = this.getBin(lower, upper);
		
		assertEquals("Width", 1.0, bin.getWidth(), 1E-3);
		assertEquals("Center", 0.5, bin.getBinCenter(), 1E-3);
		assertEquals("Lower", lower, bin.getLowerBound(), 1E-3);
		assertEquals("Upper", upper, bin.getUpperBound(), 1E-3);
		
		assertTrue("Inside bin", bin.isValueInBin(0.8));
		assertFalse("Outside bin", bin.isValueInBin(5.0));
		assertFalse("Outside bin", bin.isValueInBin(-5.0));
		
		double value = 6.66;
		
		bin.addValue(value);
		assertEquals("Value", value, bin.getCount(), 1E-3);
		

	}

}

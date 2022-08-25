/**
 * 
 */
package weka.estimators.density;

/**
 * @author pawel trajdos
 * @version 2.0.0
 * @since 2.0.0
 *
 */
public abstract class BasicKernelDensityEstimatorTest extends DensEstimatorTest {



	public void testGetKernel() {
		try {
			BasicKernelDensityEstimator bEstim  = (BasicKernelDensityEstimator) this.getEstimator();
			
			Kernel kern = bEstim.getKernel();
			assertTrue("Not null kernel", kern != null);
			
		}catch(ClassCastException cExc) {
			fail("The estimator is not an instance of BasicKernelDensityEstimator");
		}
		catch(Exception exc) {
			fail("An exception has been caught: " + exc.getLocalizedMessage());
		}
	}
	
	public void testGetBandwidth() {
		try {
			BasicKernelDensityEstimator bEstim  = (BasicKernelDensityEstimator) this.getEstimator();
			
			double bandwidth  = bEstim.getBandwidth();
			
			assertTrue("Bandwidth greater than zero!", bandwidth>0);
			
		}catch(ClassCastException cExc) {
			fail("The estimator is not an instance of BasicKernelDensityEstimator");
		}
		catch(Exception exc) {
			fail("An exception has been caught: " + exc.getLocalizedMessage());
		}
	}

}

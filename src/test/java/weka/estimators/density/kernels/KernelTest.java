/**
 * 
 */
package weka.estimators.density.kernels;

import junit.framework.TestCase;
import weka.core.Utils;
import weka.estimators.density.Kernel;
import weka.tools.Linspace;
import weka.tools.numericIntegration.Function;
import weka.tools.numericIntegration.SimpleIntegrator;
import weka.tools.numericIntegration.SimpsonsIntegrator;

/**
 * @author pawel
 *
 */
public abstract class KernelTest extends TestCase {

	protected abstract Kernel getKernel();
	
	protected double eps = 1E-6;
	protected double step =0.01;
	
	public void testCDF() {
		Kernel kern = this.getKernel();
		
		double lower = kern.supportLower();
		double upper = kern.supportUpper();
		
		assertTrue("-Inf", Utils.eq(kern.getKernelCDFValue(lower-eps), 0));
		assertTrue("+Inf", Utils.eq(kern.getKernelCDFValue(upper+eps), 1));
		
		double[] lins = Linspace.genLinspace(lower, upper, step);
		for(int i=0;i< lins.length-1;i++) {
			assertTrue("Finite", Double.isFinite(kern.getKernelCDFValue(lins[i])));
			assertTrue("Finite", Double.isFinite(kern.getKernelCDFValue(lins[i+1])));
			assertFalse("NaN", Double.isNaN(kern.getKernelCDFValue(lins[i])));
			assertFalse("NaN", Double.isNaN(kern.getKernelCDFValue(lins[i+1])));
			
			assertTrue("Increasing Property", kern.getKernelCDFValue(lins[i])<= kern.getKernelCDFValue(i+1) );
		}
		
		
	}
	
	public void testPDF() {
		final Kernel kern = this.getKernel();
		
		double lower = kern.supportLower();
		double upper = kern.supportUpper();
		
		assertTrue("-Inf", Utils.eq(kern.getKernelPDFValue(lower-eps), 0));
		assertTrue("+Inf", Utils.eq(kern.getKernelPDFValue(upper+eps), 0));
		
		double[] lins = Linspace.genLinspace(lower, upper, step);
		for(int i=0;i<lins.length;i++) {
			assertFalse("Not NaN", Double.isNaN(kern.getKernelPDFValue(lins[i])));
			assertTrue("Not Inf", Double.isFinite(kern.getKernelPDFValue(lins[i])));
			assertTrue("Greater than zero", kern.getKernelPDFValue(lins[i]) >=0);
		}
		
		SimpleIntegrator trint = new SimpsonsIntegrator();
		trint.setLowerBound(lower - eps);
		trint.setUpperBound(upper + eps);
		trint.setDelta(0.00001);
		trint.setFunction(new Function() {
			
			@Override
			public double value(double argument) {
				return kern.getKernelPDFValue(argument);
			}
		});
		double integral = 0;
		try {
			integral= trint.integrate();
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been caught");
		}
		Utils.SMALL=1e-3;
		assertTrue("Integration", Utils.eq(integral, 1.0));
		
		
	}

}

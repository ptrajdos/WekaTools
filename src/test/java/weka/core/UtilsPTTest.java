package weka.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilsPTTest {

	@Test
	public void testTmean() {
		double[] array = new double[] {1,2,3,4,5};
		double tmean = UtilsPT.truncatedMean(array);
		assertTrue(Utils.eq(tmean, 3.0));
		
		double[]array2 = new double[] {2,3};
		tmean =  UtilsPT.truncatedMean(array2);
		assertTrue(Utils.eq(tmean, 2.5));
	}
	@Test
	public void testVars() {
		double[] array = new double[] {1,2,3,4,5,6,7,8,9,10};
		double[] array2 = new double[] {10,9,8,7,6,5,4,3,2,1};
		double var = UtilsPT.var(array);
		double desVar =9 + 1.0/6.0 ;
		assertEquals(desVar, var, 10e-6);
		
		double sDev = UtilsPT.stdDev(array);
		assertEquals(Math.sqrt(desVar), sDev, 10e-6);
		
		double cVar = 0;
		try {
			cVar = UtilsPT.cov(array, array2);
		} catch (Exception e) {
			fail("Exception caught");
			e.printStackTrace();
		}
		assertEquals(-desVar, cVar, 10e-6);
		
		double corr =0;
		try {
			 corr = UtilsPT.corr(array, array2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(-1, corr, 10e-6);
		
	}

}

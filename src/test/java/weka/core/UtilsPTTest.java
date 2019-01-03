package weka.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilsPTTest {

	@Test
	public void test() {
		double[] array = new double[] {1,2,3,4,5};
		double tmean = UtilsPT.truncatedMean(array);
		assertTrue(Utils.eq(tmean, 3.0));
		
		double[]array2 = new double[] {2,3};
		tmean =  UtilsPT.truncatedMean(array2);
		assertTrue(Utils.eq(tmean, 2.5));
	}

}

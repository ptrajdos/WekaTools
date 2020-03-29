package weka.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import weka.tools.tests.DistributionChecker;

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
			e.printStackTrace();
			fail("An exception has been caught");
		}
		assertEquals(-1, corr, 10e-6);
		
	}
	@Test
	public void testDoubleComp() {
		double[] array = new double[] {1,2,3,4,5,6,7,8,9,10};
		double[] array2 = Arrays.copyOf(array, array.length);
		array2[0] += 1E-11;
		
		assertTrue("Double array comparision", UtilsPT.compareDoubleArrays(array, array2));
		
		 array = new double[] {1,2,3,4,5,6,7,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NaN};
		 array2 = Arrays.copyOf(array, array.length);
		array2[0] += 1E-11;
		
		assertTrue("Double array comparision", UtilsPT.compareDoubleArrays(array, array2));
		
		array2[3] += -1;
		
		assertFalse("Double array comparision", UtilsPT.compareDoubleArrays(array, array2));
		
	}
	
	@Test
	public void testFloatComp() {
		float[] array = new float[] {1,2,3,4,5,6,7,Float.POSITIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.NaN};
		float[] array2 = Arrays.copyOf(array, array.length);
		array2[0] += 1E-7;
		
		assertTrue("Double array comparision", UtilsPT.compareFloatArrays(array, array2));
		
		array = new float[] {1,2,3,4,5,6,7,8,9,10};
		 array2 = Arrays.copyOf(array, array.length);
		array2[0] += 1E-7;
		
		assertTrue("Double array comparision", UtilsPT.compareFloatArrays(array, array2));
		
		array2[3] += -1;
		
		assertFalse("Double array comparision", UtilsPT.compareFloatArrays(array, array2));
	}
	
	@Test
	public void testQuantile() {
		double[] array = new double[] {1,2,3,4,5,6,7,8,9,10};
		
		double med = UtilsPT.median(array);
		double quant = UtilsPT.quantile(array, 0.5);
		assertTrue("Q 0.5", Utils.eq(med,quant));
		
	}
	
	@Test
	public void testSoftmax() {
		int lens[]= {1,2,3,5,10,100};
		for(int i=0;i<lens.length;i++) {
			double[] dat = this.generateDate(lens[i], i);
			double[] smax = UtilsPT.softMax(dat);
			assertTrue("SoftMax test: ", DistributionChecker.checkDistribution(smax));
		}
		
		double[][] dnormDistribs= {{0,0,0},{0},{0,0},{Double.MIN_VALUE,Double.MIN_VALUE}};
		for (double[] ds : dnormDistribs) {
			assertTrue("Denorm distribs", DistributionChecker.checkDistribution(ds));
		}
			
		
	}
	
	double[] generateDate(int numVals, int seed) {
		double[] genData = new double[numVals];
		Random rnd = new Random(seed);
		for(int i=0;i<genData.length;i++)
			genData[i] = rnd.nextDouble();
		
		return genData;
	}

}

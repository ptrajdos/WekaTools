package weka.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import weka.tools.Linspace;
import weka.tools.tests.DistributionChecker;

public class UtilsPTTest {
	
	@Test
	public void testCreation() {
		UtilsPT te = new UtilsPT();
		assertTrue("Not null", te!=null);
	}

	@Test
	public void testTmean() {
		double[] array = new double[] {1,2,3,4,5};
		double[] array2 = new double[] {5,4,3,2,1};
		double tmean = UtilsPT.truncatedMean(array);
		assertTrue(Utils.eq(tmean, 3.0));
		
		tmean = UtilsPT.truncatedMean(array2);
		assertTrue(Utils.eq(tmean, 3.0));
		
		double[]array3 = new double[] {2,3};
		tmean =  UtilsPT.truncatedMean(array3);
		assertTrue(Utils.eq(tmean, 2.5));
		assertTrue(Utils.eq(UtilsPT.truncatedMean(new double[] {3,2}), 2.5));
	}
	@Test
	public void testVars() {
		double[] array = new double[] {1,2,3,4,5,6,7,8,9,10};
		double[] array2 = new double[] {10,9,8,7,6,5,4,3,2,1};
		double var = UtilsPT.var(array);
		double desVar =9 + 1.0/6.0 ;
		assertEquals(desVar, var, 10e-6);
		assertEquals(UtilsPT.var(new double[] {1}),0.0,10e-6);
		
		double sDev = UtilsPT.stdDev(array);
		assertEquals(Math.sqrt(desVar), sDev, 10e-6);
		
		double cVar = 0;
		try {
			cVar = UtilsPT.cov(array, array2);
			assertTrue("length one zero cov", Utils.eq(0, UtilsPT.cov(new double[] {1}, new double[] {1})) );
		} catch (Exception e) {
			fail("Exception caught");
			e.printStackTrace();
		}
		assertEquals(-desVar, cVar, 10e-6);
		
		try {
			UtilsPT.cov(new double[] {1,2,3}, new double[] {1,2});
			fail("No exception on incopatible arrays");
		} catch (Exception e1) {
		}
		
		double corr =0;
		try {
			 corr = UtilsPT.corr(array, array2);
			 assertTrue("length one unit cor", Utils.eq(1, UtilsPT.corr(new double[] {1}, new double[] {1})) );
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
		double[] array3 = new double[] {1,2,3,4,5,6,7,8,9,10,11};
		
		assertTrue("The same array comparision",UtilsPT.compareDoubleArrays(array, array));
		assertFalse("Compare with null", UtilsPT.compareDoubleArrays(array, null));
		assertFalse("Compare with null", UtilsPT.compareDoubleArrays(null, array));
		//assertFalse("Compare with null", UtilsPT.compareDoubleArrays(null, null));
		assertFalse("Compare with Different lengths", UtilsPT.compareDoubleArrays(array, array3));
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
		float[] array3 = new float[] {1,2,3,4,5,6,7,Float.POSITIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.NaN,1};
		
		assertTrue("Double array comparision", UtilsPT.compareFloatArrays(array, array2));
		assertTrue("The same array comparision",UtilsPT.compareFloatArrays(array, array));
		assertFalse("Compare with null", UtilsPT.compareFloatArrays(array, null));
		assertFalse("Compare with null", UtilsPT.compareFloatArrays(null, array));
		//assertFalse("Compare with null", UtilsPT.compareFloatArrays(null, null));
		assertFalse("Compare with Different lengths", UtilsPT.compareFloatArrays(array, array3));
		
		array = new float[] {1,2,3,4,5,6,7,8,9,10};
		 array2 = Arrays.copyOf(array, array.length);
		array2[0] += 1E-7;
		
		assertTrue("Double array comparision", UtilsPT.compareFloatArrays(array, array2));
		
		array2[3] += -1;
		
		assertFalse("Double array comparision", UtilsPT.compareFloatArrays(array, array2));
	}
	
	@Test
	public void testQuantile() {
		
		int numReps =10;
		for(int i=0;i<numReps;i++) {
			double[] sample = this.sampleGen(100, i);
			
			double med = UtilsPT.median(sample);
			double quant = UtilsPT.quantile(sample, 0.5);
			assertTrue("Median", Utils.eq(med, quant));
			
			quant = UtilsPT.quantile(sample, 0.0);
			int minIdx = Utils.minIndex(sample);
			assertTrue("Zero Quantile -> min", Utils.eq(quant, sample[minIdx]));
			assertTrue("Under zero quantile -> min", Utils.eq(UtilsPT.quantile(sample, -0.1), sample[minIdx]));
			
			quant = UtilsPT.quantile(sample, 1.0);
			int maxIdx = Utils.maxIndex(sample);
			assertTrue("One quantile -> max", Utils.eq(quant, sample[maxIdx]));
			assertTrue("Over one quantile -> max", Utils.eq(UtilsPT.quantile(sample, 1.1), sample[maxIdx]));
		}
		double sampleVal=6.54;
		double[] sample= {sampleVal};
		
		double[] linS = Linspace.genLinspace(0, 1, 10);
		for(int i=0;i<linS.length;i++) {
			double quant = UtilsPT.quantile(sample, linS[i]);
			assertTrue("One element distribution", Utils.eq(quant, sampleVal));
		}
		
	}
	
	protected double[] sampleGen(int maxLen, int seed) {
		Random rnd = new Random(seed);
		int sampleSize = rnd.nextInt(maxLen)+1;
		double[] sample = new double[sampleSize];
		for(int i=0;i<sampleSize;i++)
			sample[i] = rnd.nextDouble();
		
		return sample;
	}
	
	@Test
	public void testSoftmax() {
		int lens[]= {1,2,3,5,10,100};
		for(int i=0;i<lens.length;i++) {
			double[] dat = this.generateDate(lens[i], i);
			int dMaxIdx = Utils.maxIndex(dat);
			double[] smax = UtilsPT.softMax(dat);
			int sMaxIdx = Utils.maxIndex(smax);
			assertTrue("SoftMax test: ", DistributionChecker.checkDistribution(smax));
			assertTrue("The same max inedx ", dMaxIdx == sMaxIdx);
		}
		
		double[][] dnormDistribs= {{0,0,0},{0},{0,0},{Double.MIN_VALUE,Double.MIN_VALUE}, {Double.MAX_VALUE, Double.MAX_VALUE},
				{Double.MIN_VALUE, Double.MAX_VALUE}, {-Double.MIN_VALUE,-Double.MIN_VALUE},
				{-Double.MAX_VALUE, -Double.MAX_VALUE}, {-Double.MIN_VALUE, -Double.MAX_VALUE},
				{-Double.MIN_VALUE, Double.MAX_VALUE}, {Double.MIN_VALUE, -Double.MAX_VALUE},
				{Math.log(Math.exp(-1)),Math.log(Math.E)}};
		for (double[] ds : dnormDistribs) {
			double[] normalised = UtilsPT.softMax(ds);
			int maxIdx = Utils.maxIndex(ds);
			int sMax = Utils.maxIndex(normalised);
			assertTrue("Denorm distribs", DistributionChecker.checkDistribution(normalised));
			assertTrue("Denorm distribution max idx",maxIdx == sMax);
		}
		
		
			
		
	}
	
	@Test
	public void testSoftMin() {
		int lens[]= {1,2,3,5,10,100};
		for(int i=0;i<lens.length;i++) {
			double[] dat = this.generateDate(lens[i], i);
			int dMaxIdx = Utils.maxIndex(dat);
			double[] smax = UtilsPT.softMin(dat);
			int sMaxIdx = Utils.minIndex(smax);
			assertTrue("SoftMin test: ", DistributionChecker.checkDistribution(smax));
			assertTrue("The same max inedx ", dMaxIdx == sMaxIdx);
		}
		
		double[][] dnormDistribs= {{0,0,0},{0},{0,0},{Math.log(Math.exp(-1)),Math.log(Math.E)}};
		for (double[] ds : dnormDistribs) {
			double[] normalised = UtilsPT.softMin(ds);
			int maxIdx = Utils.maxIndex(ds);
			int sMax = Utils.minIndex(normalised);
			assertTrue("Denorm distribs", DistributionChecker.checkDistribution(normalised));
			//assertTrue("Denorm distribution max idx",maxIdx == sMax);
		}
			
		
	}
	
	@Test
	public void testParseIntOptions() {
		int testInt=1;
		String flag="C";
		int defVal=-55;
		
		String[] opts= {"-"+flag,""+testInt};
		int parsedInt = UtilsPT.parseIntegerOption(opts, flag, defVal);
		assertTrue("Parse integer option", parsedInt == testInt);
		
		String[] opts2= {"-"+flag,"B"};
		
		parsedInt = UtilsPT.parseIntegerOption(opts, flag, defVal);
		assertTrue("Parse integer option", parsedInt == defVal);
		
	}
	
	double[] generateDate(int numVals, int seed) {
		double[] genData = new double[numVals];
		Random rnd = new Random(seed);
		for(int i=0;i<genData.length;i++)
			genData[i] = rnd.nextDouble();
		
		return genData;
	}

}

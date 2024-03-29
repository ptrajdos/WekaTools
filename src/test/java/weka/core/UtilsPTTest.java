package weka.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;

import org.apache.commons.math3.special.Erf;
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
	public void testSkewnes() {
		Random rnd = new Random(10);
		int N = 10000;
		double[] distribution = new double[N];
		for(int i=0;i<distribution.length;i++)
			distribution[i] = rnd.nextGaussian();
		
		double skew = UtilsPT.skew(distribution);
		assertEquals(0.0, skew, 0.1);
		
		
	}
	
	@Test
	public void testKurtosis() {
		Random rnd = new Random(10);
		int N = 10000;
		double[] distribution = new double[N];
		for(int i=0;i<distribution.length;i++)
			distribution[i] = rnd.nextGaussian();
		
		double kurt = UtilsPT.kurtosis(distribution);
		assertEquals(0.0, kurt, 0.1);
		
		
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
	
	@Test
	public void testQuantilesGaussian() {
		
		int N = 30000;
		double[] vals  = new double[N];
		Random rnd = new Random(0);
		for(int i=0;i<vals.length;i++)
			vals[i]= rnd.nextGaussian(); // mu=0, sd=1
		
		double[] quantiles = new double[] {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
		
		for(int i=0;i<quantiles.length;i++) {
			double empQ = UtilsPT.quantile(vals, quantiles[i]);
			double theorQ = this.gaussQuantile(quantiles[i]);
			assertEquals("Normal dist Quantiles (" + quantiles[i] + "):",theorQ, empQ, 0.03);
		}
		
		double empIQR = UtilsPT.quantile(vals, 0.75) - UtilsPT.quantile(vals, 0.25);
		double theorIQR = this.gaussQuantile(0.75) - this.gaussQuantile(0.25);
		
		assertEquals( "Gaussian IQR", theorIQR, empIQR, 0.03);
		
		
	}
	
	@Test
	public void testSD() {
		
		int N = 30000;
		double[] vals  = new double[N];
		Random rnd = new Random(0);
		for(int i=0;i<vals.length;i++)
			vals[i]= rnd.nextGaussian(); // mu=0, sd=1
		
		double empSD = UtilsPT.stdDev(vals);
		double empVar = UtilsPT.var(vals);
		try {
			double empCoVar = UtilsPT.cov(vals, vals);
			double empCorr = UtilsPT.corr(vals, vals);
			assertEquals("Cov test", 1.0, empCoVar,0.01);
			assertEquals("Corr test", 1.0, empCorr,0.01);
		} catch (Exception e) {
			fail("An exception has been caught: " + e);
		}
		
		assertEquals( "Sdev for normal distribution", 1.0, empSD,0.01 );
		assertEquals( "Var for normal distribution", 1.0, empVar,0.01 );
		
	}
	
	protected double gaussQuantile(double p) {
		
		double val = Math.sqrt(2) * Erf.erfInv(2.0*p -1);
		return val;
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
			double[] dat = this.generateData(lens[i], i);
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
			double[] dat = this.generateData(lens[i], i);
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
	public void testGMean() {
		
		double[] vals = this.sampleGen(1000, 0);
		for(int i=0;i<vals.length;i++)
			vals[i]+=1E-6; // assure nonzero
		
		double gmean = UtilsPT.geometricMean(vals);
		double mean = Utils.mean(vals);
		assertFalse("Not NaN", Double.isNaN(gmean));
		assertTrue("Finite", Double.isFinite(gmean));
		assertTrue("Art mean > geom mean", mean >= gmean);
		
		vals[0]=0;
		gmean = UtilsPT.geometricMean(vals);
		mean = Utils.mean(vals);
		assertFalse("Not NaN", Double.isNaN(gmean));
		assertTrue("Inf",Double.isFinite(gmean));
		assertTrue("Zero mean", Utils.eq(gmean, 0.0));
		assertTrue("Art mean > geom mean", mean >= gmean);
		
		vals[0] = -1.0;
		gmean = UtilsPT.geometricMean(vals);
		assertTrue("NaN for negative", Double.isNaN(gmean));
		assertFalse("Negative value",Double.isFinite(gmean));
			
	}
	
	@Test
	public void testHMean() {
		
		double[] vals = this.sampleGen(1000, 0);
		for(int i=0;i<vals.length;i++)
			vals[i]+=1E-6; // assure nonzero
		
		double hmean = UtilsPT.harmonicMean(vals);
		double gmean = UtilsPT.geometricMean(vals);
		double mean = Utils.mean(vals);
		assertFalse("Not NaN", Double.isNaN(hmean));
		assertTrue("Finite", Double.isFinite(hmean));
		assertTrue("Art mean > harmonic mean", mean >= hmean);
		assertTrue("Geometric mean > harmonic mean", gmean >= hmean);
		
		vals[0]=0;
		hmean = UtilsPT.harmonicMean(vals);
		mean = Utils.mean(vals);
		assertFalse("Not NaN", Double.isNaN(hmean));
		assertTrue("Finite", Double.isFinite(hmean));
		assertTrue("Art mean > geom mean", mean >= hmean);
		assertTrue("Zero for zero value", Utils.eq(hmean, 0));	
	}
	
	@Test
	public void testQuadraticMean() {
		
		double[] vals = this.sampleGen(1000, 0);
		
		double qmean = UtilsPT.quadraticMean(vals);
		double mean = Utils.mean(vals);
		assertFalse("Not NaN", Double.isNaN(qmean));
		assertTrue("Finite", Double.isFinite(qmean));
		assertTrue("Art mean > harmonic mean", qmean >= mean);

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
	
	double[] generateData(int numVals, int seed) {
		double[] genData = new double[numVals];
		Random rnd = new Random(seed);
		for(int i=0;i<genData.length;i++)
			genData[i] = rnd.nextDouble();
		
		return genData;
	}
	
	@Test
	public void testKhanKlainSum() {
		double[] vals = new double[] {1.0,1.0E100,1.0,-1.0E100};
		
		double ks = UtilsPT.KhanKleinSum(vals);
		
		assertTrue("Small-large summation: ", Utils.eq(ks, 2.0));
		
		int N = 2000;
		
		double[] array = generateData(N, 0);
		
		double avg = UtilsPT.KhanKleinSum(array);
		avg/=(double)N;
		assertEquals("Average", 0.5, avg,1E-2);
		
	}
	
	

}

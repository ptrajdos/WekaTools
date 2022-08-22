package weka.core;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import weka.tools.SerialCopier;
import weka.tools.tests.EqualsChecker;

public class KhanKleinSummatorTest {

	@Test
	public void testSum1() {
		double[] values = new double[] {1.0,1.0E100,1.0,-1.0E100};
		
		KhanKleinSummator summator  = new KhanKleinSummator();
		
		assertEquals("Zero sum before adding values", 0, summator.getSum(), 1E-6);
		
		summator.addToSum(values);
		assertEquals("Sum1", 2.0, summator.getSum(), 1E-6);
		
		String description  = summator.toString();
		assertTrue("Not null String", description!=null);
		
		
		summator.reset();
		
		assertEquals("Zero after reset", 0,summator.getSum(),1E-6);
	}
	
	@Test
	public void testSerialization() {
		
		KhanKleinSummator summator = new KhanKleinSummator();
		
		KhanKleinSummator summator2 = new KhanKleinSummator();
		
		try {
			double[] data = generateData(100, 0);
			summator.addToSum(data);
			
			KhanKleinSummator cSumator =  (KhanKleinSummator) SerialCopier.makeCopy(summator);
			
			assertTrue("Equal with copy: ", summator.equals(cSumator));
			assertFalse("Not equal with other estimator.", summator2.equals(cSumator));
		} catch (Exception e) {
			fail("Excaption has been caught: " + e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testAddToSumCollection() {
		
		KhanKleinSummator summator = new KhanKleinSummator();
		KhanKleinSummator summator2 = new KhanKleinSummator();
		
		double[] data = generateData(100, 0);
		List<Double> dataL = new LinkedList<Double>();
		for(int i=0; i<data.length;i++)
			dataL.add(data[i]);
		
		summator.addToSum(data);
		summator2.addToSum(dataL);
		
		assertTrue("The same sum for different method of passing values", summator.equals(summator2));
		
	}
	@Test
	public void testEquality() {
		KhanKleinSummator summator = new KhanKleinSummator();
		EqualsChecker.checkEquality(summator);
	}
	
	protected double[] generateData(int number, int seed) {
		double[] values = new double[number];
		
		Random rnd = new Random(seed);
		for(int i=0;i<values.length;i++)
			values[i] = rnd.nextDouble();
		
		return values;
	}

}

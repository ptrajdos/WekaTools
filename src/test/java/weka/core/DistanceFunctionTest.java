/**
 * 
 */
package weka.core;

import java.util.Random;

import org.netlib.util.doubleW;

import junit.framework.TestCase;
import weka.core.neighboursearch.PerformanceStats;
import weka.tools.data.RandomDataGenerator;

/**
 * @author pawel
 *
 */
public abstract class DistanceFunctionTest extends TestCase {

	/**
	 * 
	 */
	public DistanceFunctionTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 */
	public DistanceFunctionTest(String name) {
		super(name);
	}
	
	public abstract DistanceFunction getDistanceFunction();
	
	
	public void testDistances() {
		this.checkDistance(getDistanceFunction());
	}
	
	public void testInvertSelection() {
		DistanceFunction dFun = this.getDistanceFunction();
		dFun.setInvertSelection(true);
		assertTrue("Get Invert Selection", dFun.getInvertSelection());
		this.checkDistance(dFun);
	}
	
	public void testCustomAttribs() {
		DistanceFunction dFun = this.getDistanceFunction();
		String selAttribs="1,2";
		dFun.setAttributeIndices(selAttribs);
		assertTrue("Get Attribs", dFun.getAttributeIndices().equals(selAttribs));
		this.checkDistance(dFun);
	}
	
	public void checkDistance(DistanceFunction dist) {
		DistanceFunction dFun = dist;
		
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances data = gen.generateData();
		int numInstances = data.numInstances();
		
		dFun.setInstances(data);
		Instances tmpData = dFun.getInstances();
		
		assertTrue("Get the same instances header",tmpData.equalHeaders(data));
		
		
		
		double cutOff =0.1;
		
		Random rnd = new Random();
		int numTries = 100;
		PerformanceStats perfStats = new PerformanceStats();
		
		
		try {
			//Update using the same instances
			for (Instance instance : tmpData) {
				dFun.update(instance);
			}
			
		for(int i=0;i<numTries;i++) {
			Instance first = data.get(rnd.nextInt(numInstances));
			Instance second = data.get(rnd.nextInt(numInstances));
			Instance third = data.get(rnd.nextInt(numInstances));
			double distance = dFun.distance(first, second);
			assertTrue("Distance is finite", Double.isFinite(distance));
			assertTrue("Distance greater/eq than zero", distance>=0);
			
			
			
			
			double distance2 = dFun.distance(second, first);
			assertTrue("switch", Utils.eq(distance, distance2));
			
			
			distance2=dFun.distance(first, third);
			double distance3 = dFun.distance(third, second);
			assertTrue("Triangle Inequality", distance <= (distance2 + distance3));
			
			double[] pProcDists = {distance,distance2,distance3};
			dFun.postProcessDistances(pProcDists);
			for (double d : pProcDists) {
				assertTrue("Post processed Finite", Double.isFinite(d));
				assertTrue("Post processed Greater/eq zero", d>=0);
			}
			assertTrue("Postprocessed triangle inequality",pProcDists[0] <= (pProcDists[1] + pProcDists[2]) );
			
			
			distance = dFun.distance(first, second, perfStats);
			assertTrue("Distance is finite. Performance stats", Double.isFinite(distance));
			
			 distance2 = dFun.distance(first, second, cutOff);
			
			boolean isCorrect = distance>=cutOff? true: Double.isFinite(distance2);
			assertTrue("Distance cutoff", isCorrect);
			
			distance  = dFun.distance(first, second, cutOff, perfStats);
			
			
			
		}
		
		 
		
		}catch(Exception e) {
			fail("Distance Function test. Exception has been caught: " + e.toString());
		}
		
		dFun.clean();
		Instances cleanedData = dFun.getInstances();
		assertTrue("Cleanded Instances not null", cleanedData !=null);
		assertTrue("Cleaned Instances", cleanedData.numInstances() ==0);
		assertTrue("Cleaned Instances Header", cleanedData.equalHeaders(data));
	}

}

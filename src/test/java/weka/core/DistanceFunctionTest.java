/**
 * 
 */
package weka.core;

import java.util.Random;

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
		DistanceFunction dFun = this.getDistanceFunction();
		
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
			
			distance = dFun.distance(first, second, perfStats);
			assertTrue("Distance is finite. Performance stats", Double.isFinite(distance));
			
			 distance2 = dFun.distance(first, second, cutOff);
			
			boolean isCorrect = distance>=cutOff? true: Double.isFinite(distance2);
			assertTrue("Distance cutoff", isCorrect);
			
			
			
		}
		}catch(Exception e) {
			fail("Distance Function test. Exception has been caught: " + e.toString());
		}
		
	}

}

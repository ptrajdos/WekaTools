/**
 * 
 */
package weka.tools.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;
import weka.tools.data.RandomDataGenerator;

/**
 * @author pawel
 *
 */
public class NoInstancesCheckerTest{

	
	public void checkNoInstancesChecker(int numInstances2Gen)throws Exception {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumObjects(numInstances2Gen);
		
		Instances data = gen.generateData();
		
		int[] numInstances2Check = {-1,0,1,2,3,10,30};
		Classifier classifier = new IBk(1);
		
		for(int i=0;i<numInstances2Check.length;i++)
			try {
				boolean result = NoInstancesChecker.performCheck(data, classifier,numInstances2Check[i],i);
				assertTrue("Check result should be true: ", result);
				result = NoInstancesChecker.performCheck(data, classifier);
				assertTrue("Check result should be true: ", result);
			} catch (Exception e) {
				e.printStackTrace();
				fail("Exception has been caught: " + e.getLocalizedMessage());
			}
		
	}
	
	@Test
	public void testNoInstancesChecker() {
		int[] numInstances2Gen = {1,2,3,10,100};
		for(int i=0;i<numInstances2Gen.length;i++) {
			
			try {
				this.checkNoInstancesChecker(numInstances2Gen[i]);
			} catch (Exception e) {
				fail("An exception has been caught: " + e.getLocalizedMessage());
			}
		}
	}
	
	@Test
	public void testEmptySet() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumObjects(0);
		Instances data = gen.generateData();
		Classifier classifier = new IBk(1);
		
		try {
			NoInstancesChecker.performCheck(data, classifier);
			fail("An Exception should have been thrown");
		} catch (Exception e) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void testFailingClassifier() {
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances data = gen.generateData();
		Classifier classifier = new AbstractClassifier() {
			
			@Override
			public void buildClassifier(Instances data) throws Exception {
				//DO nothing!
			}
			
			@Override
			public double[] distributionForInstance(Instance instance) throws Exception {
				return null;
			}
		};
		
		boolean result;
		try {
			result = NoInstancesChecker.performCheck(data, classifier);
			assertFalse("This classifier should have failed the test", result);
		} catch (Exception e) {
			fail("An Exception has been caught: " + e.getLocalizedMessage());
		}
		
	}

}

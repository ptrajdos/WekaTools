package weka.tools.data;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.Instances;

public class NominalClassShifterTest extends TestCase {

	@Test
	public void testShiftClasses() {
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances origData = gen.generateData();
		int numClasses = origData.numClasses();
		Instances transformedData; 
		for(int i=0;i<numClasses;i++) {
			transformedData= NominalClassShifter.shiftClasses(origData, i);
			assertTrue("Data compatibility", origData.equalHeaders(transformedData));
		}
		NominalClassShifter shi = new NominalClassShifter();
		assertTrue("Not null", shi!=null);
	}

}

package weka.tools.data;

import static org.junit.Assert.*;

import org.junit.Test;

import weka.core.Attribute;
import weka.core.Instances;

public class AttributeReordererTest {

	@Test
	public void testReorder() {
		RandomDataGenerator gen = new RandomDataGenerator();
		
		Instances data = gen.generateData();
		
		try {
			Instances reorderedData = AttributeReorderer.reorder(data);
			assertTrue("Not null", reorderedData!=null);
			assertTrue("Equall number of attributes", reorderedData.numAttributes() == data.numAttributes());
			
			int numAttributes = data.numAttributes();
			
			for(int i=0;i<numAttributes;i++) {
				int j = numAttributes-1 -i;
				Attribute at1 = data.attribute(i);
				Attribute at2 = reorderedData.attribute(j);
				assertTrue("Equall attributes", at1.equalsMsg(at2) == null);
			}
			
		} catch (Exception e) {
			fail("An exception has been caught: " + e);
		}
	}

}

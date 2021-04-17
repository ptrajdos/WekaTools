package weka.tools.data;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.Instances;
import weka.tools.data.AttributeCounter.Type;

public class AttributeCounterTest extends TestCase {
	
	

	@Test
	public void test() {
		RandomDataGenerator gen = new RandomDataGenerator();
		int numAttrType = 10;
		gen.setNumStringAttributes(numAttrType);
		gen.setNumDateAttributes(numAttrType);
		gen.setNumNominalAttributes(numAttrType);
		gen.setNumNumericAttributes(numAttrType);
		gen.setAddClassAttrib(false);
		
		Instances data = gen.generateData();
		
		AttributeCounter obj = new AttributeCounter();
		
		AttributeCounter.Type[] types = {AttributeCounter.Type.NOMINAL,AttributeCounter.Type.STRING,
				AttributeCounter.Type.DATE,AttributeCounter.Type.NUMERIC};
		for (Type type : types) {
			int attrNum = AttributeCounter.countAttribs(data, type);
			assertTrue("Count attributes: " + type , numAttrType == attrNum );
		}
		
	}

}

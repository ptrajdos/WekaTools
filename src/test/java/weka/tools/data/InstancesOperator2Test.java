package weka.tools.data;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Utils;

public class InstancesOperator2Test extends TestCase {

	@Test
	public void testNormalization() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNumericAttributes(10);
		gen.setNumNominalAttributes(0);
		gen.setNumStringAttributes(0);
		gen.setNumDateAttributes(0);
		gen.setAddClassAttrib(false);
		gen.setNumObjects(1000);
		Instances data = gen.generateData();
		
		try {
			Instances transformed = InstancesOperator.normalizeData(data);
			int numAttribs = transformed.numAttributes();
			
			for(int a=0;a<numAttribs;a++) {
				Attribute tmpAttribute = transformed.attribute(a);
				
				double lower = tmpAttribute.getLowerNumericBound();
				double upper = tmpAttribute.getUpperNumericBound();
				double meanOrMode = transformed.meanOrMode(tmpAttribute);
				if(tmpAttribute.getMetadata() !=null) {
					assertTrue("Greater than or equal zero", lower>=0);
					assertTrue("Less than or equal one",upper <=1);
				}

				assertTrue("Mean equall to 0.5", Math.abs(meanOrMode -0.5)<0.1);
				
			}
			
		} catch (Exception e) {
			fail("An exception has been caught!");
		}
	}
	
	@Test
	public void testStandarization() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNumericAttributes(10);
		gen.setNumNominalAttributes(0);
		gen.setNumStringAttributes(0);
		gen.setNumDateAttributes(0);
		gen.setAddClassAttrib(false);
		gen.setNumObjects(1000);
		Instances data = gen.generateData();
		
		try {
			Instances transformed = InstancesOperator.standarizeData(data);
			int numAttribs = transformed.numAttributes();
			
			for(int a=0;a<numAttribs;a++) {
				Attribute tmpAttribute = transformed.attribute(a);
				
				double lower = tmpAttribute.getLowerNumericBound();
				double upper = tmpAttribute.getUpperNumericBound();
				double meanOrMode = transformed.meanOrMode(tmpAttribute);
				if(tmpAttribute.getMetadata() !=null) {
					assertTrue("Greater than or equal zero", lower>=0);
					assertTrue("Less than or equal one",upper <=1);
				}

				assertTrue("Mean equall to 0.5", Math.abs(meanOrMode -0.0)<0.1);
				
			}
			
		} catch (Exception e) {
			fail("An exception has been caught!");
		}
	}
	
	@Test
	public void testDesparse() {
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances data = gen.generateData();
		try {
			Instances transformed = InstancesOperator.desparse(data);
			assertTrue("Not null", transformed!=null);
		} catch (Exception e) {
			fail("An exception has been caught");
		}
		
	}

}

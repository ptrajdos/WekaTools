package weka.tools.data;

import static org.junit.Assert.*;

import org.junit.Test;

import weka.core.Attribute;
import weka.core.Instances;

public class RandomDataGeneratorTest {

	@Test
	public void testNumericAttribs() {
		RandomDataGenerator gen = new RandomDataGenerator();
		
		int[] numAttribSeq = {1,2,4,6,100,3,5,1000};
		for(int n=0;n<numAttribSeq.length;n++) {
			gen.setNumNumericAttributes(numAttribSeq[n]);
			int counted = AttributeCounter.countAttribs(gen.generateData(), AttributeCounter.Type.NUMERIC);
			assertTrue("Numeric attributes: ", numAttribSeq[n]==counted);
		}
		
	}
	@Test
	public void testNumberInstances() {
		RandomDataGenerator gen = new RandomDataGenerator();
		int[] numInstancesSeq= {1,10,20,100,1000};
		for(int n=0;n<numInstancesSeq.length;n++) {
			gen.setNumObjects(numInstancesSeq[n]);
			int nInst = gen.generateData().numInstances();
			assertTrue("Num Instances: ", nInst == numInstancesSeq[n]);
		}
	}
	
	@Test
	public void testNominalAttribs() {
		RandomDataGenerator gen = new RandomDataGenerator();
		
		int[] nomAttribSeq = {1,2,4,6,100,3,5,1000};
		boolean[] classGen= {false,true};
		for(int i=0;i<classGen.length;i++) {
			gen.setAddClassAttrib(classGen[i]);
			for(int n=0;n<nomAttribSeq.length;n++) {
				gen.setNumNominalAttributes(nomAttribSeq[n]);
				int cnt = AttributeCounter.countAttribs(gen.generateData(), AttributeCounter.Type.NOMINAL);
				assertTrue("Nominal Attrs",cnt == (classGen[i]? nomAttribSeq[n]+1: nomAttribSeq[n] ) );
			}
		}
		
	}
	
	@Test 
	public void testNumClasses() {
		RandomDataGenerator gen = new RandomDataGenerator();
		
		int[] numClassSeq= {2,3,10,20};
		gen.setAddClassAttrib(true);
		
		for(int c =0;c<numClassSeq.length;c++) {
			gen.setNumClasses(numClassSeq[c]);
			int cnt = gen.generateData().numClasses();
			assertTrue("Num classes", cnt == numClassSeq[c]);
			
		}
		
		
	}
	
	@Test
	public void testNomNumVals() {
		RandomDataGenerator gen = new RandomDataGenerator();
		int[] numVals = {2,4,5,7,8};
		for(int v=0;v<numVals.length;v++) {
			gen.setMaxNumNominalValues(numVals[v]);
			Instances dataset = gen.generateData();
			int numAttrs = dataset.numAttributes();
			for(int a=0;a<numAttrs;a++) {
				Attribute tmpAttr = dataset.attribute(a);
				if(tmpAttr.isNominal()) {
					int vals = tmpAttr.numValues();
					assertTrue("Attribute Vals", vals<=numVals[v]);
				}
			}
		}
	}
	
	

}

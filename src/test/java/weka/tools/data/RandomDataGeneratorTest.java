package weka.tools.data;

import static org.junit.Assert.assertTrue;

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
			assertTrue("NumAttribs", gen.getNumNumericAttributes()== numAttribSeq[n]);
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
			assertTrue("Num Instances", gen.getNumObjects() == numInstancesSeq[n]);
			int nInst = gen.generateData().numInstances();
			assertTrue("Num Instances: ", nInst == numInstancesSeq[n]);
		}
	}
	
	@Test
	public void testNominalAttribs() {
		RandomDataGenerator gen = new RandomDataGenerator();
		
		int[] nomAttribSeq = {1,2,4,6,100,3,5,1000};
		boolean[] classGen= {false,true};
		boolean[] unaryAllow= {false,true};
		for(int u=0;u<unaryAllow.length;u++) {
			gen.setAllowUnary(unaryAllow[u]);
			assertTrue("Allow Unary", gen.isAllowUnary() == unaryAllow[u]);
			
			for(int i=0;i<classGen.length;i++) {
				gen.setAddClassAttrib(classGen[i]);
				for(int n=0;n<nomAttribSeq.length;n++) {
					gen.setNumNominalAttributes(nomAttribSeq[n]);
					assertTrue("NumNominal Attributes", gen.getNumNominalAttributes() == nomAttribSeq[n]);
					int cnt = AttributeCounter.countAttribs(gen.generateData(), AttributeCounter.Type.NOMINAL);
					assertTrue("Nominal Attrs",cnt == (classGen[i]? nomAttribSeq[n]+1: nomAttribSeq[n] ) );
				}
			}
		}
		
	}
	
	@Test 
	public void testNumClasses() {
		RandomDataGenerator gen = new RandomDataGenerator();
		
		int[] numClassSeq= {2,3,10,20};
		gen.setAddClassAttrib(true);
		assertTrue("Add Class Attrib", gen.isAddClassAttrib());
		
		for(int c =0;c<numClassSeq.length;c++) {
			gen.setNumClasses(numClassSeq[c]);
			assertTrue("Num Classes: ", gen.getNumClasses() == numClassSeq[c]);
			int cnt = gen.generateData().numClasses();
			assertTrue("Num classes", cnt == numClassSeq[c]);
			
		}
		
		
	}
	
	@Test
	public void testNomNumVals() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNominalAttributes(1000);
		int[] numVals = {2,4,5,7,8};
		for(int v=0;v<numVals.length;v++) {
			gen.setMaxNumNominalValues(numVals[v]);
			assertTrue("Gen Max num Nominal Values", gen.getMaxNumNominalValues() == numVals[v]);
			Instances dataset = gen.generateData();
			int numAttrs = dataset.numAttributes();
			for(int a=0;a<numAttrs;a++) {
				Attribute tmpAttr = dataset.attribute(a);
				if(tmpAttr.isNominal()) {
					int vals = tmpAttr.numValues();
					assertTrue("Attribute Vals", vals<=numVals[v] & vals>=1);
				}
			}
		}
	}
	
	@Test
	public void testSeed() {
		RandomDataGenerator gen = new RandomDataGenerator();
		int seed = gen.getSeed();
		assertTrue("Seed default", seed==0);
		seed=6;
		gen.setSeed(seed);
		assertTrue("Seed seed", seed == gen.getSeed());
	}
	
	@Test
	public void testStringAtts() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNominalAttributes(0);
		gen.setNumNumericAttributes(0);
		int numStrinAttrs=10;
		gen.setNumStringAttributes(numStrinAttrs);
		assertTrue("Number of string attributes", numStrinAttrs == gen.getNumStringAttributes());
		
		Instances data = gen.generateData();
		assertTrue("Not null", data!=null);
		int numAttrs = data.numAttributes();
		int classIdx = data.classIndex();
		for(int a=0;a<numAttrs;a++) {
			if(a==classIdx)
				continue;
			assertTrue("Only String attribs", data.attribute(a).isString());
		}
	}
	
	@Test
	public void testDateAttribs() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNominalAttributes(0);
		gen.setNumNumericAttributes(0);
		gen.setNumStringAttributes(0);
		int numDateAttributes = 10;
		gen.setNumDateAttributes(numDateAttributes);
		assertTrue("Num Date attributes", numDateAttributes == gen.getNumDateAttributes());
		String dateFormat = gen.getDateFormatString();
		gen.setDateFormatString(dateFormat);
		
		Instances data = gen.generateData();
		assertTrue("Not null", data!=null);
		int numAttrs = data.numAttributes();
		int classIdx = data.classIndex();
		for(int a=0;a<numAttrs;a++) {
			if(a==classIdx)
				continue;
			assertTrue("Only Date attribs", data.attribute(a).isDate());
		}
	}
	
	@Test 
	public void testSetStringGen() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setStringGen(new RandomStringGenerator());
	}
	
	@Test
	public void testSetDateGenerator() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setDateGen(new RandomDateGenerator());
	} 
	
	@Test
	public void testSetDoubleGenerator() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setDoubleGen(new RandomDoubleGeneratorGaussian());
	}

}

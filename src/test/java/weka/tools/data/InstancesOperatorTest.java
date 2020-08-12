package weka.tools.data;

import java.util.ArrayList;
import java.util.LinkedList;

import junit.framework.TestCase;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.tools.tests.DistributionChecker;

public class InstancesOperatorTest extends TestCase {

	public void testSplitClassSpec() {
		ArrayList<Attribute> atts = new ArrayList<Attribute>(3);
	      atts.add(new Attribute("X1"));
	      LinkedList<String> valList = new LinkedList<String>();
	      valList.add("1");
	      valList.add("2");
	      atts.add(new Attribute("Class", valList));
	      
	      LinkedList<String> valList2 = new LinkedList<String>();
	      valList2.add("A");
	      valList2.add("B");
	      atts.add(new Attribute("Nomi1", valList2));
	      
	      Instances dataset = new Instances("daataset",atts,1);
	      dataset.setClassIndex(2);
	      
	      
	      dataset.add(new DenseInstance(1.0, new double[] {-3,0,1}));
	      dataset.add(new DenseInstance(1.0, new double[] {-2,1,1}));
	      dataset.add(new DenseInstance(1.0, new double[] {-1,1,1}));
	      dataset.add(new DenseInstance(1.0, new double[] {0,0,1}));
	      dataset.add(new DenseInstance(1.0, new double[] {1,0,0}));
	      dataset.add(new DenseInstance(1.0, new double[] {2,1,0}));
	      dataset.add(new DenseInstance(1.0, new double[] {3,1,0}));
	      
	      try {
			Instances[] classSpec = InstancesOperator.classSpecSplit(dataset);
			assertTrue("Num Classes", classSpec.length==2);
			assertTrue("Num instances 1", classSpec[0].numInstances() ==3 );
			assertTrue("Num instances 2", classSpec[1].numInstances() ==4 );
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been caught");
		}
	    		  
	}
	
	public void testClassFreq() {
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances data = gen.generateData();
		
		double[] distr=null;
		try {
			distr = InstancesOperator.classFreq(data);
		} catch (Exception e) {
			e.printStackTrace();
			fail("testClassFreq: An exception has been caught: " + e.toString());
		}
		assertTrue("Class Freqs", DistributionChecker.checkDistribution(distr));
		
		
	} 
	
	public void testClassFreqEmpty() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumObjects(0);
		Instances data = gen.generateData();
		
		double[] distr=null;
		try {
			distr = InstancesOperator.classFreq(data);
		} catch (Exception e) {
			e.printStackTrace();
			fail("testClassFreqEmpty: An exception has been caught: " + e.toString());
		}
		assertTrue("Class Freqs", DistributionChecker.checkDistribution(distr));
		
		
	}

}

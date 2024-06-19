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
	
	protected void checkObjCounts(int[] counts, Instances data) {
		assertTrue("Not null.", counts!=null);
		int sum=0;
		int numInstances = data.numInstances();
		for(int i=0;i<counts.length;i++) {
			sum+=counts[i];
			assertTrue("Greater than zero", counts[i]>=0);
			assertTrue("Less than the number of instances", counts[i]<=numInstances);
		}
		if(sum>0)
			assertTrue("Sum equality", sum==numInstances);
	}
	
	public void testObjPerClass() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumObjects(100);
		Instances data = gen.generateData();
		Instances emptySet = new Instances(data,0);
		
		try {
			int[] counts = InstancesOperator.objPerClass(data);
			this.checkObjCounts(counts, data);
			
			counts = InstancesOperator.objPerClass(emptySet);
			this.checkObjCounts(counts, data);
			
		} catch (Exception e) {
			fail("An exception has been caught");
		}
		
		
	}
	
	public void testUniqObjPerClass() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumObjects(100);
		Instances data = gen.generateData();
		Instances emptySet = new Instances(data,0);
		
		try {
			int[] counts = InstancesOperator.uniqObjPerClass(data);
			this.checkObjCounts(counts, data);
			
			counts = InstancesOperator.objPerClass(emptySet);
			this.checkObjCounts(counts, data);
			
		} catch (Exception e) {
			fail("An exception has been caught");
		}
		
		
		try {
			Instances nonUniqSet = new Instances(data,0);
			Instances[] classSplitted = InstancesOperator.classSpecSplit(data);
			int rep = 5;
			for(int c =0; c<classSplitted.length; c++) {
				for (int r =0; r<rep; r++) {
					nonUniqSet.add(classSplitted[c].get(0));
				}
			}
			
			int[] counts = InstancesOperator.uniqObjPerClass(nonUniqSet);
			for(int i =0 ;i< counts.length; i++) {
				assertTrue("Wrong count: "+ counts[i] + " should be: 1" , counts[i] == 1);
			}
			
		} catch (Exception e) {
			fail("An exception has been caught");
		}
		
		
	}
	
	protected void instancesCheck(Instances data) {
		assertTrue("Not null set.", data!=null);
		assertTrue("Number of instances", data.numInstances()>=0);
	}
	
	protected void instancesCheck(Instances[] dataArr) {
		for(int i=0;i<dataArr.length;i++)
			this.instancesCheck(dataArr[i]);
		
	}
	
	public void testGenerateBaggingSample() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumObjects(100);
		
		Instances data = gen.generateData();
		try {
			Instances d1 = InstancesOperator.generateBaggingSample(data);
			instancesCheck(d1);
			
			d1 = InstancesOperator.generateBaggingSample(data, 0, 1.0);
			instancesCheck(d1);
			
			d1 = InstancesOperator.generateBaggingSample(data, 0, 0.0);
			instancesCheck(d1);
			
			Instances emptySet = new Instances(data,0);
			
			d1 = InstancesOperator.generateBaggingSample(emptySet);
			instancesCheck(d1);
			
		} catch (Exception e) {
			fail("An Exception has been caught: ");
		}
		
	}
	
	public void testSplitSet() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumObjects(100);
		
		Instances data = gen.generateData();
		
		Instances[] d1 = InstancesOperator.splitSet(data, 0.5);
		this.instancesCheck(d1);
		
		d1 = InstancesOperator.splitSet(data, 0.0);
		this.instancesCheck(d1);
		
		d1 = InstancesOperator.splitSet(data, 1.0);
		this.instancesCheck(d1);
		
		
		Instances emptySet = new Instances(data,0);
		d1 = InstancesOperator.splitSet(emptySet, 0.5);

	}
	
	public void testSplitSet2() {
		RandomDataGenerator gen = new RandomDataGenerator();
		int numInstances = 100;
		gen.setNumObjects(numInstances);
		
		Instances data = gen.generateData();
		
		Instances[] d1 = InstancesOperator.splitSet(data, numInstances/2);
		this.instancesCheck(d1);
		
		d1 = InstancesOperator.splitSet(data, 0);
		this.instancesCheck(d1);
		
		d1 = InstancesOperator.splitSet(data, numInstances);
		this.instancesCheck(d1);
		
		Instances emptySet = new Instances(data,0);
		
		d1 = InstancesOperator.splitSet(emptySet, numInstances);
		this.instancesCheck(d1);
	}
	
	public void testStratifiedSplitSet() {
		
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumObjects(100);
		
		Instances data = gen.generateData();
		
		Instances[] d1 = InstancesOperator.stratifiedSplitSet(data, 0.0, 0);
		this.instancesCheck(d1);
		
		d1 = InstancesOperator.stratifiedSplitSet(data, 1.0, 0);
		this.instancesCheck(d1);
		
		Instances emptySet = new Instances(data,0);
		
		d1 = InstancesOperator.stratifiedSplitSet(data, 0.5, 0);
		this.instancesCheck(d1);

	}

}

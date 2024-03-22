package weka.tools;

import java.util.Arrays;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.Instance;
import weka.core.Instances;
import weka.tools.data.InstancesOperator;
import weka.tools.data.RandomDataGenerator;

public class InstancesToolsTest extends TestCase {

	
	@Test
	public void testCreateObject() {
		InstancesTools insTools = new InstancesTools();
		assertTrue("Not null", insTools != null);
	}
	
	@Test
	public void testChecks() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNominalAttributes(0);
		gen.setNumStringAttributes(0);
		gen.setNumDateAttributes(0);
		Instances dataset = gen.generateData();
		
		
		Instance tmpInstance1 = dataset.get(0);
		Instance tmpInstance2 = dataset.get(1);
		Instance copiedInstance = InstancesTools.copyInstance(tmpInstance2);
		
		
		Instances datasetCompatible = gen.generateData();
		Instance comInstance1 = datasetCompatible.get(0);
		
		
		
		
		
		
		try {
			
			RandomDataGenerator gen2 = (RandomDataGenerator) SerialCopier.makeCopy(gen);
			gen2.setNumClasses(3);
			
			Instances datasetOtherClass = gen2.generateData();
			Instance incInstanceCl = datasetOtherClass.get(0);
			

			
			
			
			assertTrue("Instance vs dataset", InstancesTools.checkCompatibility(dataset, tmpInstance1));
			assertTrue("Instance vs dataset", InstancesTools.checkCompatibility(dataset, tmpInstance1,false));
			assertTrue("Instance vs dataset", InstancesTools.checkCompatibility(dataset, tmpInstance2));
			assertTrue("Instance vs dataset", InstancesTools.checkCompatibility(dataset, tmpInstance2,false));
			assertTrue("Instance vs dataset", InstancesTools.checkCompatibility(dataset, comInstance1));
			assertTrue("Instance vs dataset", InstancesTools.checkCompatibility(dataset, comInstance1,false));
			
			//
			assertTrue("Instance vs class-incomaptible dataset", InstancesTools.checkCompatibility(dataset,incInstanceCl,false));

			assertTrue("Instance vs class-incomaptible dataset", InstancesTools.checkCompatibility(datasetOtherClass,tmpInstance1,false));

			
			
			assertTrue("Instance vs instance", InstancesTools.checkCompatibility(tmpInstance2, tmpInstance1));
			assertTrue("Instance vs instance", InstancesTools.checkCompatibility(tmpInstance2, tmpInstance1,false));
			assertTrue("Instance vs instance", InstancesTools.checkCompatibility(tmpInstance2, comInstance1));
			assertTrue("Instance vs instance", InstancesTools.checkCompatibility(tmpInstance2, comInstance1,false));
			
			
			assertTrue("Equality check no class", InstancesTools.checkEquall(tmpInstance1, tmpInstance1, false));
			assertTrue("Equality check with class", InstancesTools.checkEquall(tmpInstance1, tmpInstance1, true));
			
			assertFalse("Equality check no class", InstancesTools.checkEquall(tmpInstance1, tmpInstance2, false));
			assertFalse("Equality check with class", InstancesTools.checkEquall(tmpInstance1, tmpInstance2, true));
			
			assertTrue("Copied not null", copiedInstance!=null);
			assertTrue("Copied equall", InstancesTools.checkEquall(tmpInstance2, copiedInstance, true));
		} catch (Exception e) {
			fail("An exception has been caught: " + e.getMessage());
		}
		
	}
	
	@Test
	public void testIncompatibleData() {
		
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNominalAttributes(0);
		gen.setNumStringAttributes(0);
		gen.setNumDateAttributes(0);
		Instances dataset = gen.generateData();
		
		
		Instance tmpInstance1 = dataset.get(0);
		Instance tmpInstance2 = dataset.get(1);
		
		Instances diffSet1 = gen.generateData();
		diffSet1.setClassIndex(0);
		Instance diffInstance1 = diffSet1.get(0);
		
		try {
			assertFalse("Instance vs dataset (should fail)", InstancesTools.checkCompatibility(dataset, diffInstance1));
			fail("An exception should have been thrown");
		} catch (Exception e) {
			assertTrue(true);
		}
		try {
			assertFalse("Instance vs dataset (should fail)", InstancesTools.checkCompatibility(tmpInstance1, diffInstance1));
			fail("An exception should have been thrown");
		}catch(Exception e) {}
		
		try {
			RandomDataGenerator gen2 = (RandomDataGenerator) SerialCopier.makeCopy(gen);
			gen2.setNumNominalAttributes(20);
			Instances nomAttribSet = gen2.generateData();
			Instance tmpInstance  =nomAttribSet.get(0);
			
			Instances nomAttribSet2 = gen2.generateData();
			Instance tmpInstance3  =nomAttribSet2.get(0);
			
			try {
				assertFalse("Instance vs dataset (should fail)", InstancesTools.checkCompatibility(nomAttribSet, tmpInstance1));
				fail("Exception should has been thrown");
			}catch(Exception a) {
				assertTrue(true);
			}
			
			try {
				assertFalse("Instance vs dataset (should fail)", InstancesTools.checkCompatibility(tmpInstance, tmpInstance1));
				fail("Exception should has been thrown");
			}catch(Exception a) {
				assertTrue(true);
			}
			
			try {
				assertFalse("Instance vs dataset (should fail)", InstancesTools.checkCompatibility(dataset, tmpInstance));
				fail("Exception should has been thrown");
			}catch(Exception a) {}
			
			try {
				assertFalse("Instance vs dataset (should fail)", InstancesTools.checkCompatibility(nomAttribSet, tmpInstance3));
				fail("Exception should has been thrown");
			}catch(Exception a) {}
			
			try {
				assertFalse("Instance vs dataset (should fail)", InstancesTools.checkCompatibility(tmpInstance, tmpInstance3));
				fail("Exception should has been thrown");
			}catch(Exception a) {}
			
			try {
				assertFalse("Instance vs dataset (should fail)", InstancesTools.checkCompatibility(nomAttribSet2, tmpInstance));
				fail("Exception should has been thrown");
			}catch(Exception a) {}
			
			RandomDataGenerator gen3 = (RandomDataGenerator) SerialCopier.makeCopy(gen);
			gen3.setNumClasses(3);
			
			Instances datasetOtherClass = gen3.generateData();
			Instance incInstanceCl = datasetOtherClass.get(0);
			
			try {
			assertFalse("Instance vs class-incomaptible dataset", InstancesTools.checkCompatibility(dataset,incInstanceCl));
			fail("Exception should has been thrown");
			}catch(Exception e) {}
			
			try {
			assertFalse("Instance vs class-incomaptible dataset", InstancesTools.checkCompatibility(datasetOtherClass,tmpInstance1));
			fail("Exception should has been thrown");
			}catch(Exception e) {}
			
			
		} catch (Exception e) {
			
			fail("Copying failed");
		}
		
	}
	
	@Test
	public void testClassFreqs() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNominalAttributes(0);
		gen.setNumStringAttributes(0);
		gen.setNumDateAttributes(0);
		Instances dataset = gen.generateData();
		int[] classCounts = InstancesTools.getClassCounts(dataset);
		int[] classCounts2;
		try {
			classCounts2 = InstancesOperator.objPerClass(dataset);
			assertTrue("Counting instances per class", Arrays.equals(classCounts, classCounts2));
		} catch (Exception e) {
			fail("An exception has been caught" + e.getMessage());
		}
		
	}
	
	@Test
	public void testUniqueInstances_NoReps() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNominalAttributes(0);
		gen.setNumStringAttributes(0);
		gen.setNumDateAttributes(0);
		int nInstances = 100;
		gen.setNumObjects(nInstances);
		
		Instances dataset = gen.generateData();
		
		int nUniqueInstances = InstancesTools.countUniqieInstances(dataset);
		assertTrue(String.format("Invalid number of unique Instances. Is %s, should be %s ",nUniqueInstances, nInstances), nUniqueInstances == nInstances);
		
		
	}
	
	@Test
	public void testUniqueInstances_Reps() {
		RandomDataGenerator gen = new RandomDataGenerator();
		gen.setNumNominalAttributes(0);
		gen.setNumStringAttributes(0);
		gen.setNumDateAttributes(0);
		int nInstances = 12;
		gen.setNumObjects(10);
		
		Instances dataset = gen.generateData();
		
		Instances eDataset = new Instances(dataset, 0);
		for(int i=0;i<nInstances;i++) {
			eDataset.add( dataset.get(0) );
		}
		
		int nUniqueInstances = InstancesTools.countUniqieInstances(eDataset);
		assertTrue(String.format("Invalid number of unique Instances. Is %s, should be %s ",nUniqueInstances, nInstances), nUniqueInstances == 1);
		
		
	}

}

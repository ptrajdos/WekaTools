package weka.core;

import java.util.Arrays;

import org.junit.Test;

import junit.framework.TestCase;
import weka.tools.data.RandomDataGenerator;

public class DenseInstanceFastTest extends TestCase {

	@Test
	public void testDenseInstance() {
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances data = gen.generateData();
		Instance testInstance1 = data.get(0);
		Instance testInstance2 = data.get(2);
		
		DenseInstanceFast inst1 = new DenseInstanceFast(testInstance1);
		DenseInstanceFast inst2 = new DenseInstanceFast(10);
		DenseInstanceFast inst3 = new DenseInstanceFast(testInstance1.weight(), testInstance1.toDoubleArray());
		DenseInstanceFast inst4 = (DenseInstanceFast) inst3.copy();
		
		assertTrue("Copy by constructor -- arrays", Arrays.equals(testInstance1.toDoubleArray(), inst3.toDoubleArray()));
		assertTrue("Copy by constructor -- arrays", Arrays.equals(testInstance1.toDoubleArray(), inst4.toDoubleArray()));
		assertTrue("Equals", inst3.equals(inst3));
		
		int hash = inst3.hashCode();
		
		DenseInstance merged = (DenseInstance) inst3.mergeInstance(inst4);
		assertTrue("Num attrs in merged instance", merged.numAttributes() == 2* inst3.numAttributes());
		
		
		
		
		
	}

}

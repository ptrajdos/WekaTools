package weka.tools;

import org.junit.Test;

import junit.framework.TestCase;
import weka.classifiers.bayes.NaiveBayes;

public class SerializationTesterTest extends TestCase {

	@Test
	public void testProperSerialization() {
		
		NaiveBayes nb = new NaiveBayes();
		
		assertTrue("Correct serialization", SerializationTester.checkSerialization(nb));
		
		assertFalse("No serialization", SerializationTester.checkSerialization(new SerializationTester()));
		
		
	}
	
	public void testCreation() {
		SerializationTester tester = new SerializationTester();
		assertTrue("Not null", tester != null);
	}

}

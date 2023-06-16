/**
 * 
 */
package weka.tools.data.splitters;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.Instances;
import weka.tools.SerialCopier;
import weka.tools.data.RandomDataGenerator;

/**
 * @author pawel trajdos
 *
 */
public abstract class DataSplitterTest extends TestCase {
	
	public abstract DataSplitter getSplitter();

	@Test
	public void testSplitter() {
		DataSplitter splitter = this.getSplitter();
		
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances estimData = gen.generateData();
		
		splitter.train(estimData);
		
		Instances[] splitted = splitter.split(estimData);
		
		assertTrue("Not null", splitted != null);
		assertTrue("More than two elements", splitted.length >= 2);
		
		for(int i=0;i < splitted.length;i++) {
			assertTrue("Data not null", splitted[i] != null);
			assertTrue("One or more instances", splitted[i].numInstances()>= 1);
		}
	}
	
	public void testSerialization() {
		
		DataSplitter splitter = this.getSplitter();
		
		try {
			DataSplitter copy = (DataSplitter) SerialCopier.makeCopy(splitter);
		} catch (Exception e) {
			
			fail("Cannot copy the object via serialization!");
		}
		
	}

}

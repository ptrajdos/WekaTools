package weka.tools.data;

import org.junit.Test;

import junit.framework.TestCase;



public class RandomStringGeneratorTest extends TestCase {

	@Test
	public void testRndStrings() {
		RandomStringGenerator gen  =new RandomStringGenerator();
		int[] strLengths= {1,3,5,6,10};
		for(int i=0;i<strLengths.length;i++) {
			checkString(gen.generateNextString(strLengths[i]), strLengths[i]);
		}	
	}
	
	public void checkString(String str, int expLength) {
		assertTrue("Not null.", str != null);
		assertTrue("Nonzero Length", str.length()>0);
		assertTrue("Expected length", str.length()==expLength );
	}
	
	public void testSeed() {
		RandomStringGenerator gen  =new RandomStringGenerator();
		int seed = gen.getSeed();
		assertTrue("Default Value of Seed", seed==0);
		seed =10; 
		gen.setSeed(seed);
		assertTrue("Change seed", gen.getSeed() == seed);
	}
	
	public void testAlphabet() {
		RandomStringGenerator gen  =new RandomStringGenerator();
		String alphabet = gen.getAlphabet();
		assertTrue("Not null.", alphabet != null);
		assertTrue("Nonzero Length", alphabet.length()>0);
		
		alphabet="ABCD";
		gen.setAlphabet(alphabet);
		
		assertTrue("Changed alphabet", alphabet.equals(gen.getAlphabet()));
	}

}

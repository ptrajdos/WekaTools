package weka.tools.data;

import org.junit.Test;

import junit.framework.TestCase;

public abstract class IRandomDoubleGeneratorTest extends TestCase {
	
	public abstract IRandomDoubleGenerator getRandomDoubleGenerator();

	@Test
	public void testSeeds() {
		IRandomDoubleGenerator gen = this.getRandomDoubleGenerator();
		long seed = gen.getSeed();
		long newSeed = 666;
		gen.setSeed((int) newSeed);
		assertTrue("Seed correct assing", newSeed == gen.getSeed());
	}
	
	@Test
	public void testRandomData() {
		int numReps =100;
		IRandomDoubleGenerator gen = this.getRandomDoubleGenerator();
		for(int i=0;i<numReps;i++) {
			double rndVal = gen.getNextDouble();
			assertTrue("Finite double", Double.isFinite(rndVal));
			assertTrue("Not NaN", !Double.isNaN(rndVal));
		}
		
	}

}

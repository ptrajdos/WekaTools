package weka.tools.data;

import org.junit.Test;

import weka.core.Utils;
import weka.tools.SerialCopier;

public abstract class RandomDoubleGeneratorTest extends IRandomDoubleGeneratorTest {

	@Test
	public void testSerialization() {
		
		IRandomDoubleGenerator gen = this.getRandomDoubleGenerator();
		try {
			IRandomDoubleGenerator copy = (IRandomDoubleGenerator) SerialCopier.makeCopy(gen);
		} catch (Exception e) {
			fail("Exception during serial copying");
		}
		
	}
	
	@Test
	public void testOffset() {
		RandomDoubleGenerator gen = (RandomDoubleGenerator) this.getRandomDoubleGenerator();
		assertTrue("Is initial offset zero?", Utils.eq(0, gen.getOffset()));
		double testOffset=6.66;
		gen.setOffset(testOffset);
		assertTrue("Set offset", Utils.eq(testOffset, gen.getOffset()));
	}
	
	@Test 
	public void testDivisor(){
		RandomDoubleGenerator gen = (RandomDoubleGenerator) this.getRandomDoubleGenerator();
		assertTrue("Is initial divisor one?", Utils.eq(1, gen.getDivisor()));
		double testDivisor=0.5;
		gen.setDivisor(testDivisor);
		assertTrue("Set Divisor", Utils.eq(testDivisor, gen.getDivisor()));
	}

}

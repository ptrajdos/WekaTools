package weka.tools.fuzzyNorms.sNorms;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import weka.core.Utils;
import weka.tools.tests.SerializationChecker;

public abstract class FuzzySNormTest {

	public abstract FuzzySNorm getSNorm();
	
	@Test 
	public void testSerialization() {
		SerializationChecker.checkSerializationCopy(getSNorm());
	}
	
	@Test
	public void testSnormProps() {
		int numReps = 10;
		Random rnd = new Random(0);
		FuzzySNorm snorm = this.getSNorm();
		for(int i=0;i<numReps;i++) {
			double x = rnd.nextDouble();
			double y = rnd.nextDouble();
			double z = rnd.nextDouble();
			assertTrue("Cummutativity", Utils.eq(snorm.calculateNorm(x, y),snorm.calculateNorm(y, x)));
			assertTrue("Cummutativity", Utils.eq(snorm.calculateNorm(x, z),snorm.calculateNorm(z, x)));
			assertTrue("Cummutativity", Utils.eq(snorm.calculateNorm(z, y),snorm.calculateNorm(y, z)));
			
			assertTrue("Check 0 neutrality", Utils.eq(snorm.calculateNorm(0, x), x));
			assertTrue("Check 0 neutrality", Utils.eq(snorm.calculateNorm(0, y), y));
			assertTrue("Check 0 neutrality", Utils.eq(snorm.calculateNorm(0, z), z));
			assertTrue("Check 0 neutrality", Utils.eq(snorm.calculateNorm(0, 0), 0));
			
			
			if(Utils.grOrEq(y, x)) {
				assertTrue("Monotony:", Utils.grOrEq( snorm.calculateNorm(y, z) , snorm.calculateNorm(x, z)  ));
			}else {
				assertTrue("Monotony:", Utils.grOrEq( snorm.calculateNorm(x, z) , snorm.calculateNorm(y, z)  ));
			}
			
			double yz = snorm.calculateNorm(y, z);
			double xy = snorm.calculateNorm(x, y);
			
			assertTrue("Associativity:", Utils.eq(snorm.calculateNorm(x, yz), snorm.calculateNorm(xy, z)));
			
			assertTrue("Check zeros: ", Utils.eq(0, snorm.calculateNorm(0, 0)));
			assertTrue("Check ones: ", Utils.eq(1, snorm.calculateNorm(1, 1)));
		}
		
	}
	
	

}

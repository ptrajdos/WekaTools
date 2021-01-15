package weka.tools.fuzzyNorms.tNorms;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import weka.core.Utils;
import weka.tools.tests.SerializationChecker;

public abstract class FuzzyTNormTest {

	public abstract FuzzyTNorm getTNorm();
	
	@Test 
	public void testSerialization() {
		SerializationChecker.checkSerializationCopy(getTNorm());
	}
	
	@Test
	public void testSnormProps() {
		int numReps = 10;
		Random rnd = new Random(0);
		FuzzyTNorm tnorm = this.getTNorm();
		for(int i=0;i<numReps;i++) {
			double x = rnd.nextDouble();
			double y = rnd.nextDouble();
			double z = rnd.nextDouble();
			assertTrue("Cummutativity", Utils.eq(tnorm.calculateNorm(x, y),tnorm.calculateNorm(y, x)));
			assertTrue("Cummutativity", Utils.eq(tnorm.calculateNorm(x, z),tnorm.calculateNorm(z, x)));
			assertTrue("Cummutativity", Utils.eq(tnorm.calculateNorm(z, y),tnorm.calculateNorm(y, z)));
			
			assertTrue("Check 1 neutrality", Utils.eq(tnorm.calculateNorm(1, x), x));
			assertTrue("Check 1 neutrality", Utils.eq(tnorm.calculateNorm(1, y), y));
			assertTrue("Check 1 neutrality", Utils.eq(tnorm.calculateNorm(1, z), z));
			assertTrue("Check 1 neutrality", Utils.eq(tnorm.calculateNorm(1, 0), 0));
			assertTrue("Check 1 neutrality", Utils.eq(tnorm.calculateNorm(0, 1), 0));
			
			if(Utils.grOrEq(y, x)) {
				assertTrue("Monotony:", Utils.grOrEq( tnorm.calculateNorm(y, z) , tnorm.calculateNorm(x, z)  ));
			}else {
				assertTrue("Monotony:", Utils.grOrEq( tnorm.calculateNorm(x, z) , tnorm.calculateNorm(y, z)  ));
			}
			
			double yz = tnorm.calculateNorm(y, z);
			double xy = tnorm.calculateNorm(x, y);
			
			assertTrue("Associativity:", Utils.eq(tnorm.calculateNorm(x, yz), tnorm.calculateNorm(xy, z)));
			
			assertTrue("Check zeros: ", Utils.eq(0, tnorm.calculateNorm(0, 0)));
			assertTrue("Check ones: ", Utils.eq(1, tnorm.calculateNorm(1, 1)));
		}
		
	}
}

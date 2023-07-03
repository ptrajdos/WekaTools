package weka.tools.arrayFunctions;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.Test;

public abstract class MultivariateFunctionTest {
	
	public abstract List<MultivariateFunction> getFunctions();
	
	
	public void checkValue(double val) {
		assertFalse("NaN", Double.isNaN(val));
		assertTrue("Finite", Double.isFinite(val));
		
	}

	@Test
	public void testArray() {
		List<MultivariateFunction> functions = this.getFunctions();
		Random rnd = new Random(0);
		int N = 10;
		
		double[] vals = new double[N];
		
		for(int i=0;i <vals.length;i++)
			vals[i] = rnd.nextDouble();
		
		for (MultivariateFunction fun : functions) {
			double val = fun.value(vals);
			this.checkValue(val);
		}
		
		
		
		
		
		
		
		
	}
	
	@Test
	public void testZeros() {
		List<MultivariateFunction> functions = this.getFunctions();
		
		int N = 10;
		
		double[] vals = new double[N];
		
		for(int i=0;i <vals.length;i++)
			vals[i] = 0.0;
		
		for (MultivariateFunction fun : functions) {
			
			double val = fun.value(vals);
			this.checkValue(val);
			
		}
		
	}
	
	@Test 
	public void testOneElement() {
		
		List<MultivariateFunction> functions = this.getFunctions();
		double[] vals = new double[] {1.0};
		
		for(MultivariateFunction fun : functions) {
			double val = fun.value(vals);
			this.checkValue(val);
		}
		

		
	}

}

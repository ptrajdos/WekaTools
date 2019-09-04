package weka.tools;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import weka.core.Utils;

public class LinspaceTest {
	
	protected double lower=0;
	protected double upper=1;
	protected int seqLen=10;
	protected double step = (upper -lower)/(seqLen-1);
	
	protected void linSpaceTester(double[] ls) {
		assertNotNull("Null.", ls);
		assertTrue("Length", ls.length == seqLen);
		double partialStep;
		for(int i=0;i<ls.length;i++) {
			assertFalse("Nan", Double.isNaN(ls[i]));
			assertTrue("Finite", Double.isFinite(ls[i]));
			if(i<ls.length-1) {
				partialStep = ls[i+1] - ls[i];
				assertTrue("Partial Step", Utils.eq(partialStep, step));
			}
		}
		assertTrue("Lower Bound", Utils.eq(ls[0], lower));
		assertTrue("Upper Bound", Utils.eq(ls[seqLen-1], upper));
		System.out.print(Arrays.toString(ls));
	}

	@Test
	public void test1() {
		double[] lin = Linspace.genLinspace(lower, upper, seqLen);
		linSpaceTester(lin);
		
	}
	
	@Test
	public void test2() {
		double[] lin = Linspace.genLinspaceSym(lower, upper, seqLen);
		linSpaceTester(lin);
		
	}
	
	@Test
	public void test3() {
		double[] lin = Linspace.genLinspaceSym(lower, upper, step);
		linSpaceTester(lin);
		
	}
	
	@Test
	public void test4() {
		double[] lin = Linspace.genLinspace(lower, upper, step);
		linSpaceTester(lin);
		
	}

}

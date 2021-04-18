package weka.intPermutation;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import junit.framework.TestCase;


public class IntPermutationTest extends TestCase {

	@Test
	public void testCreate() {
		int permLen=10;
		IntPermutation perm = new IntPermutation(permLen);
		
		int[] permutation = IntPermutationOperator.genPermutation(0, permLen);
		assertTrue("Correct?", IntPermutationOperator.isCorrect(permutation));
		IntPermutation perm2 = new IntPermutation(permutation);
		
		assertTrue("Arrays the same", Arrays.equals(permutation, perm2.getArray()));
		assertTrue("Min", perm2.getMin() == 0);
		assertTrue("Min", perm2.getMax() == 9);
		
		int[] prePerm = IntPermutationOperator.genPermutation(0, 11, false);
		IntPermutation permInc = new IntPermutation(prePerm);
		
		assertFalse("InConsistent", perm2.isConsistentWith(null));
		assertFalse("InConsistent", perm2.isConsistentWith(permInc));
		assertTrue("Consistent", perm2.isConsistentWith(perm2));
		
		assertTrue("Consistent", IntPermutationOperator.areConsistent(perm2.getArray(), perm2.getArray()));
		assertFalse("Consistent", IntPermutationOperator.areConsistent(perm2.getArray(), permInc.getArray() ));
		
		IntPermutation prod = perm2.productPermutation(perm);
		assertTrue("Consistent prod", perm2.isConsistentWith(perm));
		assertTrue("Consistent prod", perm2.isConsistentWith(perm2));
		
		IntPermutation inverted = perm2.getInversePermutation();
		int[] invertedArray = IntPermutationOperator.reversePermutation(perm2.getArray());
		assertTrue("Consistent inverted", perm2.isConsistentWith(inverted));
		assertTrue("Inverted Arrays", IntPermutationOperator.areConsistent(perm2.getArray(), invertedArray));
		
		
		assertTrue(perm2.toString()!=null);
		perm.shufflePermutation(0);
		assertTrue("Shuffled", perm2.isConsistentWith(perm) );
	}
	
	public void testFailedCreation() {
		try {
			int[] inc = {-1,0};
			assertFalse("Should be invalid", IntPermutationOperator.isCorrect(inc));
			IntPermutation perm2 = new IntPermutation(inc);
			fail("Should have failed");
		}catch(Exception e) {
			
		}
		
		try {
			int[] inc = {3,5};
			assertFalse("Should be invalid", IntPermutationOperator.isCorrect(inc));
			IntPermutation perm2 = new IntPermutation(inc);
			fail("Should have failed");
		}catch(Exception e) {
			
		}
		
		try {
			int[] inc = {0,5};
			assertFalse("Should be invalid", IntPermutationOperator.isCorrect(inc));
			IntPermutation perm2 = new IntPermutation(inc);
			fail("Should have failed");
		}catch(Exception e) {
			
		}
	}
	
	public void testEquals() {
		int permLen=10;
		IntPermutation perm = new IntPermutation(permLen);
		IntPermutation permB = new IntPermutation(permLen);
		IntPermutation perm2 = new IntPermutation(permLen);
		perm2.shufflePermutation(0);
		
		assertFalse("Eq with null", perm.equals(null));
		assertTrue("Eq with self", perm.equals(perm));
		assertTrue("Eq with the same", perm.equals(permB));
		assertFalse("Eq with other", perm.equals(perm2));
		
		
	}
	
	public void testCycles() {
		int permLen=10;
		IntPermutation perm = new IntPermutation(permLen);
		List<Set<Integer>> cycles = perm.findCycles();
		assertTrue("Cycles not null", cycles!=null);
	}

}

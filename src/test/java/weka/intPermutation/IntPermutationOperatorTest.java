package weka.intPermutation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import org.junit.Test;

import junit.framework.TestCase;

public class IntPermutationOperatorTest extends TestCase {

	@Test
	public void testCreation() {
		assertTrue(new IntPermutationOperator() !=null);
	}
	
	public void testPrints() {
		IntPermutation perm = new IntPermutation(10);
		
		IntPermutationOperator.printPermutation(perm.getArray());
		
		Writer out = new Writer() {
			
			@Override
			public void write(char[] cbuf, int off, int len) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void flush() throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void close() throws IOException {
				// TODO Auto-generated method stub
				
			}
		};
		BufferedWriter bw = new BufferedWriter(out );
		try {
			IntPermutationOperator.printPermutation(perm.getArray(), bw);
		} catch (IOException e) {
			fail("Exception has been caught: "+  e.getMessage());
		}
	}

}

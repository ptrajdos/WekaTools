package weka.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

public class OptionToolsTest extends TestCase {

	@Test
	public void testCreation() {
		OptionTools tools = new OptionTools();
		assertTrue("Not null", tools!=null);
	}
	
	@Test 
	public void testConcat() {
		String[] a1= {"a","b"};
		String[] a2 = {"c","d"};
		List<String[]> initList = new ArrayList<String[]>();
		assertTrue("Null result", OptionTools.strArraysConcat(initList)==null);
		
		initList.add(a1);
		initList.add(a2);
		String[] concat = {"a","b","c","d"};
		String[] result = OptionTools.strArraysConcat(initList);
		assertTrue("String concatenation", Arrays.deepEquals(concat, result));
		
	}

}

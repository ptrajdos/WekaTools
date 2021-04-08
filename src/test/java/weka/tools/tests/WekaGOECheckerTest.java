package weka.tools.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import weka.classifiers.trees.J48;

public class WekaGOECheckerTest {

	@Test
	public void testGlobalInfo() {
		
		WekaGOEChecker checker = new WekaGOEChecker();
		checker.setObject(new J48());
		
		
		boolean result = checker.checkCallGlobalInfo();
		assertTrue("Class has GlobalInfo", result);
		
		
		checker.setObject("XXX");
		result = checker.checkCallGlobalInfo();
		assertFalse("No global info", result);
	}
	
	@Test 
	public void testTipTexts() {
		WekaGOEChecker checker = new WekaGOEChecker();
		checker.setObject(new J48());
		
		boolean result = checker.checkToolTipsCall();
		assertTrue("All tip text have been called properly.", result);
		
		
	}

}

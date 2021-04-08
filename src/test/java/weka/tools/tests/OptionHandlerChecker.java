/**
 * 
 */
package weka.tools.tests;



import junit.framework.TestCase;
import weka.core.CheckOptionHandler;
import weka.core.OptionHandler;


/**
 * @author pawel trajdos
 * @since 1.9.0
 * @version 1.9.0
 *
 */
public class OptionHandlerChecker {

	public static void checkOptions(OptionHandler handler) {
		
		CheckOptionHandler optionTester;

	    optionTester = new CheckOptionHandler();
	    optionTester.setOptionHandler(handler);
	    optionTester.setUserOptions(new String[0]);
	    optionTester.setSilent(true);
	    
	    
	    TestCase.assertTrue("Canonical user options test",optionTester.checkCanonicalUserOptions());
	    TestCase.assertTrue("Default options test",optionTester.checkDefaultOptions());
	    TestCase.assertTrue("List options test",optionTester.checkListOptions());
	    TestCase.assertTrue("Remaining options test",optionTester.checkRemainingOptions());
	    TestCase.assertTrue("Resetting options test",optionTester.checkResettingOptions());
	    TestCase.assertTrue("Set options test",optionTester.checkSetOptions());
		
	    
	    
	}

}

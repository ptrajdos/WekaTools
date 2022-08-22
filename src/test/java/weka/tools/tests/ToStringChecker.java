/**
 * 
 */
package weka.tools.tests;

import junit.framework.TestCase;

/**
 * Performs basic checking of toStringMethod.
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 * 
 *
 */
public class ToStringChecker {

	public static void checkToString(Object obj) {
		
		String description = obj.toString();
		
		TestCase.assertTrue("Not null description", description!= null);
		TestCase.assertTrue("Description length greater than zero", description.length()>0);
	}

}

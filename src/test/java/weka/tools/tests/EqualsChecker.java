/**
 * 
 */
package weka.tools.tests;

import junit.framework.TestCase;

/**
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 * 
 *
 */
public class EqualsChecker {

	
	public static void checkEquality(Object obj) {
		
		
		TestCase.assertTrue("Self equality", obj.equals(obj));
		
		class SampleObj{
			//Dummy class -- for tests only
		}
		
		Object sampleObject = new SampleObj();
		
		TestCase.assertFalse("Unequality with made-up object", obj.equals(sampleObject));
	}

}

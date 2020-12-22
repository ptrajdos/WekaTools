/**
 * 
 */
package weka.tools.tests;

import weka.tools.SerialCopier;

/**
 * 
 * Check if the obejct may be copied by serialization
 * @author pawel trajdos
 * @since 1.8.0
 * @version 1.8.0
 * 
 *
 */
public class SerializationChecker {

	public static boolean checkSerializationCopy(Object obj) {
		
		try {
			Object copy = SerialCopier.makeCopy(obj);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

}

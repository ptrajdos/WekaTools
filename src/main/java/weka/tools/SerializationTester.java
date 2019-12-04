/**
 * 
 */
package weka.tools;

/**
 * Object that checks serialization
 * @author pawel trajdos
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class SerializationTester {

	/**
	 * Check if the object can ber serialized succesfully
	 * @param o
	 * @return
	 */
	public static boolean checkSerialization(Object o) {
		Object copied;
		try {
			copied = SerialCopier.makeCopy(o);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}

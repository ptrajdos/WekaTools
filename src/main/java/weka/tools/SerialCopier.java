/**
 * 
 */
package weka.tools;

import weka.core.SerializedObject;

/**
 * @author pawel trajdos
 * @since 0.1.0
 * @version 0.1.0
 *
 */
public class SerialCopier {

	public static Object makeCopy(Object obj) throws Exception{
		
		return new SerializedObject(obj).getObject();
	}
	
	
}

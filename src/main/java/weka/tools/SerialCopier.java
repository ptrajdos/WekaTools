/**
 * 
 */
package weka.tools;

import weka.core.SerializedObject;

/**
 * @author pawel
 *
 */
public class SerialCopier {

	public static Object makeCopy(Object obj) throws Exception{
		
		return new SerializedObject(obj).getObject();
	}
	
}

/**
 * 
 */
package weka.tools.tests;

import weka.core.RevisionHandler;

/**
 * Class for checking the Revision
 * @author pawel trajdos
 * @since 1.3.0
 * @version 1.3.0
 *
 */
public class RevisionChecker {
	
	public static boolean checkRevision(Object obj) {
		
		if(obj instanceof RevisionHandler) {
			String revision = ((RevisionHandler) obj).getRevision();
			if(revision == null)
				return false;
			
			if(revision.length()==0)
				return false;
			
		}else {
			return false;
		}
		
		return true;
	}

	
}

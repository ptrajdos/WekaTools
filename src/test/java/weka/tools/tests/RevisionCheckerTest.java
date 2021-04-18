package weka.tools.tests;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.RevisionHandler;

public class RevisionCheckerTest extends TestCase {
	
	protected class RevH1 implements RevisionHandler{

		@Override
		public String getRevision() {
			return null;
		}
		
	}
	
	protected  class RevH2 implements RevisionHandler{

		@Override
		public String getRevision() {
			return "";
		}
		
	}
	
	protected class RevH3 implements RevisionHandler{
		@Override
		public String getRevision() {
			return "Revision";
		}
	}

	@Test
	public void testCreate() {
		RevisionChecker checker = new RevisionChecker();
		assertTrue(checker!=null);
	}
	
	@Test
	public void testCheckRevision() {
		assertFalse("Null object", RevisionChecker.checkRevision(null));
		assertFalse("Object with no revision", RevisionChecker.checkRevision(new RevisionChecker()));
		assertFalse("Object with null revision", RevisionChecker.checkRevision(new RevH1() ));
		assertFalse("Object with empty revision", RevisionChecker.checkRevision(new RevH2() ));
		assertTrue("Object with revision", RevisionChecker.checkRevision(new RevH3() ));
	}

}

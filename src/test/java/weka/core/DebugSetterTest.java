package weka.core;

import org.junit.Test;

import junit.framework.TestCase;

public class DebugSetterTest extends TestCase {

	protected class X implements Debuggable{

		boolean isDebug=false;
		@Override
		public boolean isDebug() {
			return isDebug;
		}

		@Override
		public void setDebug(boolean debug) {
			this.isDebug = debug;
			
		}
		
	}
	@Test
	public void test() {
		X x  =new X();
		DebugSetter setter = new DebugSetter();
		DebugSetter.setDebug(x, true);
		DebugSetter.setDebug(setter, false);
	}

}

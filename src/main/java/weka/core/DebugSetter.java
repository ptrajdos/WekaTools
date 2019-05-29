package weka.core;

public class DebugSetter {

	public static void setDebug(Object obj, boolean debug) {
		if(obj instanceof Debuggable) {
			((Debuggable) obj).setDebug(debug);
		}
	}
}

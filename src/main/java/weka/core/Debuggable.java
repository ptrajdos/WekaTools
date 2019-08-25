package weka.core;

/**
 * An interface for classes that offer debugging mode
 * @author pawel trajdos
 * @since 0.8.0
 * @version 0.8.0
 */
public interface Debuggable {
	public boolean isDebug();
	public void setDebug(boolean debug);

}

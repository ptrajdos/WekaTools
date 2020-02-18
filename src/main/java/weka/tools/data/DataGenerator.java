/**
 * 
 */
package weka.tools.data;

import weka.core.Instances;

/**
 * An interface for objects able to generate the data
 * @author pawel trajdos
 * @since 1.2..0
 * @version 1.2.0
 *
 */
public interface DataGenerator {
	
	public Instances generateData();

}

package weka.tools.data;

import weka.core.Randomizable;
/**
 * An interface for generating random dobule values
 * @author pawel trajdos
 *
 */

public interface IRandomDoubleGenerator extends Randomizable {
	
	
	public double getNextDouble();

}

/**
 * 
 */
package weka.tools.data;

import weka.core.Instance;
import weka.core.Instances;

/**
 * @author pawel trajdos
 * @since 1.7.0
 * @version 1.7.0
 *
 */
public class NominalClassShifter {

	/**
	 * Generates new set with shifted class assignment
	 * @param data -- data to generate the new set
	 * @param shift -- number of positions to shift
	 * @return  Shifted dataset
	 */
	public static Instances shiftClasses(Instances data, int shift) {
		Instances result = new Instances(data);
		int numInstances = result.size();
		int numClasses = result.numClasses();
		Instance tmpInstance = null;
		int shift2 = shift;
		shift2 = Math.abs(shift2);
		double newClass=0;
		for(int i=0;i<numInstances;i++) {
			tmpInstance = result.get(i);
			newClass = (tmpInstance.classValue() + shift2)%numClasses; 
			tmpInstance.setClassValue(newClass);
		}
		
		return result;
	}

}

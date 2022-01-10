/**
 * 
 */
package weka.tools.data;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Reorder;

/**
 * @author pawel trajdos
 * @since 1.13.0
 * @version 1.13.0
 *
 */
public class AttributeReorderer {

	/**
	 * Put attributes in reversed order
	 * @param inputData
	 * @return
	 * @throws Exception
	 */
	public static Instances reorder(Instances inputData) throws Exception{
		
		Reorder reo = new Reorder();
		String range = "last";
		Instances tmpInstances = inputData;
		for(int i=tmpInstances.numAttributes()-1;i>0;i--){
			range+="," +i;
		}
		reo.setAttributeIndices(range);
		reo.setInputFormat(tmpInstances);
		Instances transformed = Filter.useFilter(tmpInstances, reo);
		
		return transformed;
	}

}

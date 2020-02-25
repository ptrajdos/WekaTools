/**
 * 
 */
package weka.tools.data;

import weka.core.Instances;

/**
 * The class implements attribute counting
 * @author pawel trajdos
 * @since 1.2.0
 * @version 1.2.0
 *
 */
public class AttributeCounter {

	public static enum Type{NUMERIC,NOMINAL,STRING,DATE}
	
	public static int countAttribs(Instances dataset, Type type) {
		int numAttrs = dataset.numAttributes();
		int cntAttribs =0;
		for(int a=0;a<numAttrs;a++) {
			switch (type) {
			case NUMERIC:
				if(dataset.attribute(a).isNumeric())
					cntAttribs++;
				break;
			case NOMINAL:
				if(dataset.attribute(a).isNominal())
					cntAttribs++;
				break;
				
			case STRING:
				if(dataset.attribute(a).isString())
					cntAttribs++;
				break;
				
			case DATE:
				if(dataset.attribute(a).isDate())
					cntAttribs++;
				break;

			default:
				break;
			}
		}
		return cntAttribs;
	}
	

}

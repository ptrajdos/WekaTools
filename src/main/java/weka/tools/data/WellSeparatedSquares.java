/**
 * 
 */
package weka.tools.data;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

/**
 * @author pawel trajdos
 * @since 1.4.0
 * @version 1.4.0
 *
 */
public class WellSeparatedSquares extends DataGeneratorA {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4569880167171503920L;
	
	protected double overleap = 0.0;

	/**
	 * 
	 */


	@Override
	protected void fillWithInstances(Instances dataset) {
		int numAttrs = dataset.numAttributes();
		
		int classAttribIdx = dataset.classIndex();
		Attribute classAttrib = dataset.classAttribute();
		
		if(!classAttrib.isNominal())
			return;
		
		
		
		for(int i=0;i<this.numObjects;i++) {
			
			Attribute tmpAttr = null;
			double[] instanceRep = new double[numAttrs];
			
			double classVal = this.rnd.nextInt(classAttrib.numValues());
			instanceRep[classAttribIdx] = classVal;

			
			for(int a=0;a<numAttrs;a++) {
				if(a==classAttribIdx)continue;
				
				tmpAttr = dataset.attribute(a);
				
				if(tmpAttr.isNumeric()) {
					instanceRep[a] = this.rnd.nextDouble() + classVal *(1.0 - this.overleap);
					continue;
				}
				
				if(tmpAttr.isNominal()) {
					int numVals = tmpAttr.numValues();
					instanceRep[a] = this.rnd.nextInt(numVals);
					continue;
				}
			}
			dataset.add(new DenseInstance(1.0, instanceRep));	
		}

	}

	/**
	 * @return the overleap
	 */
	public double getOverleap() {
		return this.overleap;
	}

	/**
	 * @param overleap the overleap to set
	 */
	public void setOverleap(double overleap) {
		if(overleap > 1)
			this.overleap=1.0;
		if(overleap <0)
			this.overleap=0.0;
		
		this.overleap = overleap;
	}
	

}

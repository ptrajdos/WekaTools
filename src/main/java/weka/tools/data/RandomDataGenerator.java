package weka.tools.data;

import java.io.Serializable;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.Randomizable;

public class RandomDataGenerator extends DataGeneratorA implements Serializable, DataGenerator, Randomizable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4367823102888277085L;

	
	protected void fillWithInstances(Instances dataset) {
		
		int numAttrs = dataset.numAttributes();
		for(int i=0;i<this.numObjects;i++) {
			
			Attribute tmpAttr = null;
			double[] instanceRep = new double[numAttrs];
			for(int a=0;a<numAttrs;a++) {
				tmpAttr = dataset.attribute(a);
				
				if(tmpAttr.isNumeric()) {
					instanceRep[a] = this.rnd.nextDouble();
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

}

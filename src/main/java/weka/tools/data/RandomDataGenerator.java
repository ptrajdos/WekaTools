package weka.tools.data;

import java.io.Serializable;
import java.text.ParseException;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.Randomizable;

/**
 * Generates random data for test purposed
 * @author pawel trajdos
 * @version 1.9.1
 * @since  1.4.0
 */

public class RandomDataGenerator extends DataGeneratorA implements Serializable, DataGenerator, Randomizable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4367823102888277085L;
	
	protected RandomStringGenerator stringGen ;
	
	protected RandomDateGenerator dateGen;
	
	public RandomDataGenerator() {
		this.initInternals();
	}
	
	private void initInternals() {
		this.stringGen = new RandomStringGenerator();
		this.stringGen.setSeed(this.seed);
		
		this.dateGen = new RandomDateGenerator();
		this.dateGen.setSeed(this.seed);
		this.dateGen.setDateFormat(this.dateFormat);
	}

	
	protected void fillWithInstances(Instances dataset) {
		
		int numAttrs = dataset.numAttributes();
		for(int i=0;i<this.numObjects;i++) {
			
			Attribute tmpAttr = null;
			double[] instanceRep = new double[numAttrs];
			for(int a=0;a<numAttrs;a++) {
				tmpAttr = dataset.attribute(a);
				
				
				if(tmpAttr.isNominal()) {
					int numVals = tmpAttr.numValues();
					instanceRep[a] = this.rnd.nextInt(numVals);
					continue;
				}
				if(tmpAttr.isString()) {
					int strLen = this.rnd.nextInt(20) +1;
					instanceRep[a] = tmpAttr.addStringValue(this.stringGen.generateNextString(strLen));
					continue;
				}
				if(tmpAttr.isDate()) {
					try {
						instanceRep[a] = tmpAttr.parseDate(this.dateGen.getNextDateString());
					} catch (ParseException e) {
						e.printStackTrace();
						//This should not happend
					}
					continue;
				}
				if(tmpAttr.isNumeric()) {
					instanceRep[a] = this.rnd.nextDouble();
					continue;
				}
			}
			dataset.add(new DenseInstance(1.0, instanceRep));
			
		}
		
		
	}

	@Override
	public void setSeed(int seed) {
		super.setSeed(seed);
		this.initInternals();
	}

	@Override
	public void setDateFormatString(String dateFormatString) {
		super.setDateFormatString(dateFormatString);
		this.initInternals();
	}
	
	

}

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
 * @version 1.10.0
 * @since  1.4.0
 */

public class RandomDataGenerator extends DataGeneratorA implements Serializable, DataGenerator, Randomizable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4367823102888277085L;
	
	protected IRandomStringGenerator stringGen = new RandomStringGenerator();;
	
	protected IRandomDateGenerator dateGen = new RandomDateGenerator();
	
	protected IRandomDoubleGenerator doubleGen = new RandomDoubleGeneratorUniform();
	
	public RandomDataGenerator() {
		this.initInternals();
	}
	
	private void initInternals() {
		this.stringGen.setSeed(this.seed);
		
		this.dateGen.setSeed(this.seed);
		this.dateGen.setDateFormat(this.dateFormat);
		
		this.doubleGen.setSeed(this.seed);
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
					instanceRep[a] = this.doubleGen.getNextDouble();
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
		this.dateGen.setDateFormat(this.dateFormat);
	}

	
	/**
	 * @param stringGen the stringGen to set
	 */
	public void setStringGen(IRandomStringGenerator stringGen) {
		this.stringGen = stringGen;
		this.stringGen.setSeed(this.seed);
	}

	/**
	 * @param dateGen the dateGen to set
	 */
	public void setDateGen(IRandomDateGenerator dateGen) {
		this.dateGen = dateGen;
		this.dateGen.setSeed(this.seed);
	}

	/**
	 * @param doubleGen the doubleGen to set
	 */
	public void setDoubleGen(IRandomDoubleGenerator doubleGen) {
		this.doubleGen = doubleGen;
		this.doubleGen.setSeed(this.seed);
	}
	
	
	
	
	

}

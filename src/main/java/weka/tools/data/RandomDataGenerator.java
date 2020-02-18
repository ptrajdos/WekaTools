package weka.tools.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.Randomizable;

public class RandomDataGenerator implements Serializable, DataGenerator, Randomizable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4367823102888277085L;
	
	private int numNumericAttributes=2;
	
	private int numNominalAttributes=2;
	
	private int numObjects=100;
	
	private int maxNumNominalValues=3;
	
	private String numericAttribNameProto="NumA";
	
	private String nominalAttribNameProto="NomA";
	
	private int seed=0;
	
	private Random rnd = new Random(this.seed);
	
	private boolean addClassAttrib=true;
	
	private int numClasses=2;
	
	private boolean allowUnary=false;

	



	@Override
	public Instances generateData() {
		ArrayList<Attribute> atts = new ArrayList<Attribute>(3);
		this.generateNumericAttribs(atts);
		this.generateNominalAttribs(atts);
		this.generateClass(atts);
		
		Instances dataset = null;
	      dataset = new Instances("dataset",atts,1);
	      if(this.addClassAttrib)
	    	  dataset.setClassIndex(dataset.numAttributes()-1);
		
	      this.fillWithInstances(dataset);
	      
		
		return dataset;
	}
	
	protected void generateNumericAttribs(List<Attribute> attrList) {
		for(int i=0;i<this.numNumericAttributes;i++)
			attrList.add(new Attribute(this.numericAttribNameProto+i));
		
	}
	
	protected void generateNominalAttribs(List<Attribute> attrList) {
		for(int a=0;a<this.numNominalAttributes;a++) {
			int numVals = this.rnd.nextInt(this.maxNumNominalValues)+1;
			if(numVals == 1 & this.allowUnary==false) {
				numVals++;
			}
			LinkedList<String> valList = new LinkedList<String>();
			for(int v =0;v<numVals;v++) {
				valList.add(""+v);
			}
			attrList.add(new Attribute(this.nominalAttribNameProto+a, valList));
			
		}
	}
	protected void generateClass(List<Attribute> attrList) {
		if(!this.addClassAttrib)
			return;
		LinkedList<String> valList = new LinkedList<String>();
		for(int v =0;v< this.numClasses;v++)
			valList.add(""+v);
		
		attrList.add(new Attribute("Class", valList));
		
	}
	
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
	



	/**
	 * @return the numNumericAttributes
	 */
	public int getNumNumericAttributes() {
		return this.numNumericAttributes;
	}



	/**
	 * @param numNumericAttributes the numNumericAttributes to set
	 */
	public void setNumNumericAttributes(int numNumericAttributes) {
		this.numNumericAttributes = numNumericAttributes;
	}



	/**
	 * @return the numNominalAttributes
	 */
	public int getNumNominalAttributes() {
		return this.numNominalAttributes;
	}



	/**
	 * @param numNominalAttributes the numNominalAttributes to set
	 */
	public void setNumNominalAttributes(int numNominalAttributes) {
		this.numNominalAttributes = numNominalAttributes;
	}



	/**
	 * @return the numObjects
	 */
	public int getNumObjects() {
		return this.numObjects;
	}



	/**
	 * @param numObjects the numObjects to set
	 */
	public void setNumObjects(int numObjects) {
		this.numObjects = numObjects;
	}



	/**
	 * @return the maxNumNominalValues
	 */
	public int getMaxNumNominalValues() {
		return this.maxNumNominalValues;
	}



	/**
	 * @param maxNumNominalValues the maxNumNominalValues to set
	 */
	public void setMaxNumNominalValues(int maxNumNominalValues) {
		this.maxNumNominalValues = maxNumNominalValues;
	}

	@Override
	public void setSeed(int seed) {
		this.seed=seed;
		this.rnd.setSeed(seed);
		
	}

	@Override
	public int getSeed() {
		return this.seed;
	}

	/**
	 * @return the addClassAttrib
	 */
	public boolean isAddClassAttrib() {
		return this.addClassAttrib;
	}

	/**
	 * @param addClassAttrib the addClassAttrib to set
	 */
	public void setAddClassAttrib(boolean addClassAttrib) {
		this.addClassAttrib = addClassAttrib;
	}

	/**
	 * @return the numClasses
	 */
	public int getNumClasses() {
		return this.numClasses;
	}

	/**
	 * @param numClasses the numClasses to set
	 */
	public void setNumClasses(int numClasses) {
		this.numClasses = numClasses;
	}

	/**
	 * @return the allowUnary
	 */
	public boolean isAllowUnary() {
		return this.allowUnary;
	}

	/**
	 * @param allowUnary the allowUnary to set
	 */
	public void setAllowUnary(boolean allowUnary) {
		this.allowUnary = allowUnary;
	}
	
	
	
	

}

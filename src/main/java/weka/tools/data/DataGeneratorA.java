/**
 * 
 */
package weka.tools.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Randomizable;

/**
 * An abstract class for data generators
 * @author pawel trajdos
 * @since 1.4.0
 * @version 1.4.0
 *
 */
public abstract class DataGeneratorA implements DataGenerator, Serializable, Randomizable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1465097905997963471L;
	
	protected int numNumericAttributes=2;
	
	protected int numNominalAttributes=2;
	
	protected int numStringAttributes=0;
	
	protected int numDateAttributes=0;
	
	protected int numObjects=100;
	
	protected int maxNumNominalValues=3;
	
	protected String numericAttribNameProto="NumA";
	
	protected String nominalAttribNameProto="NomA";
	
	protected String stringAttribNameProto="StringA";
	
	protected String dateAttribNameProto="DateA";
	
	protected String dateFormatString="yyyy-MM-dd'T'HH:mm:ss";
	
	protected SimpleDateFormat dateFormat = new SimpleDateFormat(this.dateFormatString);
	
	protected int seed=0;
	
	protected Random rnd = new Random(this.seed);
	
	protected boolean addClassAttrib=true;
	
	protected int numClasses=2;
	
	protected boolean allowUnary=false;
	
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
	
	protected void generateStringAttribs(List<Attribute> attrList) {
		for(int i=0;i<this.numStringAttributes;i++)
			attrList.add(new Attribute(this.stringAttribNameProto+i,true));
	}
	
	
	protected void generateDateAttribs(List<Attribute> attrList) {
		for(int i=0;i<this.numDateAttributes;i++)
			attrList.add(new Attribute(this.dateAttribNameProto+i,this.dateFormatString));
	}
	
	protected void generateClass(List<Attribute> attrList) {
		if(!this.addClassAttrib)
			return;
		LinkedList<String> valList = new LinkedList<String>();
		for(int v =0;v< this.numClasses;v++)
			valList.add(""+v);
		
		attrList.add(new Attribute("Class", valList));
		
	}
	
	protected Instances generateEmptyDataset() {
		ArrayList<Attribute> atts = new ArrayList<Attribute>(3);
		this.generateNumericAttribs(atts);
		this.generateNominalAttribs(atts);
		this.generateStringAttribs(atts);
		this.generateDateAttribs(atts);
		this.generateClass(atts);
		
		Instances dataset = null;
	      dataset = new Instances("dataset",atts,1);
	      if(this.addClassAttrib)
	    	  dataset.setClassIndex(dataset.numAttributes()-1);
	      
	      return dataset;
		
	}
	
	protected abstract void fillWithInstances(Instances dataset);
	
	@Override
	public Instances generateData() {
		
		Instances dataset = this.generateEmptyDataset();
	    this.fillWithInstances(dataset);
	      
		
		return dataset;
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

	/**
	 * @return the numStringAttributes
	 */
	public int getNumStringAttributes() {
		return this.numStringAttributes;
	}

	/**
	 * @param numStringAttributes the numStringAttributes to set
	 */
	public void setNumStringAttributes(int numStringAttributes) {
		this.numStringAttributes = numStringAttributes;
	}

	/**
	 * @return the numDateAttributes
	 */
	public int getNumDateAttributes() {
		return this.numDateAttributes;
	}

	/**
	 * @param numDateAttributes the numDateAttributes to set
	 */
	public void setNumDateAttributes(int numDateAttributes) {
		this.numDateAttributes = numDateAttributes;
	}

	/**
	 * @return the dateFormatString
	 */
	public String getDateFormatString() {
		return this.dateFormatString;
	}

	/**
	 * @param dateFormatString the dateFormatString to set
	 */
	public void setDateFormatString(String dateFormatString) {
		this.dateFormatString = dateFormatString;
	}
	
	
	


}

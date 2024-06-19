/**
 * 
 */
package weka.tools;

import java.util.Arrays;
import java.util.HashMap;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

/**
 * @author pawel trajdos
 * @since 0.0.1
 */
public class InstancesTools {

	/**
	 * Count instances belonging to classes
	 * @param dataset
	 * @return
	 */
	public static int[] getClassCounts(Instances dataset) {
		int numClass = dataset.numClasses();
		int numInstances = dataset.numInstances();
		int[] instCount =new int[numClass];
		for(int i=0;i<numInstances;i++) {
			instCount[(int) dataset.get(i).classValue()]++;
		}
		
		return instCount;
	}
	/**
	 * Checks the compatibility of the instance and the dataset
	 * @param dataset
	 * @param inst
	 * @return true if the instance and the dataset are compatible
	 * @throws Exception -- when there is some kind of incompatibility
	 * 
	 * @since 0.4.0
	 * @version 0.8.0
	 * 
	 */
	public static boolean checkCompatibility(Instances dataset, Instance inst)throws Exception{
		return checkCompatibility(dataset, inst,true);
	}
	
	public static boolean checkCompatibility(Instances dataset, Instance inst, boolean checkClassAttribute)throws Exception{
		if(dataset == inst.dataset())
			return true;
		int numAttribs = dataset.numAttributes();
		if(inst.numAttributes() != numAttribs)
			throw new Exception("Incompatible number of attributes");
		
		if(inst.classIndex() != dataset.classIndex())
			throw new Exception("The class index does not match");
		
		if(checkClassAttribute && inst.classIndex()>0) {
			if(dataset.numClasses() != inst.numClasses())
				throw new Exception("Number of classes do not match");
		}

		
		Attribute instAttr;
		Attribute setAttr;
		String msg;
		int classIdx = inst.classIndex();
		for(int a =0 ;a<numAttribs;a++){
			instAttr = inst.attribute(a);
			setAttr = dataset.attribute(a);
			if( !checkClassAttribute &&(a ==classIdx) )
				continue;
			
			msg  = instAttr.equalsMsg(setAttr);
			if(msg!=null) {
				throw new Exception(msg);
			}
			
		}
		
		return true;
	}
	/**
	 * Checks whether the instances are compatible.
	 * @param inst1
	 * @param inst2
	 * @return true if they are compatible
	 * @throws Exception if the instances are incompatible
	 * 
	 * @since 0.4.0
	 * @version 1.11.0
	 */
	public static boolean checkCompatibility(Instance inst1, Instance inst2 )throws Exception{
		return checkCompatibility(inst1, inst2, true);
	}
	
	/**
	 * Checks whether the instances are compatible.
	 * @param inst1
	 * @param inst2
	 * @param checkClassAttrib -- determines whether class attribute is checked
	 * @return
	 * @throws Exception
	 */
	
	public static boolean checkCompatibility(Instance inst1, Instance inst2, boolean checkClassAttrib )throws Exception{
		if(inst1.dataset() == inst2.dataset())
			return true;
		int numAttribs = inst1.numAttributes();
		if(inst2.numAttributes() != numAttribs)
			throw new Exception("Incompatible number of attributes");
		
		if(inst2.classIndex() != inst1.classIndex())
			throw new Exception("The class index does not match");
		
		if( checkClassAttrib && inst1.classIndex()>0 ) {
			if(inst1.numClasses() != inst2.numClasses())
				throw new Exception("The number of classes do not match");
		}
			
		Attribute inst2Attr;
		Attribute inst1Attr;
		String msg;
		int classIdx = inst1.classIndex();
		for(int a =0 ;a<numAttribs;a++){
			if( !checkClassAttrib &&(a ==classIdx) )
				continue;
			inst2Attr = inst2.attribute(a);
			inst1Attr = inst1.attribute(a);
			
			msg  = inst2Attr.equalsMsg(inst1Attr);
			if(msg!=null) {
				throw new Exception(msg);
			}
			
		}
		
		return true;
	}
	
	/**
	 * Check whether the instances are equall
	 * @param inst1 -- instance 1
	 * @param inst2 --  instance 2
	 * @param checkClass -- determines if the class attribute is checked
	 * @return -- boolean
	 * @throws Exception if something goes wrong
	 * 
	 * @since 0.4.0
	 * @version 0.5.0
	 */
	public static boolean checkEquall(Instance inst1, Instance inst2, boolean checkClass) throws Exception{
		if(!checkCompatibility(inst1, inst2))return false;
		Instances dataset = inst1.dataset();
		
		int numAttrs = dataset.numAttributes();
		
		double[] rep1 = inst1.toDoubleArray();
		double[] rep2 = inst2.toDoubleArray();
		int classIdx = dataset.classIndex();
		for(int a=0; a<numAttrs;a++) {
			if(a == classIdx && !checkClass )continue;
			if(! Utils.eq(rep1[a], rep2[a]))
				return false;
		}
		
		return true;
	}
	/**
	 * Creates a copy of the instance
	 * @author pawel trajdos
	 * 
	 * @param inst
	 * @return
	 * 
	 * @since 0.4.0
	 * @version 0.4.0
	 */
	public static Instance copyInstance(Instance inst) {
		Instance result = inst.copy(inst.toDoubleArray());
		return result;
	}
	
	/**
	 * Counts the number of unique instances
	 * @param data -- Instances to count
	 * @return number of unique instances
	 * 
	 */
	public static int countUniqieInstances(Instances data) {
		HashMap<Integer, Integer> cntMap = new HashMap<>();
		for (Instance instance : data) {
			int hash = Arrays.hashCode(instance.toDoubleArray());
			if(!cntMap.containsKey(hash))
				cntMap.put(hash, 1);
		}
		
		return cntMap.size();
	}
	

}

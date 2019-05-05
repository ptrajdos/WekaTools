/**
 * 
 */
package weka.tools.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.Standardize;
import weka.filters.unsupervised.instance.Resample;
import weka.filters.unsupervised.instance.SparseToNonSparse;

/**
 * @author pawel
 *
 */
public class InstancesOperator {
	/**
	 * generates bootstrap sample from base set
	 * @param baseSet - input set
	 * @param seed - random generator seed
	 * @param percentage - percentage of instances to return
	 * @return
	 * @throws Exception 
	 */
	public static Instances generateBaggingSample(Instances baseSet, int seed,double percentage) throws Exception{
		Resample resample = new Resample();
		resample.setNoReplacement(false);
		double finalPerc = 100.0*percentage;
		resample.setSampleSizePercent(finalPerc);
		resample.setRandomSeed(seed);
		
		Instances resampledI=null;
		try {
			resample.setInputFormat(baseSet);
			resampledI = Filter.useFilter(baseSet, resample);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return resampledI;

	}
	/**
	 * generates bootstrap sample from base set
	 * @param baseSet
	 * @param seed - random generator seed
	 * @return resampled set
	 * @throws Exception 
	 */
public static Instances generateBaggingSample(Instances baseSet, int seed) throws Exception{
		return generateBaggingSample(baseSet, seed, 1.0);
}
/**
 * generates bootstrap sample from base set
 * @param baseSet
 * @return resapmled set
 * @throws Exception 
 */
public static Instances generateBaggingSample(Instances baseSet) throws Exception{
	return InstancesOperator.generateBaggingSample(baseSet, 1);
}
/**
 * Splits input set into two sets according to splitCoeff
 * First set crdinality -> numOfInstances*splitCoeff
 * Second set crdinality -> numOfInstances*(1-splitCoeff)
 * @param inputSet
 * @param splitCoeff
 * @return Instances table - two splitted set
 */
public static Instances[] splitSet(Instances inputSet,double splitCoeff,int seed){
	Instances splittedInstances[] = new Instances[2];
	int instancesNumber = inputSet.numInstances();
	if(instancesNumber==0){
		splittedInstances[0] = new Instances(inputSet);
		splittedInstances[1] = new Instances(inputSet);
		return splittedInstances;
	}
	int leftIdx,rigthIdx;
	leftIdx = (int) Math.floor(instancesNumber*splitCoeff);
	rigthIdx=leftIdx+1;
	Instances shuffledSet  = new Instances(inputSet);
	shuffledSet.randomize(new Random(seed));
	 splittedInstances[0] =new Instances(shuffledSet, 0, leftIdx+1);
	 splittedInstances[1] =new Instances(shuffledSet, rigthIdx, instancesNumber-rigthIdx);
	return splittedInstances;
	
}
/**
 * Splits input set into two sets according to splitCoeff
 * First set crdinality -> numOfInstances*splitCoeff
 * Second set crdinality -> numOfInstances*(1-splitCoeff)
 * Stratified splitt 
 * @param inputSet
 * @param splitCoeff
 * @param seed
 * @return
 */
public static Instances[] stratifiedSplitSet(Instances inputSet, double splitCoeff, int seed){
	Instances[] splittedInstances = new Instances[2];
	int numInstances = inputSet.numInstances();
	int numClasses = inputSet.numClasses();
	int[] classCounts = new int[numClasses];
	for(int i=0;i<numInstances;i++){
		classCounts[(int)inputSet.get(i).classValue()]++;
	}
	int[] classCountsA = new int[numClasses];
	int[] classCountsB = new int[numClasses];
	for(int i=0;i<numClasses;i++){
		classCountsA[i] = (int) Math.round(classCounts[i]*splitCoeff);
		classCountsB[i] = (int) Math.round(classCounts[i]*(1-splitCoeff));
	}
	
	int setACard = (int) Math.floor(numInstances*splitCoeff);
	int setBCard = numInstances - setACard;
	
	splittedInstances[0]= new Instances(inputSet, setACard);
	splittedInstances[1]= new Instances(inputSet, setBCard);
	Instance tmpInstance = null;
	int selClass =0;
	for(int i =0;i<numInstances;i++){
		tmpInstance = inputSet.get(i);
		selClass = (int) tmpInstance.classValue();
		if(classCountsA[selClass]>0){
			classCountsA[selClass]--;
			splittedInstances[0].add((Instance) tmpInstance.copy());
			continue;
		}
		if(classCountsB[selClass]>0){
			classCountsB[selClass]--;
			splittedInstances[1].add((Instance) tmpInstance.copy());
			continue;
		}
		
	}
	
	return splittedInstances;
	
}

/**
 * Splits input set into two sets according to splitCoeff
 * First set crdinality -> numOfInstances*splitCoeff
 * Second set crdinality -> numOfInstances*(1-splitCoeff) 
 * @param inputSet
 * @param splitCoeff 
 * @return Instances table - two splitted set
 */
public static Instances[] splitSet(Instances inputSet,double splitCoeff){
	return InstancesOperator.splitSet(inputSet, splitCoeff, 1);
}
/**
 * Splits input set into two sets according to firstSetNum
 * First set -> first firstSetNum instances
 * Second set -> remaining instances
 * Does not shuffle
 * 
 * @param inputSet 
 * @param firstSetNum
 * @return Instances[] - two sets
 */
public static Instances[] splItSet(Instances inputSet,int firstSetNum){
	Instances splittedInstances[] = new Instances[2];
	int instancesNumber = inputSet.numInstances();
	int leftIdx,rigthIdx;
	leftIdx = firstSetNum;
	rigthIdx=leftIdx+1;
	Instances shuffledSet  = inputSet;
	
	 splittedInstances[0] =new Instances(shuffledSet, 0, leftIdx+1);
	 splittedInstances[1] =new Instances(shuffledSet, rigthIdx, instancesNumber-rigthIdx);
	return splittedInstances;
}


/**
 * Normalizes data to interval [0,1] for each attribute
 * @param inputSet
 * @return
 * @throws Exception
 */

public static Instances normalizeData(Instances inputSet) throws Exception{
	Normalize normalizer  = new Normalize();
	normalizer.setInputFormat(inputSet);
	return Filter.useFilter(inputSet, normalizer);
}

/**
 * Standarize data for 0 mean and unit variance
 * @param inputSet
 * @return
 * @throws Exception
 */
public static Instances standarizeData(Instances inputSet)throws Exception{
	Standardize standarize = new Standardize();
	standarize.setInputFormat(inputSet);
	return Filter.useFilter(inputSet, standarize);
}
/**
 * Generates dense dataset from sparse dataset.
 * @param inputSet
 * @return
 * @throws Exception
 */
public static Instances desparse(Instances inputSet)throws Exception{
	SparseToNonSparse sprFilter = new SparseToNonSparse();
	sprFilter.setInputFormat(inputSet);
	return Filter.useFilter(inputSet, sprFilter);
}


/**
 * Counts the number of objects per class
 * @param inputSet -- input data
 * @return -- array of class specific instance counts
 * @throws Exception
 */
public static int[] objPerClass(Instances inputSet)throws Exception{
	int numClass = inputSet.numClasses();
	int numInstances = inputSet.numInstances();
	int[] classCnts = new int[numClass];
	
	Instance tmpInstance=null;
	int classIdx;
	for(int i=0;i<numInstances;i++){
		tmpInstance = inputSet.get(i);
		classIdx = (int)tmpInstance.classValue();
		classCnts[classIdx]++;
	}
	
	return classCnts;
}
/**
 * Calculates the frequency of class-specific instances.
 * The resulting array sums up to one.  
 * @author pawel trajdos
 * @param inputSet
 * @return
 * @throws Exception
 */
public static double[] classFreq(Instances inputSet)throws Exception{
	int numClass = inputSet.numClasses(); 
	int numInstances = inputSet.numInstances();
	double[] classFreqs = new double[numClass];
	Instance tmpInstance=null;
	int classIdx;
	for(int i=0;i<numInstances;i++){
		tmpInstance = inputSet.get(i);
		classIdx = (int)tmpInstance.classValue();
		classFreqs[classIdx]+=1.0;
	}
	for(int i=0;i<classFreqs.length;i++){
		classFreqs[i]/=numInstances;
	}
	return classFreqs;
}

/**
 * Split a dataset according to class indices. 
 * Accepts <b> only </b> nominal and string class attributes. 
 * @param input -- input dataset
 * @return -- an array of splitted instances.
 * @throws Exception when a dataset with a wrong attribute class has been passed.
 * 
 * @since 0.4.0
 * @version 0.4.0
 */
public static Instances[] classSpecSplit(Instances input)throws Exception {
	Attribute classAttr = input.classAttribute();
	if(!(classAttr.isNominal() || classAttr.isString()))throw new Exception("Incompatible class atribute");
	
	int numClasses = input.numClasses();
	
	Instances[] results = new Instances[numClasses];
	for(int c=0;c<numClasses;c++) 
		results[c] = new Instances(input, 0);
	
	int instNum = input.numInstances();
	int classVal=0;
	Instance tmpInstance;
	for(int i=0;i<instNum;i++) {
		tmpInstance = input.get(i);
		classVal = (int) tmpInstance.classValue();
		results[classVal].add(tmpInstance);
	}
	return results;
}



public static void main(String[] args) {
	System.out.println("Instances Operator Test: ");
	
	String file= new String("./data/iris.arff");		
	try {
		BufferedReader reader = new BufferedReader(
                new FileReader(file));
		Instances dat = new Instances(reader);
		dat.setClass(dat.attribute(dat.numAttributes()-1));
		Instances[] sInst = null;
		sInst = InstancesOperator.splitSet(dat, 0.7);
		
		System.out.println("S0: "+sInst[0].numInstances());
		System.out.println("S1: "+sInst[1].numInstances());
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	

}

}


/**
 * 
 */
package weka.tools.tests;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * The class perform test for dataset with no instances
 * @author pawel trajdos
 * @since 1.6.0
 * @version 1.6.0
 *
 */
public class NoInstancesChecker {

	
	
	public static boolean performCheck(Instances dataset, Classifier classifier,int numInstances, int seed)throws Exception {
		
		int numDataInstances = dataset.numInstances();
		if(numDataInstances==0)
			throw new IllegalArgumentException("Dataset must contain at least one instance");
		
		int realInstancesNum = numDataInstances<numInstances? numDataInstances:numInstances;
		realInstancesNum = realInstancesNum<0? 0:realInstancesNum;
		
		Instances newData = new Instances(dataset, 0);
		Instance testInstance = dataset.get(0);
		
		if(realInstancesNum>0) {
			Random rnd = new Random(seed);
			for(int i=0;i<realInstancesNum;i++) {
				int rndIdx = rnd.nextInt(numDataInstances);
				newData.add(dataset.get(rndIdx));
			}
		}
		
		classifier.buildClassifier(newData);
		
		double[] distribution = classifier.distributionForInstance(testInstance);
		
		boolean checkDist = DistributionChecker.checkDistribution(distribution);
		if(!checkDist)
			return false;
		
		
		
		
		return true;
	}
	
	
	public static boolean performCheck(Instances dataset, Classifier classifier) throws Exception {
		return NoInstancesChecker.performCheck(dataset, classifier,0,0);
	}
	
	public static boolean performCheck(Instances dataset, Classifier classifier,int[] numInstancesBatch, int seed)throws Exception {
		
		for(int i=0;i< numInstancesBatch.length;i++) {
			boolean result = NoInstancesChecker.performCheck(dataset, classifier, numInstancesBatch[i], seed);
			if(!result)
				return false;
		}
		
		return true;
	}

}

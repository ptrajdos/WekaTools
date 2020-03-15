/**
 * 
 */
package weka.tools.tests;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.output.prediction.XML;
import weka.core.Instances;
import weka.tools.data.RandomDataGenerator;
import weka.tools.data.WellSeparatedSquares;

/**
 * @author pawel trajdos
 * @since 1.4.0
 * @version 1.4.0
 *
 */
public class RandomDataChecker {


	public static Instances generateRandomData(int numClasses, int numObjects, int numAttributes) {
		RandomDataGenerator gen  = new RandomDataGenerator();
		gen.setNumObjects(numObjects);
		gen.setNumClasses(numClasses);
		gen.setNumNominalAttributes(numAttributes);
		gen.setNumNumericAttributes(numAttributes);
		Instances data = gen.generateData();
		return data;
	}
	
	public static Instances generateWellSeparated(int numClasses, int numObjects,int numNumAttrs) {
		WellSeparatedSquares gen = new WellSeparatedSquares();
		gen.setNumClasses(numClasses);
		gen.setNumObjects(numObjects);
		gen.setNumNominalAttributes(0);
		gen.setNumNumericAttributes(numNumAttrs);
		Instances data = gen.generateData();
		
		return data;
	}
	
	
	
	
	public static Evaluation evaluateClassifier(Classifier classifier, Instances data,int numFolds, int seed) throws Exception {
		Evaluation eval = new Evaluation(data);
		
		XML xml = new XML();
		xml.setBuffer(new StringBuffer());
		xml.setHeader(data);
		
		Random rnd = new Random(seed);
		
		eval.crossValidateModel(classifier, data, numFolds, rnd);
		
		return eval;
	}
	
	public static boolean checkAgainstRandomData(Classifier classifier, double negEps, double posEps) {
		
		int numFolds =10;
		int seed=0;
		int[] numClasses = {2,3,4};
		int numObjects =1000;
		int[] numAttribs= {1,2};

		for(int a=0;a<numAttribs.length;a++)
			for(int i=0;i<numClasses.length;i++) {
				Instances data = generateRandomData(numClasses[i], numObjects,numAttribs[a]);
				try {
					Evaluation eval = evaluateClassifier(classifier, data, numFolds, seed);
					double kappa = eval.kappa();
					if(kappa > posEps )
						return false;
					if(kappa < negEps)
						return false;
				} catch (Exception e) {
					return false;
				}
			}
		
		
		
		return true;
	}
	
	public static boolean checkAgainstWellSeparatedData(Classifier classifier, double minVal) {
		
		int numFolds =10;
		int seed=0;
		int[] numClasses = {2,3,4};
		int[] numAttribs = {1,2,3,10};
		int numObjects =1000;

		for(int a=0;a<numAttribs.length;a++)
			for(int i=0;i<numClasses.length;i++) {
				Instances data = generateWellSeparated(numClasses[i], numObjects,numAttribs[a]);
				try {
					Evaluation eval = evaluateClassifier(classifier, data, numFolds, seed);
					double kappa = eval.kappa();
					
					if(kappa < minVal)
						return false;
				} catch (Exception e) {
					return false;
				}
			}
		
		return true;
	}
	
	

	
	

}

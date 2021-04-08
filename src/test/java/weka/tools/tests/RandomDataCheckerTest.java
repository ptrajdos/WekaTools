package weka.tools.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;

public class RandomDataCheckerTest {

	@Test
	public void testRandomData() {
		Classifier[] classifiers = this.genClassifiers();
		for(int c = 0; c<classifiers.length;c++) {
			assertTrue("Random data check", RandomDataChecker.checkAgainstRandomData(classifiers[c],-0.1,0.1));
		}
	}
	
	@Test
	public void testWellSeparatedData() {
		Classifier[] classifiers = this.genClassifiers();
		for(int c = 0; c<classifiers.length;c++) {
			assertTrue("Well separated data check", RandomDataChecker.checkAgainstWellSeparatedData(classifiers[c], 0.9));
		}
	}
	

	
	public Classifier[] genClassifiers() {
		
		return new Classifier[]{new Logistic(),new J48(), new NaiveBayes(), new IBk()};
		
	}

}

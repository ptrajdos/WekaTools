package weka.classifiers.evaluation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import weka.core.Instances;
import weka.core.Utils;
import weka.tools.data.RandomDataGenerator;

public class ThresholdCurvePTTest {
	
	
	public void testPerfectPredictionsI(int nClasses, int numObjs) {
		ArrayList<Prediction> predictions = new ArrayList<Prediction>();

		Random rnd = new Random();
		for (int i=0;i<numObjs;i++) {
			double classVal = rnd.nextInt(nClasses);
			double[] prediction = new double[nClasses];
			prediction[(int) classVal]=1.0;
			predictions.add(new NominalPrediction(classVal, prediction));
		}
	
		
		for (int i=0;i<nClasses;i++) {
			ThresholdCurve tc = new ThresholdCurve();
	        Instances result = tc.getCurve(predictions, i);
	        double rarea =  ThresholdCurvePT.getROCArea(result);
	        this.checkAUC(rarea);
	        assertTrue("Perfect Predictions", Utils.eq(rarea, 1.0));
		}

	}

	@Test
	public void testPerfectPredictions() {
		int[] nCla= {2,3,4};
		int[] nInst= {100,1000};
		for(int i=0;i<nCla.length;i++)
			for(int j=0;j<nInst.length;j++)
				testPerfectPredictionsI(nCla[i], nInst[j]);

	}
	
	public void testWrongPredictionsI(int nClasses, int numObjs) {
		ArrayList<Prediction> predictions = new ArrayList<Prediction>();
		Random rnd = new Random();
		for (int i=0;i<numObjs;i++) {
			double classVal = rnd.nextInt(nClasses);
			double[] prediction = new double[nClasses];
			prediction[((int) classVal + 1)%nClasses]=1.0;
			predictions.add(new NominalPrediction(classVal, prediction));
		}
	
		
		for (int i=0;i<nClasses;i++) {
			ThresholdCurve tc = new ThresholdCurve();
	        Instances result = tc.getCurve(predictions, i);
	        double rarea =  ThresholdCurvePT.getROCArea(result);
	        this.checkAUC(rarea);
	        assertTrue("Perfect Predictions", Utils.eq(rarea, 0.0));
		}

	}
	
	@Test
	public void testWrongPredictions() {
		int[] nCla= {2};
		int[] nInst= {100,1000};
		for(int i=0;i<nCla.length;i++)
			for(int j=0;j<nInst.length;j++)
				testWrongPredictionsI(nCla[i], nInst[j]);

	}
	
	@Test
	public void testRandomPredictions() {
		ArrayList<Prediction> predictions = new ArrayList<Prediction>();
		
		int nClasses = 2;
		int numObjs=100;
		Random rnd = new Random();
		for (int i=0;i<numObjs;i++) {
			double classVal = rnd.nextInt(nClasses);
			double[] prediction = new double[nClasses];
			prediction[rnd.nextInt(nClasses)%nClasses]=1.0;
			predictions.add(new NominalPrediction(classVal, prediction));
		}
	
		
		for (int i=0;i<nClasses;i++) {
			ThresholdCurve tc = new ThresholdCurve();
	        Instances result = tc.getCurve(predictions, i);
	        double rarea =  ThresholdCurvePT.getROCArea(result);
	        this.checkAUC(rarea);
		}

	}
	
	@Test
	public void testOneClass() {
		ArrayList<Prediction> predictions = new ArrayList<Prediction>();
		
		int nClasses = 2;
		int numObjs=100;
		Random rnd = new Random();
		for (int i=0;i<numObjs;i++) {
			double classVal = 1;
			double[] prediction = new double[nClasses];
			prediction[((int) classVal + 1)%nClasses]=1.0;
			predictions.add(new NominalPrediction(classVal, prediction));
		}
	
		
		for (int i=0;i<nClasses;i++) {
			ThresholdCurve tc = new ThresholdCurve();
	        Instances result = tc.getCurve(predictions, i);
	        double rarea =  ThresholdCurvePT.getROCArea(result);
	        this.checkAUC(rarea);
	        assertTrue("Perfect Predictions", Utils.eq(rarea, 0.0));
		}

	}
	
	@Test
	public void testOneClassPerfect() {
		ArrayList<Prediction> predictions = new ArrayList<Prediction>();
		
		int nClasses = 2;
		int numObjs=100;
		Random rnd = new Random();
		for (int i=0;i<numObjs;i++) {
			double classVal = 1;
			double[] prediction = new double[nClasses];
			prediction[1]=1.0;
			predictions.add(new NominalPrediction(classVal, prediction));
		}
	
		
		for (int i=0;i<nClasses;i++) {
			ThresholdCurve tc = new ThresholdCurve();
	        Instances result = tc.getCurve(predictions, i);
	        double rarea =  ThresholdCurvePT.getROCArea(result);
	        this.checkAUC(rarea);
	        assertTrue("Perfect Predictions", Utils.eq(rarea, 0.0));
		}

	}
	
	
	public Instances generateData() {
		RandomDataGenerator gen = new RandomDataGenerator();
		Instances instances = gen.generateData();
		return instances;
	}
	
	public void checkAUC(double value) {
		assertTrue("Interval [0;1] checking", value <= 1.0 & value >=0);
	}
	
	@Test
	public void testCreate() {
		ThresholdCurvePT ptc = new ThresholdCurvePT();
		assertTrue("Not null", ptc!=null);
	}
	
	@Test
	public void testWrongInstances() {
		RandomDataGenerator dataGen = new RandomDataGenerator();
		Instances instances = dataGen.generateData();
		double val = ThresholdCurvePT.getROCArea(instances);
		assertTrue("Nan value", Double.isNaN(val));
	}
	
	@Test
	public void testEmptyPredictions() {
		ArrayList<Prediction> predictions = new ArrayList<Prediction>();
		
		int nClasses = 2;
		int numObjs=100;
		Random rnd = new Random();
		for (int i=0;i<numObjs;i++) {
			double classVal = rnd.nextInt(nClasses);
			double[] prediction = new double[nClasses];
			prediction[rnd.nextInt(nClasses)%nClasses]=1.0;
			predictions.add(new NominalPrediction(classVal, prediction));
		}
	
		
		for (int i=0;i<nClasses;i++) {
			ThresholdCurve tc = new ThresholdCurve();
	        Instances result = tc.getCurve(predictions, i);
	        result = new Instances(result, 0);
	        double rarea =  ThresholdCurvePT.getROCArea(result);
	        assertTrue("Nan value", Double.isNaN(rarea));
		}

	}

}

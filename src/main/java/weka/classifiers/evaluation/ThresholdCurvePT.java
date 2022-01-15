/**
 * 
 */
package weka.classifiers.evaluation;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

/**
 * @author pawel trajdos
 * @since 1.8.0
 * @version 1.13.0
 *
 */
public class ThresholdCurvePT extends ThresholdCurve {

	/**
	   * Calculates the area under the ROC curve as the Wilcoxon-Mann-Whitney
	   * statistic.
	   * 
	   * @param tcurve a previously extracted threshold curve Instances.
	   * @return the ROC area, or Double.NaN if you don't pass in a ThresholdCurve
	   *         generated Instances.
	   */
	  public static double getROCArea(Instances tcurve) {

	    final int n = tcurve.numInstances();
	    if (!RELATION_NAME.equals(tcurve.relationName()) || (n == 0)) {
	      return Double.NaN;
	    }
	    final int tpInd = tcurve.attribute(TRUE_POS_NAME).index();
	    final int fpInd = tcurve.attribute(FALSE_POS_NAME).index();
	    final double[] tpVals = tcurve.attributeToDoubleArray(tpInd);
	    final double[] fpVals = tcurve.attributeToDoubleArray(fpInd);

	    double area = 0.0, cumNeg = 0.0;
	    final double totalPos = tpVals[0];
	    final double totalNeg = fpVals[0];
	    for (int i = 0; i < n; i++) {
	      double cip, cin;
	      if (i < n - 1) {
	        cip = tpVals[i] - tpVals[i + 1];
	        cin = fpVals[i] - fpVals[i + 1];
	      } else {
	        cip = tpVals[n - 1];
	        cin = fpVals[n - 1];
	      }
	      area += cip * (cumNeg + (0.5 * cin));
	      cumNeg += cin;
	    }
	    
	    if(Utils.eq(area, 0.0))
	    	return 0.0;
	    
	    area /= (totalNeg * totalPos);

	    return area;
	  }
	  
	  /**
	   * Calculates the area under the precision-recall curve (AUPRC).
	   * 
	   * @param tcurve a previously extracted threshold curve Instances.
	   * @return the PRC area, or Double.NaN if you don't pass in a ThresholdCurve
	   *         generated Instances.
	   */
	  public static double getPRCArea(Instances tcurve) {
	    final int n = tcurve.numInstances();
	    if (!RELATION_NAME.equals(tcurve.relationName()) || (n == 0)) {
	      return Double.NaN;
	    }

	    final int pInd = tcurve.attribute(PRECISION_NAME).index();
	    final int rInd = tcurve.attribute(RECALL_NAME).index();
	    final double[] pVals = tcurve.attributeToDoubleArray(pInd);
	    final double[] rVals = tcurve.attributeToDoubleArray(rInd);

	    double area = 0;
	    double xlast = rVals[n - 1];

	    // start from the first real p/r pair (not the artificial zero point)
	    for (int i = n - 2; i >= 0; i--) {
	      double recallDelta = rVals[i] - xlast;
	      area += (pVals[i] * recallDelta);

	      xlast = rVals[i];
	    }

	    
	    return area;
	  }
	  
	  /**
	   * Calculates the performance stats for the desired class and return results
	   * as a set of Instances.
	   * 
	   * @param predictions the predictions to base the curve on
	   * @param classIndex index of the class of interest.
	   * @return datapoints as a set of instances.
	   */
	  public Instances getCurve(ArrayList<Prediction> predictions, int classIndex) {

	    if ((predictions.size() == 0)
	      || (((NominalPrediction) predictions.get(0)).distribution().length <= classIndex)) {
	      return null;
	    }

	    double totPos = 0, totNeg = 0;
	    double[] probs = getProbabilities(predictions, classIndex);

	    // Get distribution of positive/negatives
	    for (int i = 0; i < probs.length; i++) {
	      NominalPrediction pred = (NominalPrediction) predictions.get(i);
	      if (pred.actual() == Prediction.MISSING_VALUE) {
	        System.err.println(getClass().getName()
	          + " Skipping prediction with missing class value");
	        continue;
	      }
	      if (pred.weight() < 0) {
	        System.err.println(getClass().getName()
	          + " Skipping prediction with negative weight");
	        continue;
	      }
	      if (pred.actual() == classIndex) {
	        totPos += pred.weight();
	      } else {
	        totNeg += pred.weight();
	      }
	    }

	    Instances insts = makeHeader();
	    int[] sorted = Utils.sort(probs);
	    TwoClassStats tc = new TwoClassStatsPT(totPos, totNeg, 0, 0);
	    double threshold = 0;
	    double cumulativePos = 0;
	    double cumulativeNeg = 0;

	    for (int i = 0; i < sorted.length; i++) {

	      if ((i == 0) || (probs[sorted[i]] > threshold)) {
	        tc.setTruePositive(tc.getTruePositive() - cumulativePos);
	        tc.setFalseNegative(tc.getFalseNegative() + cumulativePos);
	        tc.setFalsePositive(tc.getFalsePositive() - cumulativeNeg);
	        tc.setTrueNegative(tc.getTrueNegative() + cumulativeNeg);
	        threshold = probs[sorted[i]];
	        insts.add(makeInstance(tc, threshold));
	        cumulativePos = 0;
	        cumulativeNeg = 0;
	        if (i == sorted.length - 1) {
	          break;
	        }
	      }

	      NominalPrediction pred = (NominalPrediction) predictions.get(sorted[i]);

	      if (pred.actual() == Prediction.MISSING_VALUE) {
	        System.err.println(getClass().getName()
	          + " Skipping prediction with missing class value");
	        continue;
	      }
	      if (pred.weight() < 0) {
	        System.err.println(getClass().getName()
	          + " Skipping prediction with negative weight");
	        continue;
	      }
	      if (pred.actual() == classIndex) {
	        cumulativePos += pred.weight();
	      } else {
	        cumulativeNeg += pred.weight();
	      }

	      /*
	       * System.out.println(tc + " " + probs[sorted[i]] + " " + (pred.actual()
	       * == classIndex));
	       */
	      /*
	       * if ((i != (sorted.length - 1)) && ((i == 0) || (probs[sorted[i]] !=
	       * probs[sorted[i - 1]]))) { insts.add(makeInstance(tc,
	       * probs[sorted[i]])); }
	       */
	    }

	    // make sure a zero point gets into the curve
	    if (tc.getFalseNegative() != totPos || tc.getTrueNegative() != totNeg) {
	      tc = new TwoClassStatsPT(0, 0, totNeg, totPos);
	      threshold = probs[sorted[sorted.length - 1]] + 10e-6;
	      insts.add(makeInstance(tc, threshold));
	    }

	    return insts;
	  }
	  
	  
	  /**
	   * 
	   * @param predictions the predictions to use
	   * @param classIndex the class index
	   * @return the probabilities
	   */
	  private double[] getProbabilities(ArrayList<Prediction> predictions,
	    int classIndex) {

	    // sort by predicted probability of the desired class.
	    double[] probs = new double[predictions.size()];
	    for (int i = 0; i < probs.length; i++) {
	      NominalPrediction pred = (NominalPrediction) predictions.get(i);
	      probs[i] = pred.distribution()[classIndex];
	    }
	    return probs;
	  }

	  /**
	   * generates the header
	   * 
	   * @return the header
	   */
	  private Instances makeHeader() {

	    ArrayList<Attribute> fv = new ArrayList<Attribute>();
	    fv.add(new Attribute(TRUE_POS_NAME));
	    fv.add(new Attribute(FALSE_NEG_NAME));
	    fv.add(new Attribute(FALSE_POS_NAME));
	    fv.add(new Attribute(TRUE_NEG_NAME));
	    fv.add(new Attribute(FP_RATE_NAME));
	    fv.add(new Attribute(TP_RATE_NAME));
	    fv.add(new Attribute(PRECISION_NAME));
	    fv.add(new Attribute(RECALL_NAME));
	    fv.add(new Attribute(FALLOUT_NAME));
	    fv.add(new Attribute(FMEASURE_NAME));
	    fv.add(new Attribute(SAMPLE_SIZE_NAME));
	    fv.add(new Attribute(LIFT_NAME));
	    fv.add(new Attribute(THRESHOLD_NAME));
	    return new Instances(RELATION_NAME, fv, 100);
	  }

	  /**
	   * generates an instance out of the given data
	   * 
	   * @param tc the statistics
	   * @param prob the probability
	   * @return the generated instance
	   */
	  private Instance makeInstance(TwoClassStats tc, double prob) {

	    int count = 0;
	    double[] vals = new double[13];
	    vals[count++] = tc.getTruePositive();
	    vals[count++] = tc.getFalseNegative();
	    vals[count++] = tc.getFalsePositive();
	    vals[count++] = tc.getTrueNegative();
	    vals[count++] = tc.getFalsePositiveRate();
	    vals[count++] = tc.getTruePositiveRate();
	    vals[count++] = tc.getPrecision();
	    vals[count++] = tc.getRecall();
	    vals[count++] = tc.getFallout();
	    vals[count++] = tc.getFMeasure();
	    double ss = (tc.getTruePositive() + tc.getFalsePositive())
	      / (tc.getTruePositive() + tc.getFalsePositive() + tc.getTrueNegative() + tc
	        .getFalseNegative());
	    vals[count++] = ss;
	    double expectedByChance = (ss * (tc.getTruePositive() + tc
	      .getFalseNegative()));
	    if (expectedByChance < 1) {
	      vals[count++] = Utils.missingValue();
	    } else {
	      vals[count++] = tc.getTruePositive() / expectedByChance;

	    }
	    vals[count++] = prob;
	    return new DenseInstance(1.0, vals);
	  }


}

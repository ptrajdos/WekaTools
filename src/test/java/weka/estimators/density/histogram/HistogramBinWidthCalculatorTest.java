package weka.estimators.density.histogram;

import java.util.Arrays;
import java.util.Random;

import junit.framework.TestCase;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.tools.WeightedValuesHolder;
import weka.tools.tests.OptionHandlerChecker;
import weka.tools.tests.SerializationChecker;
import weka.tools.tests.WekaGOEChecker;

/**
 * 
 * @author pawel trajdos
 * @since 1.12.0
 * @version 1.12.0
 *
 */

public abstract class HistogramBinWidthCalculatorTest extends TestCase {

	public abstract HistogramBinWidthCalculator getBinWidthCalc();
	
	public void testSerialization() {
		HistogramBinWidthCalculator binWidthCalc = this.getBinWidthCalc();
		assertTrue("Serialization Check", SerializationChecker.checkSerializationCopy(binWidthCalc));
	}
	
	public void testOptionsIfPresent() {
		HistogramBinWidthCalculator calc = this.getBinWidthCalc(); 
		if(calc instanceof OptionHandler) {
			OptionHandler opth = (OptionHandler) calc;
			OptionHandlerChecker.checkOptions(opth);
			
			WekaGOEChecker checker = new WekaGOEChecker();
			checker.setObject(calc);
			
			assertTrue("Check Tip Texts", checker.checkToolTipsCall());
		}
	}

	public void testBinWidth() {
		HistogramBinWidthCalculator binWidthCalc = this.getBinWidthCalc();
		
		int numVals =100;
		double[] rndVals = this.getRandomUniformData(numVals);
		double[] weights = this.getUniformWeights(numVals);
		
		WeightedValuesHolder holder = new WeightedValuesHolder();
		holder.addValues(rndVals, weights);
		
		double minVal = rndVals[ Utils.minIndex(rndVals)];
		double maxVal = rndVals[ Utils.maxIndex(rndVals)];
		double range = maxVal - minVal;
		
		double binWidth = binWidthCalc.getWidth(holder);
		
		assertTrue("Non negative", binWidth>0);
		assertTrue("Less or equall range", binWidth <= range);
		
		
	}
	
	public double[] getRandomUniformData(int nVals) {
		double[] vals = new double[nVals];
		
		Random rnd = new Random(0);
		for(int i=0;i<vals.length;i++)
			vals[i] = rnd.nextDouble();
		
		return vals;
	}
	
	public double[] getUniformWeights(int nVals) {
		double[] vals = new double[nVals];
		Arrays.fill(vals, 1.0);
		Utils.normalize(vals);
		
		return vals;
	}

}

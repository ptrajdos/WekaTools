/**
 * 
 */
package weka.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author pawel trajdos
 * @since 0.9.0
 * @version 1.12.0
 *
 */
public class WeightedValuesHolderTest {

	@Test
	public void test() {
		
		double[] values= {1,2,3,4,5};
		double[] weights= {1,1,1,1,1};
		
		WeightedValuesHolder valH  = new WeightedValuesHolder();
		
		valH.addValues(values, weights);
		assertTrue("Size Check", valH.getNumVals() == values.length);
		
		double[] retVals = valH.getValues();
		double[] retWeights = valH.getWeights();
		
		for(int i =0 ;i< valH.getNumVals();i++) {
			assertEquals("ValuesCheck", valH.getValue(i), values[i],1E-6);
			assertEquals("Values Array Check", retVals[i], values[i],1E-6);
			assertEquals("WeightsCheck", valH.getWeight(i), weights[i]/weights.length,1E-6);
			assertEquals("Weights Array Check", retWeights[i], weights[i]/weights.length,1E-6);
			
		}
		
	
		
	
		
		
	}

}

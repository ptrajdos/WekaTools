/**
 * 
 */
package weka.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
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
		
		for(int i =0 ;i< valH.getNumVals();i++) {
			assertEquals("ValuesCheck", valH.getValue(i), values[i],1E-6);
			assertEquals("WeightsCheck", valH.getWeight(i), weights[i]/weights.length,1E-6);
		}
	}

}

package weka.estimators.density;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import weka.core.Utils;

public class WeightedKernelEstimatorTest extends DensEstimatorTest {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.numVals = 100;
	}

	public void testWeighted() {
		Random rnd = new Random();
		rnd.setSeed(0);
		
		double wei =1.0;
		
		WeightedKernelEstimator kern = new WeightedKernelEstimator();
		
		SimpleKernelEstimator sKern = new SimpleKernelEstimator();
		
		double dVal=0;
		
		for(int i=0;i<numVals;i++) {
			dVal = rnd.nextDouble();
			kern.addValue(dVal, wei);
			sKern.addValue(dVal, wei);
		}
		double[] vals = kern.getValues();
		double[] weights = kern.getWeights();
		assertTrue("val len", vals.length == numVals);
		assertTrue("Wei len", weights.length == numVals);
		
		double a =-1;
		double delta =0.1;
		while(a<=2) {
			System.out.println("PDF(" + a +")"+ kern.getPDF(a));
			assertEquals("Weighted PDF", sKern.getPDF(a), kern.getPDF(a),1E-3);
			a+=delta;
		}
		a=-1;
		while(a<=2) {
			System.out.println("CDF(" + a +")"+ kern.getCDF(a));
			assertEquals("Weighted CDF", sKern.getCDF(a), kern.getCDF(a),1E-3);
			a+=delta;
		}
		
		assertTrue("min CDF", Utils.eq(kern.getCDF(-1.0), 0));
		assertTrue("max CDF", Utils.eq(kern.getCDF(1.5), 1));
		
		assertTrue("min PDF", Utils.eq(kern.getPDF(-1.0), 0));
		assertTrue("max PDF", Utils.eq(kern.getPDF(1.5), 0));
	}

	@Override
	protected DensityEstimator getEstimator() {
		return new WeightedKernelEstimator();
	}

}

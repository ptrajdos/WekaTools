package weka.estimators.density;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import weka.core.Utils;

public class WeightedKernelEstimatorTest {

	@Test
	public void test() {
		Random rnd = new Random();
		rnd.setSeed(0);
		
		int numVals =300;
		double wei =1.0;
		
		WeightedKernelEstimator kern = new WeightedKernelEstimator();
		
		SimpleKernelEstimator sKern = new SimpleKernelEstimator();
		
		double dVal=0;
		
		for(int i=0;i<numVals;i++) {
			dVal = rnd.nextDouble();
			kern.addValue(dVal, wei);
			sKern.addValue(dVal, wei);
		}
		
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

}

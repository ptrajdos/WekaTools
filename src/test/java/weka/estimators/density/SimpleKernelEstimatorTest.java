package weka.estimators.density;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import weka.core.Utils;

public class SimpleKernelEstimatorTest {

	@Test
	public void test() {
		Random rnd = new Random();
		rnd.setSeed(0);
		
		int numVals =300;
		double wei =1.0;
		
		SimpleKernelEstimator kern = new SimpleKernelEstimator();
		
		
		for(int i=0;i<numVals;i++) {
			kern.addValue(rnd.nextDouble(), wei);
		}
		
		double a =-1;
		double delta =0.1;
		while(a<=2) {
			System.out.println("PDF(" + a +")"+ kern.getPDF(a));
			a+=delta;
		}
		a=-1;
		while(a<=2) {
			System.out.println("CDF(" + a +")"+ kern.getCDF(a));
			a+=delta;
		}
		
		assertTrue("min CDF", Utils.eq(kern.getCDF(-1.0), 0));
		assertTrue("max CDF", Utils.eq(kern.getCDF(1.5), 1));
		
		assertTrue("min PDF", Utils.eq(kern.getPDF(-1.0), 0));
		assertTrue("max PDF", Utils.eq(kern.getPDF(1.5), 0));
	}

}

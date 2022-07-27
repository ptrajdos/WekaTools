package weka.estimators.density;

import java.util.Arrays;
import java.util.List;

import weka.estimators.density.histogram.bin.IBin;

public class HistogramDensityEstimatorTest extends DensEstimatorTest {

	@Override
	protected DensityEstimator getEstimator() {
		return new HistogramDensityEstimator();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.compareIntegrateEps=9E-2;
	}
	
	public void testGetBins(){
		try {
			IHistogramDensityEstimator estim = (IHistogramDensityEstimator) this.getEstimator();
			
			double[] uniform = this.generateUniform();
			double[] weights = new double[uniform.length];
			Arrays.fill(weights, 1.0);
			
			estim.addValues(uniform, weights);
			
			List<IBin> bins = estim.getBins();
			
			assertTrue("Bins should have been not null", bins!= null);
			assertTrue("Bins lenght should be greater or equal one.", bins.size() >=1);
		}catch(Exception exc) {
			fail("An exception has been caught: " + exc);
		}
		
	}
}

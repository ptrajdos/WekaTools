package weka.estimators.density.kernelucv;

import weka.estimators.density.DensEstimatorTest;
import weka.estimators.density.DensityEstimator;

public class RectangleUCVKernelEstimatorTest extends DensEstimatorTest {



	@Override
	protected DensityEstimator getEstimator() {
		return new RectangleUCVKernelEstimator();
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.numVals=100;//Due to complexity
		this.integrationSequenceLength=10000;
	}

}

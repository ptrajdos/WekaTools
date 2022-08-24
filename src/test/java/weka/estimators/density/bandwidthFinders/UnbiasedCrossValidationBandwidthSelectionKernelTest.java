package weka.estimators.density.bandwidthFinders;

import weka.estimators.density.DensEstimatorTest;
import weka.estimators.density.DensityEstimator;
import weka.estimators.density.KernelDensityEstimator;
import weka.estimators.density.kernels.EpanechnikovKernel;

public class UnbiasedCrossValidationBandwidthSelectionKernelTest extends DensEstimatorTest {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.numVals=100;
	}

	@Override
	protected DensityEstimator getEstimator() {
		UnbiasedCrossValidationBandwidthSelectionKernelEstimator estim =new UnbiasedCrossValidationBandwidthSelectionKernelEstimator();
		KernelDensityEstimator es = estim.getKernEstim();
		es.setKernel(new EpanechnikovKernel());
		estim.setKernEstim(es);
		return estim;
	}



}

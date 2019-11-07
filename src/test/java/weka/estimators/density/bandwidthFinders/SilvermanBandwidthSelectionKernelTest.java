package weka.estimators.density.bandwidthFinders;

import static org.junit.Assert.*;

import org.junit.Test;

import weka.estimators.density.DensEstimatorTest;
import weka.estimators.density.DensityEstimator;

public class SilvermanBandwidthSelectionKernelTest extends DensEstimatorTest{

	@Override
	protected DensityEstimator getEstimator() {
		return new SilvermanBandwidthSelectionKernel();
	}

	

}

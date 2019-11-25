package weka.estimators.density.kernels;

import weka.estimators.density.Kernel;

public class EpanechnikovKernelTest extends KernelTest {

	@Override
	protected Kernel getKernel() {
		return new EpanechnikovKernel();
	}

	

}

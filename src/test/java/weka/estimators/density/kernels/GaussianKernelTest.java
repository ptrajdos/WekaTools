package weka.estimators.density.kernels;

import weka.estimators.density.Kernel;

public class GaussianKernelTest extends KernelTest {

	@Override
	protected Kernel getKernel() {
		return new GaussianKernel();
	}



}

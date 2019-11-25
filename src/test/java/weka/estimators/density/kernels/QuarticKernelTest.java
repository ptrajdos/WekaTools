package weka.estimators.density.kernels;

import weka.estimators.density.Kernel;

public class QuarticKernelTest extends KernelTest {

	@Override
	protected Kernel getKernel() {
		return new QuarticKernel();
	}

	
}

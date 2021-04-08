package weka.estimators.density.kernels;

import weka.estimators.density.Kernel;

public class SigmoidKernelTest extends KernelTest {

	@Override
	protected Kernel getKernel() {
		return new SigmoidKernel();
	}

	
}

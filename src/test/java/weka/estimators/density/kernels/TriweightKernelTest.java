package weka.estimators.density.kernels;

import weka.estimators.density.Kernel;

public class TriweightKernelTest extends KernelTest {

	@Override
	protected Kernel getKernel() {
		return new TriweightKernel();
	}

	
}

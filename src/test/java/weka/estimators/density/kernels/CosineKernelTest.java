package weka.estimators.density.kernels;

import weka.estimators.density.Kernel;

public class CosineKernelTest extends KernelTest {

	@Override
	protected Kernel getKernel() {
		return new CosineKernel();
	}

	

}

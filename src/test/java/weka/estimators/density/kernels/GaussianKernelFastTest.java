package weka.estimators.density.kernels;

import weka.estimators.density.Kernel;

public class GaussianKernelFastTest extends KernelTest {

	@Override
	protected Kernel getKernel() {
		return new GaussianKernelFast();
	}
}

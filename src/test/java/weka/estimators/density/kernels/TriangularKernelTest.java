package weka.estimators.density.kernels;

import weka.estimators.density.Kernel;

public class TriangularKernelTest extends KernelTest {

	@Override
	protected Kernel getKernel() {
		return new TriangularKernel();
	}

	
}

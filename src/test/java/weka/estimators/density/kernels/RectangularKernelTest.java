package weka.estimators.density.kernels;

import weka.estimators.density.Kernel;

public class RectangularKernelTest extends KernelTest {

	@Override
	protected Kernel getKernel() {
		return new RectangularKernel();
	}

	

}

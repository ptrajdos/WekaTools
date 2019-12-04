package weka.estimators.density.kernels;

import weka.estimators.density.Kernel;

public class LogisticKernelTest  extends KernelTest{

	@Override
	protected Kernel getKernel() {
		return new LogisticKernel();
	}

	

}

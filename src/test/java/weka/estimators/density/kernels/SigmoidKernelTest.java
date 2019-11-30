package weka.estimators.density.kernels;

import static org.junit.Assert.*;

import org.junit.Test;

import weka.estimators.density.Kernel;

public class SigmoidKernelTest extends KernelTest {

	@Override
	protected Kernel getKernel() {
		return new SigmoidKernel();
	}

	
}

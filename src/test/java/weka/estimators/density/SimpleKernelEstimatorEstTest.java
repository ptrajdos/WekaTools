package weka.estimators.density;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleKernelEstimatorEstTest extends DensEstimatorTest{

	@Override
	protected DensityEstimator getEstimator() {
		return new SimpleKernelEstimator();
	}

	

}

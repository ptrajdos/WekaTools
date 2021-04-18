package weka.tools.data;

public class RandomDoubleGeneratorGaussianTest extends RandomDoubleGeneratorTest {

	@Override
	public IRandomDoubleGenerator getRandomDoubleGenerator() {
		return new RandomDoubleGeneratorGaussian();
	}

	
}

package weka.tools.data;

public class RandomDoubleGeneratorUniformTest extends RandomDoubleGeneratorTest {

	@Override
	public IRandomDoubleGenerator getRandomDoubleGenerator() {
		return new RandomDoubleGeneratorUniform();
	}

	
}

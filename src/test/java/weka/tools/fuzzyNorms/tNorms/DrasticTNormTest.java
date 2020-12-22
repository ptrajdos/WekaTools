package weka.tools.fuzzyNorms.tNorms;

public class DrasticTNormTest extends FuzzyTNormTest {

	@Override
	public FuzzyTNorm getTNorm() {
		return new DrasticTNorm();
	}

	
}

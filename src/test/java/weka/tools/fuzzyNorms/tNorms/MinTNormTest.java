package weka.tools.fuzzyNorms.tNorms;

public class MinTNormTest extends FuzzyTNormTest {

	@Override
	public FuzzyTNorm getTNorm() {
		return new MinTNorm();
	}

	

}

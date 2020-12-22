package weka.tools.fuzzyNorms.tNorms;

public class NilpotentTNormTest extends FuzzyTNormTest {

	@Override
	public FuzzyTNorm getTNorm() {
		return new NilpotentTNorm();
	}



}

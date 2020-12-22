package weka.tools.fuzzyNorms.tNorms;

public class LukasiewiczTNormTest extends FuzzyTNormTest {

	@Override
	public FuzzyTNorm getTNorm() {
		return new LukasiewiczTNorm();
	}

	

}

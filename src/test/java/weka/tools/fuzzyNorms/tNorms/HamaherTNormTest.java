package weka.tools.fuzzyNorms.tNorms;

public class HamaherTNormTest extends FuzzyTNormTest {

	@Override
	public FuzzyTNorm getTNorm() {
		return new HamaherTNorm();
	}

	

}

package weka.tools.fuzzyNorms.tNorms;

public class ProdTNormTest extends FuzzyTNormTest {

	@Override
	public FuzzyTNorm getTNorm() {
		return new ProdTNorm();
	}

	

}

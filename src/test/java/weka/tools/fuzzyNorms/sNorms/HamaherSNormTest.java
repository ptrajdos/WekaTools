package weka.tools.fuzzyNorms.sNorms;

public class HamaherSNormTest extends FuzzySNormTest {

	@Override
	public FuzzySNorm getSNorm() {
		return new HamaherSNorm();
	}

	

}

package weka.tools.fuzzyNorms.sNorms;

public class LukasiewiczSNormTest extends FuzzySNormTest {

	@Override
	public FuzzySNorm getSNorm() {
		return new LukasiewiczSNorm();
	}

	

}

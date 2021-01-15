package weka.tools.fuzzyNorms.sNorms;

import static org.junit.Assert.*;

import org.junit.Test;

public class LukasiewiczSNormTest extends FuzzySNormTest {

	@Override
	public FuzzySNorm getSNorm() {
		return new LukasiewiczSNorm();
	}

	

}

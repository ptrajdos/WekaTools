package weka.tools.fuzzyNorms.sNorms;

import static org.junit.Assert.*;

import org.junit.Test;

public class HamaherSNormTest extends FuzzySNormTest {

	@Override
	public FuzzySNorm getSNorm() {
		return new HamaherSNorm();
	}

	

}

package weka.tools.fuzzyNorms.sNorms;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaxSNormTest extends FuzzySNormTest {

	@Override
	public FuzzySNorm getSNorm() {
		return new MaxSNorm();
	}

	

}

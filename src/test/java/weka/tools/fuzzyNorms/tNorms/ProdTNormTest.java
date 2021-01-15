package weka.tools.fuzzyNorms.tNorms;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProdTNormTest extends FuzzyTNormTest {

	@Override
	public FuzzyTNorm getTNorm() {
		return new ProdTNorm();
	}

	

}

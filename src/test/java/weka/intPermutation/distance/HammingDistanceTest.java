package weka.intPermutation.distance;

import static org.junit.Assert.*;

import org.junit.Test;

public class HammingDistanceTest extends IntPermDistanceCalcTest {

	@Override
	protected IntPermDistanceCalc getDistanceCalc() {
		
		return new HammingDistance();
	}

}

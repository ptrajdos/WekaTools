package weka.intPermutation.distance;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositionalDistanceTest extends IntPermDistanceCalcTest {

	@Override
	protected IntPermDistanceCalc getDistanceCalc() {
		
		return new PositionalDistance();
	}

}

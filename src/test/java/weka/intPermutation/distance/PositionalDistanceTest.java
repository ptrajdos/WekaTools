package weka.intPermutation.distance;

public class PositionalDistanceTest extends IntPermDistanceCalcTest {

	@Override
	protected IntPermDistanceCalc getDistanceCalc() {
		
		return new PositionalDistance();
	}

}

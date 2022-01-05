package weka.intPermutation.distance;

public class UlamDistanceTest extends IntPermDistanceCalcTest {

	@Override
	protected IntPermDistanceCalc getDistanceCalc() {
		
		return new UlamDistance();
	}

}

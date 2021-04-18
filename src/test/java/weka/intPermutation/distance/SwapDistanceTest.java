package weka.intPermutation.distance;

public class SwapDistanceTest extends IntPermDistanceCalcTest {

	@Override
	protected IntPermDistanceCalc getDistanceCalc() {
		
		return new SwapDistance();
	}

}

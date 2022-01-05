package weka.intPermutation.distance;

public class HammingDistanceTest extends IntPermDistanceCalcTest {

	@Override
	protected IntPermDistanceCalc getDistanceCalc() {
		
		return new HammingDistance();
	}

}

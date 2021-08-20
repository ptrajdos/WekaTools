package weka.intPermutation.distance;

public class PrecedenceDistanceTest extends IntPermDistanceCalcTest {

	@Override
	protected IntPermDistanceCalc getDistanceCalc() {
		
		return new PrecedenceDistance();
	}

}

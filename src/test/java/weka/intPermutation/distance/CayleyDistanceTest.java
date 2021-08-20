package weka.intPermutation.distance;

public class CayleyDistanceTest extends IntPermDistanceCalcTest {

	@Override
	protected IntPermDistanceCalc getDistanceCalc() {
		
		return new CayleyDistance();
	}

	

}

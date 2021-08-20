package weka.intPermutation.distance;

public class AdjacencyDistanceTest extends IntPermDistanceCalcTest {

	@Override
	protected IntPermDistanceCalc getDistanceCalc() {
		return new AdjacencyDistance();
	}

	
}

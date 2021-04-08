package weka.tools.stats.distributionDistances;

public class DistributionDistanceCalculatorHellingerTest extends DistributionDistanceCalculatorEuclideanTest {

	@Override
	public DistributionDistanceCalculator getDistributionCalculator() {
		return new DistributionDistanceCalculatorHellinger();
	}



}

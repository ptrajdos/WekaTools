package weka.tools.stats.distributionDistances;

public class DistributionDistanceCalculatorTotalVariationTest extends ADistributionDistanceCalculatorTest {

	@Override
	public DistributionDistanceCalculator getDistributionCalculator() {
		return new DistributionDistanceCalculatorTotalVariation();
	}

	

}

package weka.tools.stats.distributionDistances;

public class DistributionDistanceCalculatorKLTest extends ADistributionDistanceCalculatorTest {

	@Override
	public DistributionDistanceCalculator getDistributionCalculator() {
		return new DistributionDistanceCalculatorKL();
	}


}

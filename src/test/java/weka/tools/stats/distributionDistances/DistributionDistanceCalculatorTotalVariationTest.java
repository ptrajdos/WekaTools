package weka.tools.stats.distributionDistances;

import static org.junit.Assert.*;

import org.junit.Test;

public class DistributionDistanceCalculatorTotalVariationTest extends ADistributionDistanceCalculatorTest {

	@Override
	public DistributionDistanceCalculator getDistributionCalculator() {
		return new DistributionDistanceCalculatorTotalVariation();
	}

	

}

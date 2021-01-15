package weka.tools.stats.distributionDistances;

import static org.junit.Assert.*;

import org.junit.Test;

public class DistributionDistanceCalculatorHellingerTest extends DistributionDistanceCalculatorEuclideanTest {

	@Override
	public DistributionDistanceCalculator getDistributionCalculator() {
		return new DistributionDistanceCalculatorHellinger();
	}



}

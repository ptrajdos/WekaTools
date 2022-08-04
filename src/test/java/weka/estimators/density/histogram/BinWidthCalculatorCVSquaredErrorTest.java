package weka.estimators.density.histogram;

public class BinWidthCalculatorCVSquaredErrorTest extends HistogramBinWidthCalculatorTest {

	@Override
	public HistogramBinWidthCalculator getBinWidthCalc() {
		return new BinWidthCalculatorCVSquaredError();
	}

}

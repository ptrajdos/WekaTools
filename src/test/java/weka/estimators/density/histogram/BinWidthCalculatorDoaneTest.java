package weka.estimators.density.histogram;

public class BinWidthCalculatorDoaneTest extends HistogramBinWidthCalculatorTest{

	@Override
	public HistogramBinWidthCalculator getBinWidthCalc() {
		return new BinWidthCalculatorDoane();
	}

}

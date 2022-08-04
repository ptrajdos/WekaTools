package weka.estimators.density.histogram;

public class BinWidthCalculatorPredefinedTest extends HistogramBinWidthCalculatorTest{

	@Override
	public HistogramBinWidthCalculator getBinWidthCalc() {
		return new BinWidthCalculatorPredefined(1E-3);
	}

}

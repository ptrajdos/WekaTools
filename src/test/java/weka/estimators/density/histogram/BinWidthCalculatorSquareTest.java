package weka.estimators.density.histogram;

public class BinWidthCalculatorSquareTest extends HistogramBinWidthCalculatorTest {

	@Override
	public HistogramBinWidthCalculator getBinWidthCalc() {
		return new BinWidthCalculatorSquare();
	}

}

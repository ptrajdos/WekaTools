package weka.estimators.density.histogram;

public class BinWidthCalculatorFreedmanDiaconisTest extends HistogramBinWidthCalculatorTest {

	@Override
	public HistogramBinWidthCalculator getBinWidthCalc() {
		return new BinWidthCalculatorFreedmanDiaconis();
	}



}

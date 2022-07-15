package weka.estimators.density.histogram.bin;


public class BinTest extends IBinTest {

	@Override
	public IBin getBin(double lower, double upper) {
		return new Bin(lower,upper);
	}


}

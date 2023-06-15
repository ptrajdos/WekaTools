package weka.tools.data.splitters;

import static org.junit.Assert.*;

import org.junit.Test;

public class PercentageSplitterTest extends DataSplitterTest {

	@Override
	public DataSplitter getSplitter() {
		return new PercentageSplitter();
	}

	

}

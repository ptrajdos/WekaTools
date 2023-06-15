package weka.tools.data.splitters;

import static org.junit.Assert.*;

import org.junit.Test;

public class CopySpliterTest extends DataSplitterTest {


	@Override
	public DataSplitter getSplitter() {

		return new CopySpliter();
	}

}

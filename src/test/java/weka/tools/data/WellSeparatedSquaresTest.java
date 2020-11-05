package weka.tools.data;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.Instances;
import weka.core.Utils;

public class WellSeparatedSquaresTest extends TestCase {

	@Test
	public void test() {
		WellSeparatedSquares sq = new WellSeparatedSquares();
		double[] overlaps= {-1,0,0.1,0.2,0.5,1.0,2.0};
		for(int i=0;i<overlaps.length;i++) {
			sq.setOverleap(overlaps[i]);
			assertTrue("Overlap set", Utils.eq(sq.getOverleap(), overlaps[i]) );
			Instances data = sq.generateData();
			assertTrue("Data not null", data!=null);
		}
	}

}

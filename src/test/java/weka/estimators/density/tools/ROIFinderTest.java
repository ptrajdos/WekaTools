package weka.estimators.density.tools;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.Utils;
import weka.estimators.density.DensityEstimator;

public class ROIFinderTest extends TestCase {

	@Test
	public void testROI() {
		DensityEstimator densEstim = new DensityEstimator() {
			
			@Override
			public void addValue(double data, double weight) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public double[] getWeights() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public double[] getValues() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public double getPDF(double x) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getCDF(double x) {
				
				if(x<0.5)
					return 0;
				return 1;
			}
			
			@Override
			public void addValues(double[] data, double[] weight) {
				// TODO Auto-generated method stub
				
			}
		};
		
		double[] roi = ROIFinder.findRoi(densEstim, 0, 1.0, 100);
		
		assertTrue("Lower", Utils.eq(roi[0], 0.5));	
		assertTrue("Upper", Utils.eq(roi[1], 0.5));
		
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Utils.SMALL=2E-2;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		Utils.SMALL=1E-6;
	}
	
	

}


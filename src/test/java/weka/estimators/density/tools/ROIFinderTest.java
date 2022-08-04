package weka.estimators.density.tools;

import org.junit.Test;

import junit.framework.TestCase;
import weka.core.Utils;
import weka.estimators.density.DensityEstimator;
import weka.estimators.density.tools.ROIFinder.RoI;

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

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		};
		
		DensityEstimator densEstim2 = new DensityEstimator() {
			
			@Override
			public void addValue(double data, double weight) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void reset() {
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
				
				if(x<0.1)
					return 0;
				return 1;
			}
			
			@Override
			public void addValues(double[] data, double[] weight) {
				// TODO Auto-generated method stub
				
			}
		};
		
		double lower = 0.0;
		double upper = 1.0;
		int numSamples = 100;
		
		double[] roi = ROIFinder.findRoi(densEstim, lower, upper, numSamples);
		
		assertTrue("Lower", Utils.eq(roi[0], 0.5));	
		assertTrue("Upper", Utils.eq(roi[1], 0.5));
		
		RoI roiObj = ROIFinder.findRoi2(densEstim, lower, upper, numSamples);
		
		assertTrue("Lower", Utils.eq(roiObj.getLowerBound(), 0.5));	
		assertTrue("Upper", Utils.eq(roiObj.getUpperBound(), 0.5));
		
		assertTrue("Intersetion with self", roiObj.isIntersecting(roiObj));
		
		RoI roiObj2 = ROIFinder.findRoi2(densEstim2, lower, upper, numSamples);
		assertTrue("Lower", Utils.eq(roiObj2.getLowerBound(), 0.1));	
		assertTrue("Upper", Utils.eq(roiObj2.getUpperBound(), 0.1));
		
		assertFalse("Non intersecting", roiObj2.isIntersecting(roiObj));
		
		assertTrue("No intersection", roiObj.getIntersection(roiObj2) == null);
		
	}
	
	@Test
	public void testROIIntersection(){
		
		DensityEstimator estim1 = new DensityEstimator() {
			
			@Override
			public void addValue(double data, double weight) {}
			
			@Override
			public void reset() {}
			
			@Override
			public double[] getWeights() {return null;}
			
			@Override
			public double[] getValues() {return null;}
			
			@Override
			public double getPDF(double x) {return 0;}
			
			@Override
			public double getCDF(double x) {
				if (x<0)
					return 0.0;
				
				if (x >=1)
					return 1.0;

				return x;
			}
			
			@Override
			public void addValues(double[] data, double[] weight) {}
		};
		
		DensityEstimator estim2 = new DensityEstimator() {
			
			@Override
			public void addValue(double data, double weight) {}
			
			@Override
			public void reset() {}
			
			@Override
			public double[] getWeights() {return null;}
			
			@Override
			public double[] getValues() {return null;}
			
			@Override
			public double getPDF(double x) {return 0;}
			
			@Override
			public double getCDF(double x) {
				if (x<0.5)
					return 0.0;
				
				if (x >=1.5)
					return 1.0;

				return x - 0.5;
			}
			
			@Override
			public void addValues(double[] data, double[] weight) {}
		};
		
		
		double lower = -3.0;
		double upper = 3.0;
		int numSamples = 10000;
		
		RoI roiObj1 = ROIFinder.findRoi2(estim1, lower, upper, numSamples);
		
		RoI roiObj2 = ROIFinder.findRoi2(estim2, lower, upper, numSamples);
		
		double intersectionLen = roiObj1.intersectionLength(roiObj2);
		
		RoI intersectionObj = roiObj1.getIntersection(roiObj2);
		
		
		assertEquals("Intersection Len",0.5, intersectionLen, 0.1);
		
		assertEquals("Intersection lower",0.5, intersectionObj.getLowerBound(), 0.1);
		assertEquals("Intersection lower",1.0, intersectionObj.getUpperBound(), 0.1);
		
		
		
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


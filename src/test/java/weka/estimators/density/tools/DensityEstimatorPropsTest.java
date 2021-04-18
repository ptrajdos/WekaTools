package weka.estimators.density.tools;

import junit.framework.TestCase;
import weka.core.Utils;
import weka.estimators.density.DensityEstimator;

public class DensityEstimatorPropsTest extends TestCase {
	
	
	protected DensityEstimator createUniformEstimator() {
DensityEstimator dens = new DensityEstimator() {
			
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
				if(x >=0 & x<=1)
					return 1;
				return 0;
			}
			
			@Override
			public double getCDF(double x) {
				if(x<0)
					return 0;
				if(x>1)
				return 1;
				return x-1;
			}
			
			@Override
			public void addValues(double[] data, double[] weight) {
				// TODO Auto-generated method stub
				
			}
		};
		return dens;
	}

	
	public void testExpVal() {
		DensityEstimator dens = this.createUniformEstimator();
		try {
			double expVal = DensityEstimatorProps.getExpectedValue(dens, 0, 1);
			double expVal2 = DensityEstimatorProps.getMoment(dens, 0, 1, 1);
			assertTrue("Expected value", Utils.eq(expVal, 0.5));
			assertTrue("Expected value as moment", Utils.eq(expVal, expVal2));
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been caught");
		}
	}
	
	public void testSecMoment() {
		DensityEstimator dens = this.createUniformEstimator();
		try {
			double secM = DensityEstimatorProps.getSecMoment(dens, 0, 1);
			assertTrue("Second Moment", Utils.eq(secM, 1.0/12.0 + 0.25));
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been caught");
		}
		
	}
	
	public void testVariance() {
		DensityEstimator dens = this.createUniformEstimator();
		try {
			double var = DensityEstimatorProps.getVariance(dens, 0, 1);
			double var2 = DensityEstimatorProps.getCentralMoment(dens, 0, 1, 2);
			assertTrue("Variance", Utils.eq(var, 1.0/12.0));
			assertTrue("Variance as central moment", Utils.eq(var2, var));
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been caught");
		}
		
	}
	
	public void testKurtosis() {
		DensityEstimator dens = this.createUniformEstimator();
		try {
			double kurt = DensityEstimatorProps.getStandarizedMoment(dens, 0, 1, 4) -3;
			assertTrue("Kurtosis", Utils.eq(kurt, -1.2));
		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception has been caught");
		}
	}
	
	public void testCreation() {
		DensityEstimatorProps props = new DensityEstimatorProps();
		assertTrue(props!=null);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Utils.SMALL=1e-1;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		Utils.SMALL=1e-6;
	}
	
	

}

package weka.estimators.density;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import weka.core.OptionHandler;
import weka.core.OptionHandlersTest.OptionHandlerTest;



public class BandwidthSelectionKernelEstimatorOptTest extends OptionHandlerTest{

	public BandwidthSelectionKernelEstimatorOptTest(String name, String classname) {
		super(name, classname);
	}
	
	public BandwidthSelectionKernelEstimatorOptTest(String name) {
		this(name, BandwidthSelectionKernelEstimator.class.getCanonicalName());
	}

	@Override
	protected OptionHandler getOptionHandler() {
		BandwidthSelectionKernelEstimator ke = Mockito.mock(BandwidthSelectionKernelEstimator.class, Mockito.CALLS_REAL_METHODS);
		return ke;
	}	

}

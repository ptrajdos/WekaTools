package weka.estimators.density.bandwidthFinders;

import org.mockito.Mockito;

import weka.core.OptionHandler;
import weka.core.OptionHandlersTest.OptionHandlerTest;

public class MaximalSmoothingPrincipleBandwidthSelectionKernelOptTest extends OptionHandlerTest{

	public MaximalSmoothingPrincipleBandwidthSelectionKernelOptTest(String name, String classname) {
		super(name, classname);
	}
	
	public MaximalSmoothingPrincipleBandwidthSelectionKernelOptTest(String name) {
		this(name, MaximalSmoothingPrincipleBandwidthSelectionKernel.class.getCanonicalName());
	}

	@Override
	protected OptionHandler getOptionHandler() {
		MaximalSmoothingPrincipleBandwidthSelectionKernel ke = Mockito.mock(MaximalSmoothingPrincipleBandwidthSelectionKernel.class, Mockito.CALLS_REAL_METHODS);
		return ke;
	}	
	

}

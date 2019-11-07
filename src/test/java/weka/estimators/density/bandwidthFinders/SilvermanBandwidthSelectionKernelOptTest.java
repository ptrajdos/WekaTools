package weka.estimators.density.bandwidthFinders;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import weka.core.OptionHandler;
import weka.core.OptionHandlersTest.OptionHandlerTest;
import weka.estimators.density.BandwidthSelectionKernelEstimator;

public class SilvermanBandwidthSelectionKernelOptTest extends OptionHandlerTest {

	public SilvermanBandwidthSelectionKernelOptTest(String name, String classname) {
		super(name, classname);
	}
	
	public SilvermanBandwidthSelectionKernelOptTest(String name) {
		this(name, SilvermanBandwidthSelectionKernel.class.getCanonicalName());
	}

	@Override
	protected OptionHandler getOptionHandler() {
		SilvermanBandwidthSelectionKernel ke = Mockito.mock(SilvermanBandwidthSelectionKernel.class, Mockito.CALLS_REAL_METHODS);
		return ke;
	}	


}

package weka.estimators.density;

import org.mockito.Mockito;

import weka.core.OptionHandler;
import weka.core.OptionHandlersTest.OptionHandlerTest;

public class AbstractKernelEstimatorTest extends OptionHandlerTest {

	public AbstractKernelEstimatorTest(String name, String classname) {
		super(name, classname);
	}

	public AbstractKernelEstimatorTest(String name) {
		this(name, AbstractKernelEstimator.class.getCanonicalName());
	}

	/* (non-Javadoc)
	 * @see weka.core.OptionHandlersTest.OptionHandlerTest#getOptionHandler()
	 */
	@Override
	protected OptionHandler getOptionHandler() {
		AbstractKernelEstimator ke = Mockito.mock(AbstractKernelEstimator.class, Mockito.CALLS_REAL_METHODS);
		return ke;
	}
	
	

}

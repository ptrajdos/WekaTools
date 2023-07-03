package weka.tools.arrayFunctions;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import weka.core.OptionHandler;
import weka.tools.tests.OptionHandlerChecker;
import weka.tools.tests.SerializationChecker;
import weka.tools.tests.WekaGOEChecker;

public abstract class AFunctionTest extends MultivariateFunctionTest {

	@Test
	public void testSerialization() {
		List<MultivariateFunction> functions = this.getFunctions();
		for(MultivariateFunction fun: functions) {
			assertTrue("Serialization check", SerializationChecker.checkSerializationCopy(fun));
		}
		
	}
	
	@Test
	public void testOptions() {
		
		List<MultivariateFunction> functions = this.getFunctions();
		
		for(MultivariateFunction func: functions)
		
			if (func instanceof OptionHandler) {
				OptionHandlerChecker.checkOptions((OptionHandler) func);
			}
	
	}
	
	@Test
	public void testGOE() {
		
List<MultivariateFunction> functions = this.getFunctions();
		
		for(MultivariateFunction func: functions) {
			WekaGOEChecker checker = new WekaGOEChecker();
			checker.setObject(func);
			checker.checkCallGlobalInfo();
			checker.checkToolTips();
			checker.checkToolTipsCall();
		}

		
	}
	
	 
	

}

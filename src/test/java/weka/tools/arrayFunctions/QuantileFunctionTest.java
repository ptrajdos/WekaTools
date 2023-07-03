/**
 * 
 */
package weka.tools.arrayFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pawel trajdos
 * @version 2.1.0
 * @since 2.1.0
 * 
 *
 */
public class QuantileFunctionTest extends AFunctionTest {

//	@Override
//	public MultivariateFunction getFunctions() {
//		return new QuantileFunction(0.5);
//	}
	
	@Override
	public List<MultivariateFunction> getFunctions() {
		
		ArrayList<MultivariateFunction> funcs = new ArrayList<>();
		funcs.add( new QuantileFunction() );
		funcs.add(new QuantileFunction(0.1));
		funcs.add(new QuantileFunction(0.9));
		return  funcs;
	}


	

}

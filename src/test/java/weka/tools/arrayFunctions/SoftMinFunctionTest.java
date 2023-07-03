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
 */
public class SoftMinFunctionTest extends AFunctionTest {

	@Override
	public List<MultivariateFunction> getFunctions() {
		
		ArrayList<MultivariateFunction> funcs = new ArrayList<>();
		funcs.add( new SoftMinFunction() );
		return  funcs;
	}


}

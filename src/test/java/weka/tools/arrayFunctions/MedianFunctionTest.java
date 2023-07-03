/**
 * 
 */
package weka.tools.arrayFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pawel trajdos
 * @since 2.1.0
 * @version 2.1.0
 *
 */
public class MedianFunctionTest extends AFunctionTest {

	@Override
	public List<MultivariateFunction> getFunctions() {
		
		ArrayList<MultivariateFunction> funcs = new ArrayList<>();
		funcs.add( new MedianFunction() );
		return  funcs;
	}
	
}

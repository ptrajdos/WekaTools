/**
 * 
 */
package weka.core;

/**
 * @author pawel trajdos
 * @since 1.3.1
 * @version 1.3.1
 *
 */
public class EuclideanDistanceTest extends DistanceFunctionTest {

	/**
	 * 
	 */
	public EuclideanDistanceTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 */
	public EuclideanDistanceTest(String name) {
		super(name);
		
	}

	@Override
	public DistanceFunction getDistanceFunction() {
		return new EuclideanDistance();
	}

}

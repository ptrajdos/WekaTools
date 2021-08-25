/**
 * 
 */
package weka.estimators.density;

/**
 * @author pawel trajdos
 * @since 0.13.0
 * @version 1.12.0
 *
 */
public abstract class BoundedDensityEstimatorTest extends DensEstimatorTest {

	public BoundedDensityEstimatorTest() {
		this.eps = 1e-6;
		this.stricEstimInterval=true;
	}



}

/**
 * 
 */
package weka.tools.stats.distributionDistances;

/**
 * @author pawel trajdos
 * @since 1.8.0
 * @version 1.8.0
 *
 */
public class DistributionDistanceCalculatorKL extends ADistributionDistanceCalculator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3795266312856309304L;
	
	private double eps=1e-10;

	@Override
	protected double calculateDistanceInternal(double[] dist1, double[] dist2) {
		double sum=0;
		for(int i=0;i<dist1.length;i++) {
			if(dist1[i] < eps | dist2[i] < eps)
				continue;
			sum+= dist1[i]*Math.log(dist1[i]/dist2[i]);

		}
		return sum;
	}

}

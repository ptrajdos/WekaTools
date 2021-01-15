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
public class DistributionDistanceCalculatorEuclidean extends ADistributionDistanceCalculator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6800122192608240686L;

	@Override
	protected double calculateDistanceInternal(double[] dist1, double[] dist2) {
		double sum=0;
		for(int i=0;i<dist1.length;i++)
			sum+= (dist1[i] - dist2[i])*(dist1[i] - dist2[i]);
		
		double distance = Math.sqrt(sum);
		return distance;
	}

}

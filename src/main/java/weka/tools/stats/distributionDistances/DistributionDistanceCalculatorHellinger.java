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
public class DistributionDistanceCalculatorHellinger extends DistributionDistanceCalculatorEuclidean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4145120065230434947L;

	@Override
	protected double calculateDistanceInternal(double[] dist1, double[] dist2) {
		double distance =0;
		double[] sqrtDist1 = new double[dist1.length];
		double[] sqrtDist2 = new double[dist2.length];
		for(int i=0;i<sqrtDist1.length;i++) {
			sqrtDist1[i] = Math.sqrt(dist1[i]) ;
			sqrtDist2[i] = Math.sqrt(dist2[i]) ;
		}
		distance = (1.0/Math.sqrt(2.0))* super.calculateDistanceInternal(sqrtDist1, sqrtDist2);
	
		return distance;
	}
	
	



}

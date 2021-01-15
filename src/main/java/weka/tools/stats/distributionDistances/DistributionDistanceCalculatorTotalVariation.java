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
public class DistributionDistanceCalculatorTotalVariation extends ADistributionDistanceCalculator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1452661375833647487L;


	@Override
	protected double calculateDistanceInternal(double[] dist1, double[] dist2) {
		double maxVal=0;
		double dist=0;
		for(int i=0;i<dist1.length;i++) {
			dist = Math.abs(dist1[i] - dist2[i]);
			if(dist>maxVal)
				maxVal=dist;
		}
			
		return maxVal;
	}

}

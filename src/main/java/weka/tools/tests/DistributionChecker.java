/**
 * 
 */
package weka.tools.tests;

import weka.core.Utils;

/**
 * Class for checking properties of distribution
 * @author pawel trajdos
 * @since 1.3.0 
 * @version 1.3.0
 *
 */
public class DistributionChecker {

	public static boolean checkDistribution(double[] dist) {
		double sum = Utils.sum(dist);
		if(! Utils.eq(sum, 1.0))
			return false;
		
		for(int i=0;i<dist.length;i++)
			if(dist[i] < 0 | dist[i]>1)
				return false;
		
		return true;
	}

}

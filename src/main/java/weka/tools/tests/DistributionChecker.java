/**
 * 
 */
package weka.tools.tests;

import weka.core.Utils;

/**
 * Class for checking properties of distribution
 * @author pawel trajdos
 * @since 1.3.0 
 * @version 1.3.1
 *
 */
public class DistributionChecker {

	public static boolean checkDistribution(double[] dist) {
		if(dist == null)
			return false;
		double sum = Utils.sum(dist);
		if(! Utils.eq(sum, 1.0)) {
			if(!Utils.eq(sum, 0.0))//Zero response encode missing class
				return false;
				
		}
			
		
		for(int i=0;i<dist.length;i++)
			if(dist[i] < 0 | dist[i]>1)
				return false;
		
		return true;
	}

}

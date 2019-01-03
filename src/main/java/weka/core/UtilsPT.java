/**
 * 
 */
package weka.core;

import java.util.Arrays;

/**
 * @author pawel trajdos
 * @version 0.2.0
 *
 */
public class UtilsPT {

	/**
	 * @version 0.2.0
	 * @param array -- array of  real numbers
	 * @return -- median of the sample
	 */
	public static double median(double[] array){
		double[] tmpArray = Arrays.copyOf(array,array.length);
		Arrays.sort(tmpArray);
		double median=0;
		int centerIdx =  tmpArray.length/2;
		if(tmpArray.length%2==1){
			median = tmpArray[centerIdx];
		}else{
			median = (tmpArray[centerIdx] + tmpArray[centerIdx-1])/2.0;
		}
		
		return median;
		
	}
	/**
	 * Calculated the truncated mean for the given array
	 * @version 0.3.0
	 * @param array -- array to calculate the truncated means
	 * @return truncated mean
	 */
	public static double truncatedMean(double[] array) {
		if(array.length<3) {
			return Utils.mean(array);
		}
		
		double minVal = Double.POSITIVE_INFINITY;
		double maxVal = Double.NEGATIVE_INFINITY;
		double sum=0;
		for(int i=0;i<array.length;i++) {
			sum+= array[i];
			
			if(array[i] < minVal) {
				minVal = array[i];
			}
			
			if(array[i] > maxVal) {
				maxVal=array[i];
			}
		}
		
		sum-= (minVal+maxVal);
		double mean = sum/(array.length-2);
		
		return mean;
	}

}

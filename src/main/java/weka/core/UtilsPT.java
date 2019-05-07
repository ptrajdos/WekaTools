/**
 * 
 */
package weka.core;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author pawel trajdos
 * @since 0.2.0
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
	 * @since 0.3.0
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
	
	/**
	 * Calculate variance of the sample (array)
	 * @since 0.4.0
	 * @version 0.4.0
	 * @param array
	 * @return
	 */
	public static double var(double[] array) {
		double variance =0;
		double mean = Utils.mean(array);
		for(int i=0;i<array.length;i++)
			variance += (array[i] - mean)*(array[i] - mean);
		variance/= array.length-1;
		return variance;
	}
	
	/**
	 * Calculates standard deviation of the sample (array)
	 * @since 0.4.0
	 * @version 0.4.0
	 * @param array
	 * @return
	 */
	public static double stdDev(double[] array) {
		return Math.sqrt(UtilsPT.var(array));
	}
	/**
	 * Calculate covariance of two samples of equal length (arr1, arr2)
	 * @since 0.4.0
	 * @version 0.4.0
	 * @param arr1
	 * @param arr2
	 * @return
	 * @throws Exception when samples are incompatible
	 */
	public static double cov(double[] arr1, double[] arr2)throws Exception{
		if(arr1.length != arr2.length)throw new Exception("Incompatible arrays");
		double cov =0;
		double m1 = Utils.mean(arr1);
		double m2 = Utils.mean(arr2);
		for(int i=0;i<arr1.length;i++)
			cov+= (arr1[i] - m1)*(arr2[i]-m2);
		cov /= arr1.length-1;
		return cov;
	}
	
	/**
	 * Calculate correlation of two samples (arrays)
	 * @since 0.4.0
	 * @version 0.4.0
	 * @param arr1
	 * @param arr2
	 * @return
	 * @throws Exception when arrays are incompatible
	 */
	public static double corr(double[] arr1, double[] arr2)throws Exception{
		double corr=cov(arr1,arr2);
		corr/= stdDev(arr1)*stdDev(arr2);
		return corr;
	}
	
	/**
	 * Compares two double arrays.
	 * @author pawel trajdos
	 * @since 0.5.0
	 * @version 0.5.0
	 * 
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	
	public static boolean compareDoubleArrays(double[] arr1, double[] arr2) {
		if(arr1 ==  arr2)
			return true;
		if(arr1 == null || arr2==null)
			return false;
		if(arr1.length!= arr2.length)
			return false;
		int len = arr1.length;
		
		for(int i=0;i<len;i++)
			if(!Utils.eq(arr1[i], arr2[i]))
				return false;
		return true;
	}
	
	/**
	 * Compares two float arrays.
	 * @author pawel trajdos
	 * @since 0.5.0
	 * @version 0.5.0
	 * 
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	
	public static boolean compareFloatArrays(float[] arr1, float[] arr2) {
		if(arr1 ==  arr2)
			return true;
		if(arr1 == null || arr2==null)
			return false;
		if(arr1.length!= arr2.length)
			return false;
		int len = arr1.length;
		
		for(int i=0;i<len;i++)
			if(! (Math.abs(arr1[i] - arr2[i]) <Utils.SMALL))
				return false;
		return true;
	}
	
	
	

}

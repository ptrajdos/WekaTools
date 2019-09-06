/**
 * 
 */
package weka.core;

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
	 * @version 0.8.1
	 * @param array
	 * @return
	 */
	public static double var(double[] array) {
		double variance =0;
		if(array.length==1)
			return 0.0;
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
	 * @version 0.8.1
	 * @param arr1
	 * @param arr2
	 * @return
	 * @throws Exception when samples are incompatible
	 */
	public static double cov(double[] arr1, double[] arr2)throws Exception{
		if(arr1.length != arr2.length)throw new Exception("Incompatible arrays");
		if(arr1.length == 1)
			return 0.0;
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
	 * @version 0.8.1
	 * @param arr1
	 * @param arr2
	 * @return
	 * @throws Exception when arrays are incompatible
	 */
	public static double corr(double[] arr1, double[] arr2)throws Exception{
		double corr=cov(arr1,arr2);
		if(arr1.length==1)
			return 1.0;
		corr/= stdDev(arr1)*stdDev(arr2);
		return corr;
	}
	
	/**
	 * Compares two double arrays.
	 * @author pawel trajdos
	 * @since 0.5.0
	 * @version 0.5.2
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
		
		for(int i=0;i<len;i++) {
			if(Double.doubleToLongBits(arr1[i]) == Double.doubleToLongBits(arr2[i]))
				continue;
			if(!Utils.eq(arr1[i], arr2[i]))
				return false;
		}
		return true;
	}
	
	/**
	 * Compares two float arrays.
	 * @author pawel trajdos
	 * @since 0.5.0
	 * @version 0.5.2
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
		
		
		for(int i=0;i<len;i++) {
			if(Float.floatToIntBits(arr1[i]) == Float.floatToIntBits(arr2[i]))
				continue;
				
			 if(! (Math.abs(arr1[i] - arr2[i]) <Utils.SMALL))
				return false;
		}
		return true;
	}
	
	/**
	 * Helper function for getting options
	 * Return string for object and its options.
	 * 
	 * @author pawel trajdos
	 * @since 0.6.0
	 * @version 0.6.0
	 * 
	 * @param obj
	 * @return
	 */
	public static String getClassAndOptions(Object obj) {
		String className = obj.getClass().getName();
		String objOptions = " ";
		if(obj instanceof OptionHandler) {
			objOptions = Utils.joinOptions(((OptionHandler) obj).getOptions());
		}
		String result = ""+className + " " + objOptions;
		return result;
	}
	
	/**
	 * Parses the integer option from options. If the integer option is invalid returns defaultValue
	 * @param options
	 * @param optionFlag -- flag of the option
	 * @param defValue -- default Value
	 * @return
	 * 
	 * @author pawel trajdos
	 * @since 0.6.0
	 * @version 0.6.0
	 */
	public static int parseIntegerOption(String[] options,String optionFlag,  int defValue) {
		int value=defValue;
		try {
			String optionStr = Utils.getOption(optionFlag, options);
			value = Integer.parseInt(optionStr);
		} catch (Exception e) {
			System.err.println("Invalid option" + optionFlag + "\n Default value will be used");
		}
		return value;
	}
	
	/**
	 * Parses the double option from options. If the integer option is invalid returns defaultValue
	 * @param options
	 * @param optionFlag -- flag of the option
	 * @param defValue -- default Value
	 * @return
	 * 
	 * @author pawel trajdos
	 * @since 0.6.0
	 * @version 0.10.0
	 */
	public static double parseDoubleOption(String[] options,String optionFlag,  double defValue) {
		double value=defValue;
		try {
			String optionStr = Utils.getOption(optionFlag, options);
			value = Double.parseDouble(optionStr);
		} catch (Exception e) {
			System.err.println("Invalid option: " + optionFlag + "\n Default value will be used");
			System.err.println("Options: " + Arrays.toString(options));
			System.err.println("Default value: " + defValue);
		}
		return value;
	}
	/**
	 * Parses Object from options.
	 * If the object cannot be parsed, then the default value is used. 
	 * 
	 * @param options
	 * @param optionFlag
	 * @param defValue
	 * @param classtype
	 * @return
	 * @throws Exception
	 * 
	 * 
	 * @author pawel trajdos
	 * @since 0.6.0
	 * @version 0.10.0
	 */
	public static Object parseObjectOptions(String[] options, String optionFlag, Object defValue, Class<?> classtype)throws Exception {
		Object parsedObj = defValue;
		try {
			String objectOptionString = Utils.getOption(optionFlag, options);
		    if(objectOptionString.length() != 0) {
		      String objectClassSpec[] = Utils.splitOptions(objectOptionString);
		      if(objectClassSpec.length == 0) { 
		        throw new Exception("Invalid prototype specification string."); 
		      }
		      String className = objectClassSpec[0];
		      objectClassSpec[0] = "";
		      parsedObj = Utils.forName( classtype, 
		                                 className, 
		                                 objectClassSpec);
		    }else {
		    	throw new Exception("No option");
		    }
		}catch(Exception e) {
			System.err.println("Option " + optionFlag + " cannot be parsed. Default value is used");
			System.err.println("Default value: " + defValue.toString());
			System.err.println("Class type: " + classtype.toGenericString());
			System.err.println("Options String: " + Arrays.toString(options));
		}
	    
		return parsedObj;
	}
	
	/**
	 * Calculates soft-max of the array 
	 * @param array
	 * @return
	 *
	 * @author pawel trajdos
	 * @since 0.7.0
	 * @version 0.7.0
	 */
	
	public static double[] softMax(double[] array) {
		double[] tmp =  Arrays.copyOf(array, array.length);
		double sum=0;
		for(int i=0;i<array.length;i++) {
			tmp[i] = Math.exp(tmp[i]);
			sum+= tmp[i];
		}
		if(!Utils.eq(0, sum)) {
			Utils.normalize(tmp);
		}
		return tmp;
	}
	/**
	 * Calculates soft-min of the array 
	 * @param array
	 * @return
	 *
	 * @author pawel trajdos
	 * @since 0.7.0
	 * @version 0.7.0
	 */
	public static double[] softMin(double[] array) {
		double[] tmp =  Arrays.copyOf(array, array.length);
		double sum=0;
		for(int i=0;i<array.length;i++) {
			tmp[i] = Math.exp(-tmp[i]);
			sum+= tmp[i];
		}
		if(!Utils.eq(0, sum)) {
			Utils.normalize(tmp);
		}
		return tmp;
	}

}

/**
 * 
 */
package weka.core;

import java.util.Arrays;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 * @author pawel trajdos
 * @since 0.2.0
 * @version 2.0.0
 *
 */
public class UtilsPT {
	
	public static final Logger logger = Logger.getLogger(UtilsPT.class.getName());
	static {
		logger.setLevel(Level.SEVERE);
	}

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
	 * Calculates q-order quantile from the sample
	 * @param array -- sample
	 * @param q -- quantile order [0,1]
	 * @return quantile
	 * @version 1.5.3
	 * @since 0.12.0
	 */
	public static double quantile(double[] array,double q) {
		if(array.length==1)
			return array[0];
		
		double[] tmpArray = Arrays.copyOf(array,array.length);
		Arrays.sort(tmpArray);
		
		if(q >= 1)
			return tmpArray[array.length-1];
		if(q <= 0)
			return tmpArray[0];
		
		double preIdx = q*(array.length+1);
		double idx = (int)preIdx ;
		if(idx<1)
			return tmpArray[0];
		if(idx>=array.length)
			return tmpArray[tmpArray.length-1];
		
		double g = preIdx - idx;
		double quantile=0;
		quantile = (1-g)*tmpArray[(int)idx-1] + g*tmpArray[(int)idx]; 
		
		
		return quantile;
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
	 * Calculates the geometric mean of the given values
	 * @param values 
	 * @return g-mean
	 */
	public static double geometricMean(double[] values) {
		
		double sum =0;
		for(int i=0;i<values.length;i++)
			sum+= Math.log(values[i]);
		
		double result= Math.exp(sum/values.length);
		return result;
	}
	
	/**
	 * Calculates the harminic mean of given values
	 * @param values
	 * @return
	 */
	public static double harmonicMean(double[] values) {
		double sum=0;
		for(int i=0;i<values.length;i++) 
			sum+=1.0/values[i];
		
		double result =values.length/sum;
		return result;
	}
	
	/**
	 * Calculates the quadratic mean of given values
	 * @param values
	 * @return
	 */
	public static double quadraticMean(double[] values) {
		double sum =0;
		
		for(int i=0;i<values.length;i++) 
			sum+=values[i] * values[i];
		
		double result =Math.sqrt(sum/values.length);
		return result;
	}
	
	/**
	 * Calculates the k-th, standarized, empirical moment of the sample
	 * @param values -- data
	 * @param k -- moment order
	 * @return empirical skewnes
	 */
	public static double standarizedMoment(double[] values, double k) {
		double me = Utils.mean(values);
		double sdev = UtilsPT.stdDev(values);
		
		double[] skewDistr = new double[values.length];
		
		for(int i =0;i<skewDistr.length;i++)
			skewDistr[i] = Math.pow((values[i] - me)/sdev, k);
		
		double skew = Utils.mean(skewDistr);
	
		return skew;
	}
	
	/**
	 * Calculates the empirical skewness of the given values
	 * @param values
	 * @return -- empirical skewness
	 */
	public static double skew(double[] values) {
		return UtilsPT.standarizedMoment(values, 3.0);
	}
	
	/**
	 * Calculates the empirical kurtosis of the given values
	 * @param values
	 * @return -- empirical kurtosis
	 */
	public static double kurtosis(double[] values) {
		return UtilsPT.standarizedMoment(values, 4.0) - 3.0;
	}
	
	/**
	 * Helper function for getting options
	 * Return string for object and its options.
	 * 
	 * @author pawel trajdos
	 * @since 0.6.0
	 * @version 1.8.0
	 * 
	 * @param obj
	 * @return
	 */
	public static String getClassAndOptions(Object obj) {
		
		if(obj == null)
			return "";
		String className = obj.getClass().getName();
		String objOptions = "";
		if(obj instanceof OptionHandler) {
			objOptions = Utils.joinOptions(((OptionHandler) obj).getOptions());
		}
		
		String result;
		if(objOptions.length()>0) {
			result = ""+className + " " + objOptions;	
		}else {
			result = ""+className;
		}
		 
		return result.trim();
	}
	/**
	 * Returns the representation to be used with sklweka.
	 * @param obj -- An object to get options from
	 * @return String representation of the object
	 * 
	 * @since 2.0.0
	 * @version 2.0.0
	 */
	public static String getString4WekaSklearn(Object obj) {
		String className = "classname=" +"\"" + obj.getClass().getName() + "\"";
		
		
		
		String representation = ""+ className;
		
		if(obj instanceof OptionHandler) {
			String[] options = ((OptionHandler) obj).getOptions();
			StringBuffer strBuff = new StringBuffer();
			strBuff.append(", options=[");
			
			for(int i=0;i<options.length;i++) {
				String tmpOptions = Utils.joinOptions(new String[] {options[i].trim()}).trim();
				
				if(! ( tmpOptions.charAt(0) == '"' && tmpOptions.charAt(tmpOptions.length()-1) == '"' ) ) {
					strBuff.append("\"" + options[i].trim() + "\"");
				}else {
					strBuff.append(tmpOptions);
				}
				
				
				
				if(i<options.length -1) {
					strBuff.append(", ");
				}
			}
			
			
			strBuff.append("]");
			representation += strBuff.toString();
			
		}
		
		return representation;
	}
	
	/**
	 * Returns the representation to be used with sklweka.
	 * @param obj -- An object to get options from
	 * @return Map representation of the object
	 * 
	 * @since 2.0.0
	 * @version 2.0.0
	 */
	
	public static Map<String,Object> getWekaSklearnMap(Object obj) {
		
		Map<String,Object> WekaSklearnOptions = new HashMap<String, Object>();
		
		String className =obj.getClass().getName();
		WekaSklearnOptions.put("classname", className);
		
		
		List<String> optList = new LinkedList<String>();
		
		
		if(obj instanceof OptionHandler) {
			String[] options = ((OptionHandler) obj).getOptions();
			
			
			for(int i=0;i<options.length;i++) {
				String trimmedOption = options[i].trim();
				//String tmpOptions = Utils.joinOptions(new String[] {options[i].trim() }).trim();
				
				optList.add(trimmedOption);	
			}
		}
		
		
		WekaSklearnOptions.put("options", optList);
		
		
		return WekaSklearnOptions;
		
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
			logger.warning("Invalid option" + optionFlag + "\n Default value will be used");
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
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("Invalid option: " + optionFlag + "\n Default value will be used");
			strBuilder.append("Options: " + Arrays.toString(options));
			strBuilder.append("Default value: " + defValue);
			logger.warning(strBuilder.toString());
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
	 */
	public static Object parseObjectOptions(String[] options, String optionFlag, Object defValue, Class<?> classtype)throws Exception {
		Object parsedObj = defValue;
		String objectOptionString = null;
		try {
			objectOptionString = Utils.getOption(optionFlag, options);
		    if(objectOptionString.length() != 0) {
		      String objectClassSpec[] = Utils.splitOptions(objectOptionString);
		      if(objectClassSpec.length == 0) { 
		        throw new Exception("Invalid prototype specification string."); 
		      }
		      try {
		      String className = objectClassSpec[0];
		      objectClassSpec[0] = "";
		      parsedObj = Utils.forName( classtype, 
		                                 className, 
		                                 objectClassSpec);
		      }catch(Exception e) {
		    	  throw new Exception("Exception "+ e.getMessage() +" at creating object with options: " + Arrays.toString(objectClassSpec));
		      }
		    }else {
		    	throw new Exception("No options have been given! ");
		    }
		}catch(Exception e) {
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("Option " + optionFlag + " cannot be parsed. Default value is used");
			strBuilder.append("Exception: " + e.getMessage());
			strBuilder.append("Default value: " + defValue.toString());
			strBuilder.append("Class type: " + classtype.toGenericString());
			strBuilder.append("Options String: " + Arrays.toString(options));
			strBuilder.append("Option Object String:  " + ((objectOptionString != null)? objectOptionString: "NULL" ));
			
			logger.warning(strBuilder.toString());
				
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
	 * @version 1.9.0
	 */
	
	public static double[] softMax(double[] array) {
		double[] tmp =  Arrays.copyOf(array, array.length);
		double tmpMax = tmp[Utils.maxIndex(tmp)];
		double sum=0;
		for(int i=0;i<array.length;i++) {
			tmp[i] = Math.exp(tmp[i]-tmpMax);
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
	/**
	 * Khan-Klein summation.
	 *Implemented according to:
	 *
	 * @article{Klein2005,
  doi = {10.1007/s00607-005-0139-x},
  url = {https://doi.org/10.1007/s00607-005-0139-x},
  year = {2005},
  month = nov,
  publisher = {Springer Science and Business Media {LLC}},
  volume = {76},
  number = {3-4},
  pages = {279--293},
  author = {A. Klein},
  title = {A Generalized Kahan-Babu{\v{s}}ka-Summation-Algorithm},
  journal = {Computing}
}
	 *
	 * @param array -- array of numbers to sum
	 * @return sum
	 */
	public static double KhanKleinSum(double[] array) {
		double sum = 0.0;
		double cs = 0.0; 
		double ccs = 0.0;
		double c = 0.0;
		double cc  = 0.0;
		
		for(int i=0;i<array.length;i++) {
			double t = sum + array[i];
			
			if (Math.abs(sum) >= Math.abs(array[i]))
				c = (sum - t) + array[i];
			else
				c = (array[i] - t) + sum;
			
			sum = t;
			t = cs + c;
			
			if( Math.abs(cs) >= Math.abs(c))
				cc = (cs - t) + c;
			else
				cc = (c - t) + cs;
			
			cs =t;
			ccs = ccs + cc;
		}
		
		return sum + cs + ccs;
	}

}

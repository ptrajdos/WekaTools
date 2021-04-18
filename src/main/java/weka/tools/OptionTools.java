/**
 * 
 */
package weka.tools;

import java.util.List;

/**
 * @author pawel
 *
 */
public class OptionTools {

	/**
	 * Concatenates a list of string arrays
	 * @param obj -- list of string arrays
	 * @return
	 */
	public static String[] strArraysConcat(List<String[]> obj){
		if(obj.size()==0)return null;
		
		int arrElementCount=0;
		for(int i=0;i<obj.size();i++){
			arrElementCount+=obj.get(i).length;
		}
		String[] concatenated=new String[arrElementCount];
		int overallCnt=0;
		for(int i=0;i<obj.size();i++){
			for(int e=0;e<obj.get(i).length;e++){
				concatenated[overallCnt++]=obj.get(i)[e];
			}
		}
		
		return concatenated;
	}

}

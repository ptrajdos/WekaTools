/**
 * 
 */
package weka.intPermutation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Operates on permutations of string of positive integer numbers
 * The zero permutation is: min,min+1,min+2,...,max
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class IntPermutationOperator {

	public static boolean isCorrect(int[] perm){
		boolean positive=true;
		int min=Integer.MAX_VALUE;
		int max=Integer.MIN_VALUE;
		
		for(int i=0;i<perm.length;i++){
			
			if(perm[i]<0){
				positive=false;
				break;
			}
			
			if(perm[i]<min){
				min=perm[i];
			}
			if(perm[i]>max){
				max=perm[i];
			}
			
		}
		if(!positive) return false;
		if(max != (min + perm.length -1))return false;
		
		int[] counts = new int[perm.length];
		
		boolean cover = true;
		for(int i =0;i<perm.length;i++){
			counts[perm[i]-min]++;
			if(counts[perm[i]-min]>1){
				cover=false;
				break;
			}
		}
		if(!cover)return false;
		
		
		return true;
	}
	public static boolean areConsistent(int[] perm1, int[] perm2){
		boolean c1, c2;
		if(perm1.length != perm2.length)return false;
			
		c1  = IntPermutationOperator.isCorrect(perm1);
		c2 = IntPermutationOperator.isCorrect(perm2);
		if( ! (c1&&c2))return false;
		
		int max1,max2;
		max1=max2=Integer.MIN_VALUE;
		int min1,min2;
		min1=min2=Integer.MAX_VALUE;
		for(int i=0;i<perm1.length;i++){
			
			if(perm1[i]>max1){
				max1=perm1[i];
			}
			if(perm2[i]>max2){
				max2=perm2[i];
			}
			
			if(perm1[i]<min1){
				min1=perm1[i];
			}
			if(perm2[i]<min2){
				min2=perm2[i];
			}
		}
		
		boolean minC,maxC;
		minC = (min1==min2);
		maxC = (max1==max2);
		if(! (maxC && minC))return false;
		
		return true;
	}
	
	/**
	 * Generates base permutation:
	 * minValue, minValue+1, ...,maxValue; 
	 * @param minValue
	 * @param maxValue
	 * @return generated permuatation
	 * @throws IllegalArgumentException if minValue>= maxValue 
	 */
	public static int[] genPermutation(int minValue,int maxValue, boolean dummmy)throws IllegalArgumentException{
		
		if(maxValue<=minValue)throw new IllegalArgumentException("minValue>maxValue");
		
		int permLen = maxValue - minValue +1;
		int[] permutation = new int[permLen];
		int currValue=minValue;
		for(int i=0;i<permutation.length;i++){
			permutation[i]=currValue++;
		}
		return permutation;
	}
	/**
	 * Generates a sequence of given length starting from minValue 
	 * @param minValue
	 * @param length
	 * @return permutation
	 * @throws IllegalArgumentException if length is equal or les than zero
	 */
	public static int[] genPermutation(int minValue, int length)throws IllegalArgumentException{
		
		if(length<=0)throw new IllegalArgumentException("Length is less or equall zero");
		int[] permutation = new int[length];
		for(int i =0;i<length;i++){
			permutation[i]= minValue + i;
		}
		
		return permutation;
	}
	
	public static int[] reversePermutation(int[] permutation){
		int[] reversed = new int[permutation.length];
		int cnt=0;
		for(int i =permutation.length-1;i>=0;i--){
			reversed[cnt++]= permutation[i];
		}
		
		return reversed;
	}
	
	public static int[] shufflePermutation(int[] permutation,int seed){
		int[] shuffled = new int[permutation.length];
		ArrayList<Integer> permList = new ArrayList<Integer>();
		for(int i:permutation){
			permList.add(new Integer(i));
		}
		Collections.shuffle(permList, new Random(seed));
		
		for(int i=0;i<permutation.length;i++){
			shuffled[i] = permList.get(i);
		}
		
		return shuffled;
	}
	
	public static void printPermutation(int[] permuatation,BufferedWriter outStream) throws IOException{
		int permLen = permuatation.length;
		StringBuilder stringBuild = new StringBuilder();
		for(int i=0;i<permLen;i++){
			stringBuild.append(permuatation[i]);
			if(i+1 != permLen)
				stringBuild.append(",");
		}
		stringBuild.append("\n");
		outStream.write(stringBuild.toString());
		
	}
	public static void printPermutation(int[] permuatation){
		int permLen = permuatation.length;
		StringBuilder stringBuild = new StringBuilder();
		for(int i=0;i<permLen;i++){
			stringBuild.append(permuatation[i]);
			if(i+1 != permLen)
				stringBuild.append(",");
		}
		stringBuild.append("\n");
		System.out.println(stringBuild.toString());
		
		
	}
	
	
	
	
}

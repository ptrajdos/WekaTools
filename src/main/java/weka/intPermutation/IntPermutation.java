/**
 * 
 */
package weka.intPermutation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Class implements permutation of integers
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */
public class IntPermutation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2740481993480858909L;
	private int[] permutation;
	private int min;
	private int max;

	/**
	 * Builds permutation object using given array
	 * @param permutation
	 * @throws IllegalArgumentException
	 */
	public IntPermutation(int[] permutation)throws IllegalArgumentException {
		this.permutation = permutation;
		
		boolean isCorrect = this.isValid();
		
		if(!isCorrect){
			this.min=0;
			this.max=0;
			this.permutation=null;
			throw new IllegalArgumentException("Permutation is inconsistent!");
		}
		
		
	}
	
	public IntPermutation(int len)throws IllegalArgumentException {
		this.permutation = IntPermutationOperator.genPermutation(0, len);
		this.min =0;
		this.max = len-1;
	}
	
	
	public int[] getArray(){
		return this.permutation;
	}
	
	public int getMin(){
		return this.min;
	}
	public int getMax(){
		return this.max;
	}
	/**
	 * Checks if the object is consistent with given permutation
	 * @param perm
	 * @return true is objects are conssistent
	 */
	public boolean isConsistentWith(IntPermutation perm){
		if(perm==null)return false;
		
		boolean minC,maxC;
		minC = (this.min==perm.getMin());
		maxC = (this.max==perm.getMax());
		if(! (minC && maxC))return false;
		
		return true;
	}
	/**
	 * Computes product(composition) of permuataions this * perm 
	 * According to:
	 * Biggs, Norman L.; White, A. T. (1979). Permutation groups and combinatorial structures. Cambridge University Press. ISBN 0-521-22287-7.
	 * @param product
	 * @return 
	 */
	public IntPermutation productPermutation(IntPermutation perm)throws IllegalArgumentException{
		
		boolean compl = this.isConsistentWith(perm);
		if(!compl)throw new IllegalArgumentException("Permutations are not compatible!");
		
		IntPermutation applied = null;
		int[] apPerm =  new int[this.permutation.length];
		int[] trPerm = perm.getArray();
		for(int i=0;i<this.permutation.length;i++){
			//apPerm[i] = trPerm[this.permutation[i]];
			apPerm[i] = this.permutation[trPerm[i]];
		}
		
		applied = new IntPermutation(apPerm);
		return applied;
	}
	
	public List<Set<Integer>> findCycles(){
		IntPermutation baseP = new IntPermutation(IntPermutationOperator.genPermutation(0, this.permutation.length));
		IntPermutation tmpP = baseP;
		
		List<Set<Integer>> cycleHashList = new ArrayList<Set<Integer>>(this.permutation.length);
		for(int i=0;i<this.permutation.length;i++){
			cycleHashList.add(new HashSet<Integer>());
		}
		//Build cyclesHashList
		int[] tmpTab = null;
		do{
			tmpP = this.productPermutation(tmpP);
			tmpTab = tmpP.getArray();
			for(int i=0;i<this.permutation.length;i++){
				cycleHashList.get(i).add(new Integer(tmpTab[i]));
			}
		}while(!baseP.equals(tmpP));
		
		//Find cycles
		List<Set<Integer>> finalCycles = new LinkedList<Set<Integer>>();
		
		boolean[] tabooTable = new boolean[this.permutation.length];
		for(int i=0;i<this.permutation.length;i++){
			if(!tabooTable[i]){
				finalCycles.add(cycleHashList.get(i));
				for(Integer j : cycleHashList.get(i)){
					tabooTable[j.intValue()]=true;
				}
			}
		}
		
		return finalCycles;
	}
	/**
	 * Generates inverse permutation
	 * @return
	 */
	public IntPermutation getInversePermutation(){
		int[] inverseTable = new int[this.permutation.length];
		int tmp=0;
		for(int i=0;i<this.permutation.length;i++){
			tmp = this.permutation[i];
			inverseTable[tmp]=i;
		}
		return new IntPermutation(inverseTable);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder desc = new StringBuilder("Permutation: ");
		for(int i=0;i<this.permutation.length;i++){
			
			if( i==this.permutation.length-1){
				desc.append(this.permutation[i]);
				continue;
			}
			desc.append(this.permutation[i]+", ");

			
		}
		return desc.toString();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null)return false;
		if(this==obj)return true;
		if(! (obj instanceof IntPermutation))return false;
		IntPermutation testObj = (IntPermutation)obj;
		int[] testTab = testObj.getArray();
		for(int i=0;i<this.permutation.length;i++){
			if(this.permutation[i]!=testTab[i])return false;
		}
		
		
		return true;
	}


	/**
	 * Validates permutation
	 * @return true if permutation is valid
	 */
	private boolean isValid(){
		
		boolean positive=true;
		 this.min=Integer.MAX_VALUE;
		 this.max=Integer.MIN_VALUE;
		 
		if(this.permutation == null)return false;
		
		int[] perm = this.permutation;
		for(int i=0;i<perm.length;i++){
			
			if(perm[i]<0){
				positive=false;
				break;
			}
			
			if(perm[i]<this.min){
				this.min=perm[i];
			}
			if(perm[i]>this.max){
				this.max=perm[i];
			}
			
		}
		if(!positive) return false;
		if(min!=0)return false;
		if(this.max != (this.min + perm.length -1))return false;
		
		int[] counts = new int[perm.length];
		
		boolean cover = true;
		for(int i =0;i<perm.length;i++){
			counts[perm[i]-this.min]++;
			if(counts[perm[i]-this.min]>1){
				cover=false;
				break;
			}
		}
		if(!cover)return false;
		
		
		return true;
	}
	
	public void shufflePermutation(int seed){
		this.permutation = IntPermutationOperator.shufflePermutation(this.permutation, seed);
	}

}

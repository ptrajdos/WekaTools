package weka.intPermutation.distance;

import java.util.Collection;
import java.util.Iterator;

import weka.intPermutation.IntPermutation;

/**
 * Calculate distances for set of permutations
 * @author pawel trajdos
 * @since 1.1.0
 * @version 1.1.0
 *
 */

public class SetObjDistanceCalculator implements IntPermSetObjectDistance {

	protected IntPermDistanceCalc distCalc;
	
	
	public SetObjDistanceCalculator(IntPermDistanceCalc distCalc) {
		this.distCalc = distCalc;
	}

	@Override
	public double calculateAverageDistance(IntPermutation obj, IntPermutation[] set) throws Exception {
		double result=0;
		int setSize =set.length;
		for(int i=0;i<setSize;i++){
			result+= this.distCalc.calculateDistance(obj, set[i]);
		}
		result/=setSize;
		return result;
	}

	@Override
	public double calculateAverageDistance(IntPermutation obj, Collection<IntPermutation> set) throws Exception {
		double result =0;
		int setSize  = set.size();
		Iterator<IntPermutation> itr = set.iterator();
		while(itr.hasNext()){
			result+= this.distCalc.calculateDistance(obj, itr.next());
		}
		result/=setSize;
		return result;
	}

	@Override
	public double getMaxValue() {
		return this.distCalc.getMaxDist();
	}

	@Override
	public double getMinValue() {
		return this.distCalc.getMinDist();
	}

}

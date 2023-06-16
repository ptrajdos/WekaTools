/**
 * 
 */
package weka.tools.data.splitters;

import java.util.Arrays;
import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Randomizable;
import weka.core.Utils;

/**
 * @author pawel trajdos
 *
 */
public class PercentageSplitter implements DataSplitter, Randomizable {
	
	protected double[] percentages;
	
	protected int seed;
	
	protected Random rnd;
	
	public PercentageSplitter(double[] percentages, int seed) {
		this.percentages = Arrays.copyOf(percentages, percentages.length);
		Utils.normalize(this.percentages);
		this.seed = seed;
		this.rnd = new Random(seed);
	}
	
	public PercentageSplitter() {
		this(new double[] {0.5, 0.5},0);
	}

	@Override
	public void train(Instances data) {
		//Do nothing

	}

	@Override
	public Instances[] split(Instances data) {
		
		Instances[] retInstances = new Instances[this.percentages.length];
		for(int i = 0; i<this.percentages.length;i++)
			retInstances[i] = new Instances(data, 0);
		
		int nInstances = data.numInstances();
		
		double[] desInstancesCnts = new double[this.percentages.length];
		for(int i = 0; i < this.percentages.length; i++) {
			desInstancesCnts[i] = Math.ceil( this.percentages[i] * nInstances);
		}
		
		for(int i=0; i<nInstances;i++) {
			
			int setIdx =0;
			do {
				setIdx = this.rnd.nextInt(this.percentages.length);
			}while(retInstances[setIdx].numInstances() >= desInstancesCnts[setIdx]);
			
			Instance tmpInstance = data.get(i);
			
			retInstances[setIdx].add(tmpInstance.copy(tmpInstance.toDoubleArray()));
		}
		
		return retInstances;
	}

	@Override
	public void setSeed(int seed) {
		rnd.setSeed(seed);
		this.seed = seed;
		
	}

	@Override
	public int getSeed() {
		return this.seed;
	}

}

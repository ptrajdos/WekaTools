/**
 * 
 */
package weka.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import weka.core.Utils;

/**
 * Stores a set of weighted values
 * @author pawel trajdos
 * @since 0.9.0
 * @version 1.12.0
 *
 */
public class WeightedValuesHolder implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5557317355588921816L;
	
	protected List<Double> values;
	protected List<Double> weights;
	protected double currentWeightSum=0;
	
	public WeightedValuesHolder() {
		this.values  = new ArrayList<Double>(100);
		this.weights = new ArrayList<Double>(100);
	}

	public void addValue(double value, double weight) {
		this.currentWeightSum+= weight;
		this.weights.add(weight);
		this.values.add(value);
	}
	
	public void addValues(double[] values, double[] weights) {
		for(int i=0;i<values.length;i++) {
			this.addValue(values[i], weights[i]);
		}
	}
	
	public double getValue(int index) {
		return this.values.get(index);
	}
	
	public double[] getValues() {
		double[] values = new double[this.values.size()];
		for(int i=0;i<values.length;i++)
			values[i]=this.getValue(i);
		return values;
	}
	/**
	 * Returns a normalized weight related to index
	 * @param index
	 * @return
	 */
	public double getWeight(int index) {
		if(Utils.eq(this.currentWeightSum, 0))
			return this.weights.get(index);
		return this.weights.get(index)/this.currentWeightSum;
	}
	
	public double[] getWeights() {
		double[] weights = new double[this.weights.size()];
		
		for(int i=0;i<weights.length;i++)
			weights[i] = this.getWeight(i);
		
		return weights;
	}
	
	public int getNumVals() {
		return this.values.size();
	}
	
	public void reset() {
		this.values  = new ArrayList<Double>(100);
		this.weights = new ArrayList<Double>(100);
	}

}

/**
 * 
 */
package weka.core;

import weka.core.DenseInstance;
import weka.core.Instance;

/**
 * @author Pawel Trajdos
 * Fast getter of the values -- no copy
 *
 */
public class DenseInstanceFast extends DenseInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3404876881343029877L;

	/**
	 * @param instance
	 */
	public DenseInstanceFast(Instance instance) {
		super(instance);
	}

	/**
	 * @param numAttributes
	 */
	public DenseInstanceFast(int numAttributes) {
		super(numAttributes);
	}

	/**
	 * @param weight
	 * @param attValues
	 */
	public DenseInstanceFast(double weight, double[] attValues) {
		super(weight, attValues);
	}

	/* (non-Javadoc)
	 * @see weka.core.DenseInstance#toDoubleArray()
	 */
	@Override
	public double[] toDoubleArray() {
		return this.m_AttValues;
	}

	/* (non-Javadoc)
	 * @see weka.core.DenseInstance#copy()
	 */
	@Override
	public Object copy() {
		
		  DenseInstanceFast result = new DenseInstanceFast(this);
		  result.m_Dataset = this.m_Dataset;
		    return result;
	}

	/* (non-Javadoc)
	 * @see weka.core.DenseInstance#mergeInstance(weka.core.Instance)
	 */
	@Override
	public Instance mergeInstance(Instance inst) {
		int m = 0;
	    double[] newVals = new double[numAttributes() + inst.numAttributes()];
	    for (int j = 0; j < numAttributes(); j++, m++) {
	      newVals[m] = value(j);
	    }
	    for (int j = 0; j < inst.numAttributes(); j++, m++) {
	      newVals[m] = inst.value(j);
	    }
	    return new DenseInstanceFast(1.0, newVals);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
	

}

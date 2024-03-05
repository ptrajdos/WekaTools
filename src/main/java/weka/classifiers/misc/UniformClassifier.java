/**
 * 
 */
package weka.classifiers.misc;

import java.io.Serializable;
import java.util.Arrays;

import weka.classifiers.AbstractClassifier;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

/**
 * 
 * Class implements classifier that always return uniform class distribution in soft predictions.
 * 
 * 
 */
public class UniformClassifier extends AbstractClassifier implements Serializable {

	private static final long serialVersionUID = -8488407064263836976L;
	
	protected int nClasses;


	@Override
	public void buildClassifier(Instances data) throws Exception {
		if(!this.m_DoNotCheckCapabilities)
			this.getCapabilities().testWithFail(data);
		this.nClasses = data.numClasses();
	}

	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {

		double[] distribution = new double[this.nClasses];
		Arrays.fill(distribution, 1.0);
		Utils.normalize(distribution);
		
		return distribution;
	}

	@Override
	public Capabilities getCapabilities() {
		Capabilities baseCapabilities = super.getCapabilities();
		baseCapabilities.disable(Capability.DATE_CLASS );
		baseCapabilities.disable(Capability.EMPTY_NOMINAL_CLASS);
		baseCapabilities.disable(Capability.NUMERIC_CLASS);
		baseCapabilities.disable(Capability.RELATIONAL_CLASS);
		baseCapabilities.disable(Capability.NO_CLASS);
		baseCapabilities.disable(Capability.STRING_CLASS);
		baseCapabilities.disable(Capability.UNARY_CLASS);
		
		return baseCapabilities;
	}

	public String globalInfo() {
		return "Class  that implements algorithm that always returns distribution 1.0/nClasses";
	}
	
	
	
	
	

}

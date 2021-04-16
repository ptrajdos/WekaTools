/**
 * 
 */
package weka.estimators;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;
import weka.tools.GlobalInfoHandler;

/**
 * @author pawel trajdos
 * @since 1.9.0
 * @version 1.9.0
 *
 */
public class MultivariateEstimatorFromInstancesWrapper implements MultivariateEstimatorFromInstances, Serializable, OptionHandler,GlobalInfoHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2223319848030963620L;
	
	protected MultivariateEstimator mEstimator = new MultivariateGaussianEstimator();
	
	/**
	 * Reference data
	 */
	protected Instances refData;
	
	protected boolean[] activeAttrs;
	protected List<Integer> activeIndices;

	protected boolean noInstances=false;
	protected boolean uninitialised=true;

	
	protected void initialiseAttributeIndices() {
		int numAttrs = this.refData.numAttributes();
		
		this.activeIndices = new LinkedList<Integer>();
		
		this.activeAttrs = new boolean[numAttrs];
		for(int i=0;i<numAttrs;i++) {
			if(i == this.refData.classIndex())continue;
			this.activeAttrs[i] = this.refData.attribute(i).isNumeric() ;
			if(this.activeAttrs[i])this.activeIndices.add(i);
		}
		
	}
	protected void initialiseEstimator() {
		
		int numInst = this.refData.numInstances();
		if(numInst ==0) {
			this.noInstances=true;
			return;
		}
		int numValAttrs = this.activeIndices.size();
		double[] instWeights = new double[numInst];
		
		double[][] instRepresentation = new double[numInst][numValAttrs];
		Instance tmpInst;
		double[] iRep;
		for(int i =0;i<numInst;i++) {
			tmpInst =this.refData.get(i); 
			iRep = tmpInst.toDoubleArray();
			instWeights[i] = tmpInst.weight();
			for(int a =0;a<numValAttrs;a++) {
				instRepresentation[i][a] = iRep[this.activeIndices.get(a)];
			}
		}
		try {
			this.mEstimator.estimate(instRepresentation, instWeights);
			this.uninitialised=false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void estimate(Instances instances) {
		this.refData = instances;
		this.initialiseAttributeIndices();
		this.initialiseEstimator();
		this.uninitialised=false;

	}
	
	protected double[] transformInstance(Instance inst) {
		int numActAttrs = this.activeIndices.size();
		double[] instRepTrans = new double[numActAttrs];
		double[] instRep = inst.toDoubleArray();
		for(int a=0;a<numActAttrs;a++)
			instRepTrans[a] = instRep[this.activeIndices.get(a)];
		
		return instRepTrans;
	}

	@Override
	public double density(Instance instance) {
		if(this.noInstances | this.uninitialised)
			return 0;
		
		
		double[] instanceRep = this.transformInstance(instance);
		double logDens = this.mEstimator.logDensity(instanceRep);
		double dens = Math.exp(logDens);
		return dens;
	}

	@Override
	public double logDensity(Instance instance) {
		if(this.noInstances | this.uninitialised)
			return Double.NEGATIVE_INFINITY;
		
		
		double[] instanceRep = this.transformInstance(instance);
		double dens = this.mEstimator.logDensity(instanceRep);
		return dens;
	}

	/**
	 * @return the mEstimator
	 */
	public MultivariateEstimator getmEstimator() {
		return this.mEstimator;
	}

	/**
	 * @param mEstimator the mEstimator to set
	 */
	public void setmEstimator(MultivariateEstimator mEstimator) {
		this.mEstimator = mEstimator;
	}
	
	public String mEstimatorTipText() {
		return "Estimator to use internally";
	}

	@Override
	public Enumeration<Option> listOptions() {
		
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tMultivariate estimator to use "+
		          "(default: "+MultivariateGaussianEstimator.class.getCanonicalName() +   ").\n",
			      "EST", 1, "-EST"));
		
		return newVector.elements();
		
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		
	this.setmEstimator(	(MultivariateEstimator) UtilsPT.parseObjectOptions(options, "EST", new MultivariateGaussianEstimator(), MultivariateEstimator.class));
		
	}

	@Override
	public String[] getOptions() {
		 Vector<String> result;

		 result = new Vector<String>();
		 result.add("-EST");
		 result.add(UtilsPT.getClassAndOptions(this.getmEstimator()));
		    
		    
		 return result.toArray(new String[result.size()]);
	}

	@Override
	public String globalInfo() {
		return "Estimates multivariate density from Instances object";
	}
	
	

}

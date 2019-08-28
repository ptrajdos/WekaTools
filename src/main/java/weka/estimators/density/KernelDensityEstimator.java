package weka.estimators.density;

/**
 * An interface for Kernel Density Estimator
 *
 * @author pawel trajdos
 * @since 0.9.0
 * @version 0.9.0
 * 
 *
 */
public interface KernelDensityEstimator extends DensityEstimator {
	
	public void setKernel(Kernel kernel);
	public Kernel getKernel();
	
	public void setBandwidth(double bandwidth);
	public double getBandwidth();

}

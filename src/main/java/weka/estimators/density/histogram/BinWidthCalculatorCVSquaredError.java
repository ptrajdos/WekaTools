/**
 * 
 */
package weka.estimators.density.histogram;

import java.io.Serializable;
import java.util.List;

import weka.estimators.density.AHistogramDensityEstimator;
import weka.estimators.density.HistogramDensityEstimator;
import weka.estimators.density.IHistogramDensityEstimator;
import weka.estimators.density.histogram.bin.IBin;
import weka.tools.Linspace;
import weka.tools.SerialCopier;

/**
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 *
 * 
 *
 */
public class BinWidthCalculatorCVSquaredError implements HistogramBinWidthCalculator, Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6098309296685892758L;

	@Override
	public double getWidth(IHistogramDensityEstimator histEstim) {
		
		HistogramBinWidthCalculator calc = new BinWidthCalculatorSquare();
		double upperH =  calc.getWidth(histEstim);
		
		
		if(histEstim instanceof AHistogramDensityEstimator) {
			try {
				double lowerH = 1E-6;
				HistogramDensityEstimator tmpEstim = (HistogramDensityEstimator) SerialCopier.makeCopy(histEstim);
				double[] values = tmpEstim.getValues();
				double[] weights = tmpEstim.getWeights();
				
				
				
				double[] hSequence  = Linspace.genLinspace(lowerH, upperH, 20);
				double numElements = values.length;
				
				double minCritVal = Double.POSITIVE_INFINITY;
				double minH = 0;
				
				
				for(int i=0;i<hSequence.length;i++) {
					double h = hSequence[i];
					tmpEstim.setBinWidthCalculator(new BinWidthCalculatorPredefined(h));
					tmpEstim.reset();
					tmpEstim.addValues(values, weights);
					
					double sqSum = this.getSquaredCountSum(tmpEstim.getBins());
					double critVal = 2.0/((numElements -1)*h) - ((numElements+1)/(numElements*numElements*(numElements-1)*h))*sqSum;
					
					if (critVal < minCritVal) {
						minCritVal = critVal;
						minH = h;
					}
					
				}
				
				return minH;
				
				
			} catch (Exception e) {
				e.printStackTrace();
				return upperH;
			}
		}
		
		return upperH;
		
	}
	
	protected double getSquaredCountSum(List<IBin> list){
		double squaredSum = 0;
		
		for (IBin iBin : list) {
			double binCount = iBin.getCount(); 
			squaredSum +=  binCount*binCount;
		}
		
		return squaredSum;
		
	}

}

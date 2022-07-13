/**
 * 
 */
package weka.estimators.density.histogram;

import java.io.Serializable;

import weka.core.Utils;
import weka.core.UtilsPT;
import weka.tools.WeightedValuesHolder;

/**
 * @author pawel trajdos
 * @version 1.14.0
 * @since 1.14.0
 *
 */
public class BinWidthCalculatorDoane implements HistogramBinWidthCalculator, Serializable {

	/**
	 * Doane's mathod of obtaining bin width.
	 * Implemented according to:
	 * @article{doane1976aesthetic,
		  title={Aesthetic frequency classifications},
		  author={Doane, David P},
		  journal={The American Statistician},
		  volume={30},
		  number={4},
		  pages={181--183},
		  year={1976},
		  publisher={Taylor \& Francis}
		}
	 * 
	 */
	private static final long serialVersionUID = 485106661098619854L;
	
	private double minBinWidth =1E-5;
	public static double eps = 1E-6;

	@Override
	public double getWidth(WeightedValuesHolder valHolder) {
		double[] values = valHolder.getValues();
		
		double absSkew = Math.abs(UtilsPT.skew(values));
		double n = values.length;
		double dg1 = Math.sqrt( 6.0*( n - 2.0)/( ( n+1.0 ) * ( n+3.0 ) )  );
		double minVal = values[Utils.minIndex(values)];
		double maxVal = values[Utils.maxIndex(values)];
		double range = maxVal-minVal;
		
		if( dg1 < eps )
			return range;
		
		double numBins = 1 + Math.log(n)/Math.log(2.0) + Math.log( 1 + absSkew/dg1 )/Math.log(2.0);
		
		
		
		double binWidth = range/numBins;
		if(binWidth<this.minBinWidth)
			binWidth=this.minBinWidth;
		return binWidth;
		
	}

}

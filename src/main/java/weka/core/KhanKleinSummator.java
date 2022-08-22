/**
 * 
 */
package weka.core;

import java.io.Serializable;
import java.util.Collection;

/**
 * Object that performs Khan-Klein summation.
 * Implemented according to:
 *  @article{Klein2005,
  doi = {10.1007/s00607-005-0139-x},
  url = {https://doi.org/10.1007/s00607-005-0139-x},
  year = {2005},
  month = nov,
  publisher = {Springer Science and Business Media {LLC}},
  volume = {76},
  number = {3-4},
  pages = {279--293},
  author = {A. Klein},
  title = {A Generalized Kahan-Babu{\v{s}}ka-Summation-Algorithm},
  journal = {Computing}
}
 * @author pawel trajdos
 * @since 2.0.0
 * @version 2.0.0
 */
public class KhanKleinSummator implements Serializable {
	
	private static final long serialVersionUID = -4867366981517568876L;
	double sum;
	double cs;
	double ccs;
	double c;
	double cc;

	
	private void initialize() {
		this.sum = 0.0;
		this.cs = 0.0;
		this.ccs = 0.0;
		this.c = 0.0;
		this.cc = 0.0;
		
	}
	public KhanKleinSummator() {
		this.initialize();
	}
	
	/**
	 * Resets the summation inside object
	 */
	public void reset() {
		this.initialize();
	}
	
	/**
	 * Adds value to the internal sum
	 * @param value
	 */
	public void addToSum(double value) {
		
		double t = this.sum + value;
		
		if(Math.abs(this.sum) >= Math.abs(value))
			this.c = (this.sum - t) + value;
		else
			this.c = (value - t) + this.sum;
		
		this.sum = t;
		
		t = this.cs + this.c;
		
		if ( Math.abs(this.cs) >= Math.abs(this.c) )
			this.cc = (this.cs - t) + this.c;
		else
			this.cc = (this.c - t) + this.cs;
		
		this.cs = t;
		this.ccs = this.ccs + this.cc;
				
	}
	
	/**
	 * Adds multiple values to the internal sum
	 * @param values -- an array
	 */
	public void addToSum(double[] values) {
		for (double d : values) {
			this.addToSum(d);
		}
	}
	
	/**
	 * Adds multiple values to the internal sum
	 * @param values -- Collection
	 */
	public void addToSum(Collection<Double> values) {
		for (Double double1 : values) {
			this.addToSum(double1);
		}
		
	}
	
	/**
	 * Gets the sum
	 * @return
	 */
	public double getSum() {
		return this.sum + this.cs + this.ccs;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		
		if( !(obj instanceof KhanKleinSummator))
			return false;
		
		KhanKleinSummator tmp = (KhanKleinSummator) obj;
		
		if(! Utils.eq(c, tmp.c))
			return false;
		
		if(! Utils.eq(cc, tmp.cc))
			return false;
		
		if(! Utils.eq(cs, tmp.cs))
			return false;
		
		if(! Utils.eq(ccs, tmp.ccs))
			return false;
		
		if(! Utils.eq(sum, tmp.sum))
			return false;
		
		return true;
		
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("KhanKlein summator: \n");
		buffer.append("sum: " + this.sum + "\n");
		
		buffer.append("cs: " + this.cs + "\n");
		buffer.append("ccs: " + this.ccs + "\n");
		buffer.append("cc: " + this.cc + "\n");
		buffer.append("c: " + this.c + "\n");
		
		return buffer.toString();
	}
	
	

}

package weka.tools.arrayFunctions;

import java.util.Enumeration;
import java.util.Vector;

import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.UtilsPT;

/**
 * 
 * @author pawel trajdos
 * @version 2.1.0
 * @since 2.1.0
 *
 */
public class QuantileFunction extends AFunction implements OptionHandler {

	private static final long serialVersionUID = -1172067297803198679L;
	
	private double quant = 0.5;
	
	public QuantileFunction() {
		this(0.5);
	}
	
	public QuantileFunction(double quant) {
		this.quant = quant;
	}

	@Override
	public double value(double[] point) {
		return UtilsPT.quantile(point, quant);
	}

	public double getQuant() {
		return quant;
	}

	public void setQuant(double quant) {
		this.quant = quant;
	}
	
	public String quantTipText() {
		return "Quantile to calculate";
	}

	@Override
	public Enumeration<Option> listOptions() {
		
		Vector<Option> newVector = new Vector<Option>(1);
		
		newVector.addElement(new Option(
			      "\tThe quantile to use "+
		          "(default: 0.5).\n",
			      "QA", 1, "-QA"));
	
		return newVector.elements();
	}

	@Override
	public void setOptions(String[] options) throws Exception {
		
		this.setQuant(UtilsPT.parseDoubleOption(options, "QA", 0.5));
		
	}

	@Override
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		
		options.add("-QA");
		options.add(""+this.getQuant());
		
	    return options.toArray(new String[0]);
	}
	
	

}

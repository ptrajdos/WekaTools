/**
 * 
 */
package weka.tools.data.splitters;

import java.io.Serializable;

import weka.core.Instances;

/**
 * @author pawel trajdos
 * @since 2.1.0
 * @version 2.1.0
 *
 */
public class CopySplitter implements DataSplitter, Serializable {
	
	private static final long serialVersionUID = 8915051309918891290L;
	protected int nCopies; 
	
	public CopySplitter(int nCopies) {
		this.nCopies = nCopies;
	}
	
	public CopySplitter() {
		this(2);
	}

	@Override
	public void train(Instances data) {
		// Does Nothing

	}

	@Override
	public Instances[] split(Instances data) {
		Instances[] splitted  = new Instances[this.nCopies];
		
		for(int i=0;i<this.nCopies;i++) {
			splitted[i] = new Instances(data);
		}
		
		return splitted;
	}

}

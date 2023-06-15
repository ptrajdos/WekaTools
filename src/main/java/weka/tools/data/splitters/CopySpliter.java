/**
 * 
 */
package weka.tools.data.splitters;

import weka.core.Instances;

/**
 * @author pawel trajdos
 * @since 2.1.0
 * @version 2.1.0
 *
 */
public class CopySpliter implements DataSplitter {
	
	protected int nCopies; 
	
	public CopySpliter(int nCopies) {
		this.nCopies = nCopies;
	}
	
	public CopySpliter() {
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

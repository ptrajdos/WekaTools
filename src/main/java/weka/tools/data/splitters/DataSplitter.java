package weka.tools.data.splitters;

import weka.core.Instances;
/**
 * 
 * Data Splitter interface
 * @author pawel trajdos
 * @since 2.1.0
 * @version 2.1.0
 *
 */
public interface DataSplitter {
	
	public void train(Instances data);
	
	public Instances[] split(Instances data);
	

}

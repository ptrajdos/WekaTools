/**
 * 
 */
package weka.tools.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import weka.core.Randomizable;

/**
 * @author pawel trajdos
 * @since 1.7.0
 * @version 1.7.0
 * 
 *
 */
public class RandomDateGenerator implements Randomizable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1397058862422070816L;
	protected int seed=0;
	protected Random rnd = new Random(this.seed);
	protected DateFormat dateFormat = new SimpleDateFormat();


	@Override
	public void setSeed(int seed) {
		this.seed= seed;
		this.rnd.setSeed(seed);
		
	}

	@Override
	public int getSeed() {
		return this.seed;
	}
	
	public Date getNextDate() {
		long dateL = rnd.nextLong();
		Date dat = new Date(dateL);
		return dat;
	}
	
	public String getNextDateString() {
		return this.dateFormat.format(this.getNextDate());
	}

	/**
	 * @return the dateFormat
	 */
	public DateFormat getDateFormat() {
		return this.dateFormat;
	}

	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	

}

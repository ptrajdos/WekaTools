package weka.tools.data;

import java.text.DateFormat;
import java.util.Date;

import weka.core.Randomizable;
/**
 * An interface for Random Date Generators
 * @author pawel trajdos
 * @since 1.10.0
 * @version 1.10.0
 *
 */

public interface IRandomDateGenerator extends Randomizable {
	
	/**
	 * Generate next date
	 * @return
	 */
	public Date getNextDate();
	
	
	/**
	 * Generate next string containing formatted date
	 * @return
	 */
	public String getNextDateString();

	/**
	 * Gets the date format
	 * @return the dateFormat
	 */
	public DateFormat getDateFormat();

	/**
	 * Sets the date format
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(DateFormat dateFormat);

}

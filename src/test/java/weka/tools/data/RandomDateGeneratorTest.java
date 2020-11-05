package weka.tools.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import weka.tools.SerialCopier;

public class RandomDateGeneratorTest extends TestCase {


	public void testDates() {
		RandomDateGenerator gen = new RandomDateGenerator();
		int numOfChecks = 5;
		for(int i=0;i<numOfChecks;i++) {
			Date dat = gen.getNextDate();
			assertTrue("Not null", dat != null);
			
			String datS = gen.getNextDateString();
			
			assertTrue("Not null", datS != null);
		}
		
	}
	
	public void testSeed() {
		RandomDateGenerator gen = new RandomDateGenerator();
		assertTrue("Default seed", gen.getSeed()==0);
		int seedVal = 2;
		gen.setSeed(seedVal);
		assertTrue("Default seed", gen.getSeed()==seedVal);
	}
	
	public void testDateFormat() {
		RandomDateGenerator gen = new RandomDateGenerator();
		DateFormat format = gen.getDateFormat();
		assertTrue("Class for date Format: ", (format != null) & (format instanceof DateFormat) );
		
		DateFormat form2 = new SimpleDateFormat();
		gen.setDateFormat(form2);
		assertTrue("Setting Date Format", form2.equals(gen.getDateFormat()));
	}
	
	public void testSerializable() {
		RandomDateGenerator gen = new RandomDateGenerator();
		try {
			RandomDateGenerator gen2 = (RandomDateGenerator) SerialCopier.makeCopy(gen);
		} catch (Exception e) {
			fail("An Exception has been caught");
		}
	}

}

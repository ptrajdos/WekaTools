/**
 * 
 */
package weka.tools.tests;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Vector;

import weka.core.CheckGOE;
import weka.core.OptionMetadata;
import weka.gui.ProgrammaticProperty;

/**
 * @author pawel trajdos
 * @since 1.2.0
 * @version 1.2.0
 *
 */
public class WekaGOEChecker  extends CheckGOE{

	/**
	   * checks whether the object declares a globalInfo method.
	   * 
	   * @return true if the test was passed
	   */
	  public boolean checkCallGlobalInfo() {
	    boolean result;
	    Class<?> cls;
	    Object obj = this.getObject();
	    

	    result = true;
	    cls = obj.getClass();

	    // test for globalInfo method
	    try {
	      Method method= cls.getMethod("globalInfo", (Class[]) null);
	      method.setAccessible(true);
	      String globalInfo = (String) method.invoke(obj, null);
	     
	      if(globalInfo.length() == 0)
	    	  result = false;
	      
	    } catch (Exception e) {
	      result = false;
	    }

	    return result;
	  }
	  
	  /**
	   * checks whether the object declares tip text method for all its properties.
	   * 
	   * @return true if the test was passed
	   */
	  public boolean checkToolTipsCall() {
	    boolean result;
	    Class<?> cls;
	    BeanInfo info;
	    PropertyDescriptor[] desc;
	    int i;
	    Vector<String> missing;
	    String suffix;

	    print("Tool tips...");

	    result = true;
	    suffix = "TipText";
	    cls = getObject().getClass();

	    // get properties
	    try {
	      info = Introspector.getBeanInfo(cls, Object.class);
	      desc = info.getPropertyDescriptors();
	    } catch (Exception e) {
	      e.printStackTrace();
	      desc = null;
	    }

	    // test for TipText methods
	    if (desc != null) {
	      missing = new Vector<String>();

	      for (i = 0; i < desc.length; i++) {
	        // skip property?
	        if (m_IgnoredProperties.contains(desc[i].getName())) {
	          continue;
	        }
	        if ((desc[i].getReadMethod() == null)
	          || (desc[i].getWriteMethod() == null)) {
	          continue;
	        }

	        OptionMetadata m = desc[i].getReadMethod().getAnnotation(OptionMetadata.class);
	        if (m == null) {
	          m = desc[i].getWriteMethod().getAnnotation(OptionMetadata.class);
	        }
	        if (m != null) {
	          continue;
	        }

	        // programatic properties don't need tip texts
	        ProgrammaticProperty p = desc[i].getReadMethod().getAnnotation(ProgrammaticProperty.class);
	        if (p == null) {
	          p = desc[i].getWriteMethod().getAnnotation(ProgrammaticProperty.class);
	        }
	        if (p != null) {
	          continue;
	        }

	        try {
	         Method method =  cls.getMethod(desc[i].getName() + suffix, (Class[]) null);
	         method.setAccessible(true);
	         String tipText =(String) method.invoke(this.getObject(), null); 
	         
	         if(tipText.length()==0)
	        	 result=false;
	         
	        } catch (Exception e) {
	          result = false;
	          missing.add(desc[i].getName() + suffix);
	        }
	      }

	      if (result) {
	        println("yes");
	      } else {
	        println("no (missing: " + missing + ")");
	      }

	    } else {
	      println("maybe");
	      result=false;
	    }

	    return result;
	  }
	  
	  


}

package uy.com.s4b.inside.core.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Title: UtilsString.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 20/11/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */

public class UtilsString {

	/**
	 * 
	 */
	public UtilsString() {
		
	}
	
	/**
     * Match if the beginning of the text matches a list item
     *
     * @param text to search
     * @param list where search text
     * @return true if text was found
     */
	public static boolean searchTextInList(String text, List<String> list) {
		int i = 0;
		boolean found = false;
		
		while(i < list.size() && !found) {
			if (text.startsWith(list.get(i))) {
				found = true;
			}
			i++;	
		}
		return found;
	}

}

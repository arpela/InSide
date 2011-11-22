package uy.com.s4b.inside.core.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Title: UtilsDate.java <br>
 * Description: <br>
 * Fecha creaci�n: 09/11/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

public class UtilsDate {

	/**
	 * 
	 */
	public UtilsDate() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public static String getTimeStamp() {
		SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatdate.format(Calendar.getInstance().getTime());
	}

}

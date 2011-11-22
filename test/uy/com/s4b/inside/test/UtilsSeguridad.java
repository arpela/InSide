package uy.com.s4b.inside.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import uy.com.s4b.inside.core.common.CriptPassword;


/**
 * Title: UtilsSeguridad.java <br>
 * Description: <br>
 * Fecha creaci�n: 06/11/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
public class UtilsSeguridad {


	public static void main(String[] args) {
		try {
			String textoEncrip = new CriptPassword().encripta("opnet");
			System.out.println(textoEncrip);
			String des = new CriptPassword().desencripta(textoEncrip);
			System.out.println(des);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
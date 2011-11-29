package uy.com.s4b.inside.test;

import uy.com.s4b.inside.core.common.CriptPassword;



public class UtilsSeguridad {


	public static void main(String[] args) {
		try {
			String textoEncrip = new CriptPassword().encripta("inside");
			System.out.println(textoEncrip);
			String des = new CriptPassword().desencripta(textoEncrip);
			System.out.println(des);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}



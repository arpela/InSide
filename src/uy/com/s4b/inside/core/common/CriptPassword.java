package uy.com.s4b.inside.core.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: CriptPassword.java <br>
 * Description: Realiza la encriptacion de la password cuando esta siendo guarda<br>
 * Fecha creaci�n: 18/10/2008 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
public class CriptPassword {

	/**
	 * Retona la password encriptada para ser guarda en la base de datos.
	 * @param key
	 * @return
	 * @throws InSideException 
	 * @throws NoSuchAlgorithmException
	 */
	public String getHashSH1(String key) throws InSideException  {
		MessageDigest md;
		String retorno = "";
		byte[] buffer = key.getBytes();
		try {
			md = MessageDigest.getInstance("SHA1");
			md.update(buffer);
			byte[] digest = md.digest();
			for (byte aux : digest) {
				int b = aux & 0xff;
				if (Integer.toHexString(b).length() == 1){
					retorno += "0";
				}
				retorno += Integer.toHexString(b);
			}
			return retorno;
		} catch (NoSuchAlgorithmException ex) {
			throw new InSideException("generalError", ex.getMessage(), ex);
		}
	}
}

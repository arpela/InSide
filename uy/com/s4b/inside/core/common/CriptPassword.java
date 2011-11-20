package uy.com.s4b.inside.core.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
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
	
	private final static String keyBuffer = "26006379";
	private final static String algoritmo = "DES/ECB/PKCS5Padding";
	

	private SecretKeySpec getKey() {
		SecretKeySpec key = new SecretKeySpec(keyBuffer.getBytes(), "DES");
		return key;
	}

	
	public String desencripta(String s) throws Exception {
		Cipher cipher = Cipher.getInstance(algoritmo);
		cipher.init(2, getKey());
		byte retorno[] = cipher.doFinal(new BASE64Decoder().decodeBuffer(s));
		return new String(retorno);
	}

	
	public String encripta(String s) throws Exception {
		SecureRandom securerandom = new SecureRandom();
		securerandom.nextBytes(keyBuffer.getBytes());
		Cipher cipher = Cipher.getInstance(algoritmo);
		cipher.init(1, getKey());
		return new BASE64Encoder().encode(cipher.doFinal(s.getBytes()));
	}
	
	
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

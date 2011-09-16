package uy.com.s4b.inside.core.exception;

import javax.ejb.ApplicationException;

/**
 * Title: InSideException.java <br>
 * Description: <br>
 * Fecha creaci�n: 08/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@ApplicationException(rollback=true)
public class InSideException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2911965558354124871L;
	private String keyMSGError;
	
	/**
	 * 
	 */
	public InSideException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public InSideException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public InSideException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InSideException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	public InSideException(String key, String message, Throwable cause) {
		super(message, cause);
		this.setKeyMSGError(key);
	}

	/**
	 * @return the keyMSGError
	 */
	public String getKeyMSGError() {
		return keyMSGError;
	}

	/**
	 * @param keyMSGError the keyMSGError to set
	 */
	public void setKeyMSGError(String keyMSGError) {
		this.keyMSGError = keyMSGError;
	}

}

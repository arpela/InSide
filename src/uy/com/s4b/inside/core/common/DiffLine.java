package uy.com.s4b.inside.core.common;

/**
 * 
 * Title: DiffLine.java <br>
 * Description: <br>
 * Fecha creaci�n: 21/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */
public enum DiffLine {
	NEW("#88b119"), CHANGE("#ffc81f"), DELETE("#c81f24"), MOVE("#ffc81f"), ERROR("#c81f24");
	
	private final String value;
	
	/**
	 * 
	 */
	private DiffLine(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	
}

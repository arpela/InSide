package uy.com.s4b.inside.core.ejbs.event;

import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: SysLogService.java <br>
 * Description: <br>
 * Fecha creaci�n: 29/10/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

public interface SysLogService {

	/**
	 * Realiza el parce del msg y lo persiste.
	 * @param hostAddress 
	 * @throws InSideException
	 */
	void parserAndSaveSysLog(String msg, String hostAddress) throws InSideException;
	
	
}

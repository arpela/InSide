package uy.com.s4b.inside.core.ejbs.ignoreline;

import java.util.List;

import uy.com.s4b.inside.core.entity.IgnoreLine;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: IgnoreLineService.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 20/11/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */

public interface IgnoreLineService {
	
	/**
	 * Retorna la lista de IgnoreLine que se encuentran en el sistema.
	 * @return
	 * @throws InSideException
	 */
	public List<IgnoreLine> getAllIgnoreLine() throws InSideException;
	
	
	/**
	 * Retorna la lista de values de IgnoreLine que se encuentran en el sistema.
	 * @return
	 * @throws InSideException
	 */
	public List<String> getAllIgnoreLineValue() throws InSideException;

}

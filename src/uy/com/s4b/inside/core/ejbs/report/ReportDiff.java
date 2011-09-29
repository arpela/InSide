package uy.com.s4b.inside.core.ejbs.report;

import java.util.Map;

import uy.com.s4b.inside.core.common.DiffLine;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: ReportDiff.java <br>
 * Description: <br>
 * Fecha creación: 25/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

public interface ReportDiff {

	/**
	 * 
	 * @param oldString
	 * @param newString
	 * @return
	 * @throws InSideException
	 */
	Map<Integer, DiffLine> doDiff(String oldString, String newString) throws InSideException;

}

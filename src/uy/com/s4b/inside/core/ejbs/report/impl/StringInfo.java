package uy.com.s4b.inside.core.ejbs.report.impl;


/**
 * Title: stringInfo.java <br>
 * Description: <br>
 * Fecha creación: 25/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

public class StringInfo {

	static final int MAXLINECOUNT = 30000;

	public int maxLine; /* After input done, # lines in file. */
	Node symbol[]; /* The symtab handle of each line. */
	int other[]; /* Map of line# to line# in other string */

	/* ( -1 means don't-know ). */
	/* Allocated AFTER the lines are read. */

	StringInfo(String filename) {
		symbol = new Node[MAXLINECOUNT + 2];
		other = null; // allocated later!
	}

	// This is done late, to be same size as # lines in input string.
	void alloc() {
		other = new int[symbol.length + 2];
	}
	
}

package uy.com.s4b.inside.core.ejbs.report.impl;


/**
 * Title: Node.java <br>
 * Description: <br>
 * Fecha creación: 25/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

public class Node {

	Node pleft, pright;
	int linenum;

	static final int freshnode = 0, oldonce = 1, newonce = 2, bothonce = 3,
			other = 4;

	int /* enum linestates */linestate;
	String line;

	static Node panchor = null; /* symtab is a tree hung from this */

	/**
	 * Construct a new symbol table node and fill in its fields.
	 * 
	 * @param string
	 *            A line of the text
	 */
	Node(String pline) {
		pleft = pright = null;
		linestate = freshnode;
		/* linenum field is not always valid */
		line = pline;
	}

	/**
	 * matchsymbol Searches tree for a match to the line.
	 * 
	 * @param String
	 *            pline, a line of text If node's linestate == freshnode, then
	 *            created the node.
	 */
	static Node matchsymbol(String pline) {
		int comparison;
		Node pnode = panchor;
		if (panchor == null)
			return panchor = new Node(pline);
		for (;;) {
			comparison = pnode.line.compareTo(pline);
			if (comparison == 0)
				return pnode; /* found */

			if (comparison < 0) {
				if (pnode.pleft == null) {
					pnode.pleft = new Node(pline);
					return pnode.pleft;
				}
				pnode = pnode.pleft;
			}
			if (comparison > 0) {
				if (pnode.pright == null) {
					pnode.pright = new Node(pline);
					return pnode.pright;
				}
				pnode = pnode.pright;
			}
		}
		/* NOTE: There are return stmts, so control does not get here. */
	}

	/**
	 * addSymbol(String pline) - Saves line into the symbol table. Returns a
	 * handle to the symtab entry for that unique line. If inoldfile nonzero,
	 * then linenum is remembered.
	 */
	static Node addSymbol(String pline, boolean inoldfile, int linenum) {
		Node pnode;
		pnode = matchsymbol(pline); /* find the node in the tree */
		if (pnode.linestate == freshnode) {
			pnode.linestate = inoldfile ? oldonce : newonce;
		} else {
			if ((pnode.linestate == oldonce && !inoldfile)
					|| (pnode.linestate == newonce && inoldfile))
				pnode.linestate = bothonce;
			else
				pnode.linestate = other;
		}
		if (inoldfile)
			pnode.linenum = linenum;
		return pnode;
	}

	/**
	 * symbolIsUnique Arg is a ptr previously returned by addSymbol.
	 * -------------- Returns true if the line was added to the symbol table
	 * exactly once with inoldstring true, and exactly once with inoldstring
	 * false.
	 */
	boolean symbolIsUnique() {
		return (linestate == bothonce);
	}

	/**
	 * showSymbol Prints the line to stdout.
	 */
	void showSymbol() {
		System.out.println(line);
	}
}

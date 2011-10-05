package uy.com.s4b.inside.core.ejbs.report.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import uy.com.s4b.inside.core.ejbs.report.EJBReportDiffLocal;
import uy.com.s4b.inside.core.ejbs.report.EJBReportDiffRemote;
import uy.com.s4b.inside.core.ejbs.report.ReportDiff;
import uy.com.s4b.inside.core.ejbs.report.impl.difflib.Delta;
import uy.com.s4b.inside.core.ejbs.report.impl.difflib.DiffUtils;
import uy.com.s4b.inside.core.ejbs.report.impl.difflib.Patch;
import uy.com.s4b.inside.core.exception.InSideException;

/** This is the info kept per-string. */

/**
 * Title: Diff.java <br>
 * Description: <br>
 * Fecha creación: 20/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * 
 * @author Pablo
 * 
 */

@Stateless
@Remote(EJBReportDiffRemote.class)
@Local(EJBReportDiffLocal.class)
@RemoteBinding(jndiBinding = "inSide/EJBReportDiff/remote")
@LocalBinding(jndiBinding = "inSide/EJBReportDiff/local")
public class Diff implements ReportDiff {

	public Diff() {

	}

	/** Do one file comparison. Called with both filenames. */

	public Map<Integer, Delta> doDiff(String oldString, String newString)
			throws InSideException {

		Patch patch = DiffUtils.diff(Arrays.asList(oldString.split("\n")),
				Arrays.asList(newString.split("\n")));
		
		Map<Integer, Delta> resultMapPatch = new HashMap<Integer, Delta>();
		for (Delta delta: patch.getDeltas()) {
			resultMapPatch.put(delta.getOriginal().getPosition(), delta); 	
		
		}
		return resultMapPatch;

	}

}
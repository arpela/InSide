package uy.com.s4b.inside.test;

import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import uy.com.s4b.inside.core.common.DiffLine;
import uy.com.s4b.inside.core.ejbs.report.EJBReportDiffRemote;
import uy.com.s4b.inside.core.exception.InSideException;


/**
 * Title: Diff.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 20/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * 
 * @author Pablo
 * 
 */

public class TestDiff {

	
	/**
	 * main - entry point when used standalone. NOTE: no routines return error
	 * codes or throw any local exceptions. Instead, any routine may complain to
	 * stderr and then exit with error to the system.
	 * 
	 * @throws InSideException
	 */
	public static void main(String argstrings[]) {
		String serverip = "10.1.1.2:1099"; 
	
		
		String multiLine1 = "L1\nL2\nL3\nL4\nL5";
		String multiLine2 = "L1\nL2\nL3\nL4\nL5";
		
		try {
			Properties retorno = System.getProperties();
			retorno.setProperty(Context.PROVIDER_URL, "jnp://" + serverip);
			retorno.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			retorno.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			Context ctx = new InitialContext(retorno);
			
			EJBReportDiffRemote service =  (EJBReportDiffRemote)
				PortableRemoteObject.narrow(ctx.lookup("inSide/EJBReportDiff/remote"), EJBReportDiffRemote.class);
		
			Map<Integer, DiffLine> resDiffLine = service.doDiff(multiLine1, multiLine2);

			switch (resDiffLine.get(4)) {
				case CHANGE:
					System.out.println("CHANGE");
					break;
	
				case DELETE:
					System.out.println("DELETE");
					break;
	
				case NEW:
					System.out.println("NEW");
					break;
	
				case MOVE:
					System.out.println("MOVE");
					break;
	
				default:
					System.out.println("ERROR");
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

package uy.com.s4b.inside.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import uy.com.s4b.inside.core.common.TypeAction;
import uy.com.s4b.inside.core.common.TypeConfig;
import uy.com.s4b.inside.core.ejbs.event.EJBEventInSideRemote;
import uy.com.s4b.inside.core.ejbs.version.EJBVersionRemote;
import uy.com.s4b.inside.core.entity.Action;
import uy.com.s4b.inside.core.entity.Device;
import uy.com.s4b.inside.core.entity.MsgSysLog;
import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * @author pablo
 *
 */
public class TestMSGSysLog {

	/**
	 * 
	 */
	public TestMSGSysLog() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Properties retorno = System.getProperties(); 
			String serverip = "10.1.1.2:1099"; 
			retorno.setProperty(Context.PROVIDER_URL, "jnp://" + serverip);
			retorno.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			retorno.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			Context ctx = new InitialContext(retorno);
			
			EJBEventInSideRemote service =  (EJBEventInSideRemote)
						PortableRemoteObject.narrow(ctx.lookup("inSide/EJBEventInSide/remote"), EJBEventInSideRemote.class);
			
			service.saveMsgSysLog(getMsgSysLog());
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (InSideException ex) {
			ex.printStackTrace();
		}
	}
	

	/**
	 * @return
	 */
	private static MsgSysLog getMsgSysLog() {
		Action a = new Action();
//		a.setId();
		a.setName(TypeAction.SAVE_EVENT);
		MsgSysLog retorno = new MsgSysLog ();
		retorno.setIdSysLog("SYS-6-CLOCKUPDATE");
		retorno.setAction(a);
		return retorno;
	}
}
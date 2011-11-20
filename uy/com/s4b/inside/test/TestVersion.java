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

import uy.com.s4b.inside.core.common.TypeConfig;
import uy.com.s4b.inside.core.ejbs.version.EJBVersionRemote;
import uy.com.s4b.inside.core.entity.Device;
import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * @author pablo
 *
 */
public class TestVersion {

	/**
	 * 
	 */
	public TestVersion() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		
		try {
			Properties retorno = System.getProperties(); 
			String serverip = "localhost:1099"; 
			retorno.setProperty(Context.PROVIDER_URL, "jnp://" + serverip);
			retorno.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			retorno.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			Context ctx = new InitialContext(retorno);
			
			EJBVersionRemote service =  (EJBVersionRemote)
						PortableRemoteObject.narrow(ctx.lookup("inSide/EJBVersion/remote"), EJBVersionRemote.class);
			
			service.save(getVersion());
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (InSideException ex) {
			ex.printStackTrace();
		}
	}
	

	/**
	 * @return
	 */
	private static Version getVersion() {
		Version retorno = new Version();
		Device oneDevice = new Device();
		oneDevice.setId(1);

		
		retorno.setConfig(getFile());
		retorno.setDate(Calendar.getInstance());
		retorno.setOneDevice(oneDevice);
		retorno.setType(TypeConfig.Running);
		
		return retorno;
	}

	/**
	 * @return
	 */
	private static String getFile() {
		StringBuffer retorno = new StringBuffer();
		try {
			BufferedReader r = new BufferedReader(new FileReader(new File("/home/pablo/Documentos/InSide/archivos config/running-router-01172011.cfg")));
			String linea = r.readLine();
			while (linea != null){
				retorno.append(linea);
				retorno.append("\n");
				linea = r.readLine();
			}
			r.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return retorno.toString();
	}
}
/**
 * 
 */
package uy.com.s4b.inside.test;

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import uy.com.s4b.inside.core.ejbs.manager.EJBManagerUserRemote;
import uy.com.s4b.inside.core.entity.Profile;
import uy.com.s4b.inside.core.entity.User;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * @author pablo
 *
 */
public class TestProvisorio {

	/**
	 * 
	 */
	public TestProvisorio() {
		
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
			
			EJBManagerUserRemote service =  (EJBManagerUserRemote)
						PortableRemoteObject.narrow(ctx.lookup("inSide/EJBManagerUser/remote"), EJBManagerUserRemote.class);
			
			service.saveUser(getUser(0));
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (InSideException ex) {
			ex.printStackTrace();
		}
	}
	

	private static User getUser(Integer idNick){
		User retorno = new User();
		retorno.setName("administrador");
		retorno.setNick("admin" + idNick);
		retorno.setPass("123456");
		retorno.setSurname("administrador");
		retorno.setMyProfiles(getProliles());
		return retorno;
	}

	private static Set<Profile> getProliles() {
		Profile p1 = new Profile();
		p1.setDescripcion("Perfil de administrador");
		p1.setName("Admin");
		
		Profile p2 = new Profile();
		p2.setDescripcion("Perfil para ver reportes");
		p2.setName("Reportes");
		
		Set<Profile> retorno = new TreeSet<Profile>();
		retorno.add(p1);
		retorno.add(p2);
		
		return retorno;
	}
}

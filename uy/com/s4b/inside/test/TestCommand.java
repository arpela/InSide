package uy.com.s4b.inside.test;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import uy.com.s4b.inside.core.common.TypeCommand;
import uy.com.s4b.inside.core.ejbs.command.EJBCommandRemote;
import uy.com.s4b.inside.core.entity.Command;
import uy.com.s4b.inside.core.entity.Ios;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * @author pablo
 *
 */
public class TestCommand {

	/**
	 * 
	 */
	public TestCommand() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		
		try {
			Properties retorno = System.getProperties(); 
			String serverip = "10.1.1.2:1099"; 
			retorno.setProperty(Context.PROVIDER_URL, "jnp://" + serverip);
			retorno.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			retorno.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			Context ctx = new InitialContext(retorno);
			
			EJBCommandRemote service =  (EJBCommandRemote) PortableRemoteObject.narrow
					(ctx.lookup("inSide/EJBCommandBean/remote"), EJBCommandRemote.class);
			
			Ios i = new Ios();
			i.setId(1);
			i.setName("emulador");
			
			System.out.println("configuracion para start up");
			List<Command> l = service.getListCommandForIosAndType(i, TypeCommand.GET_CONFIG_STARTUP);
			
			for (Command command : l) {
				System.out.println(command.getValue());
			}
			
			System.out.println("configuracion para running");
			l = service.getListCommandForIosAndType(i, TypeCommand.GET_CONFIG_RUNNING);
			
			for (Command command : l) {
				System.out.println(command.getValue());
			}
			
//			Command c =  service.getCommand(3);

//			GenericCommand g = new GenericCommand();
//			g.setId(2);
//			g.setTypeCommand(TypeCommand.GET_CONFIG_STARTUP);
//
//			Command c = new Command();
//			c.setCommandGeneric(g);
//			c.setOrden(1);
//			c.setValue("enable");
//			service.save(c);
//			c = new Command();
//			c.setCommandGeneric(g);
//			c.setOrden(2);
//			c.setValue("copy startup-config");
//			service.save(c);
//
//			c = new Command();
//			c.setCommandGeneric(g);
//			c.setOrden(3);
//			c.setValue("\n");
//			service.save(c);
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (InSideException ex) {
			ex.printStackTrace();
		}
	}
}
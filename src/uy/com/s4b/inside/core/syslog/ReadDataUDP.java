package uy.com.s4b.inside.core.syslog;

import java.net.DatagramPacket;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.ejbs.event.EJBSysLogLocal;
import uy.com.s4b.inside.core.exception.InSideException;


/**
 * Title: ConexionUdp.java <br>
 * Description: <br>
 * Fecha creación: 07/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

/**
 * 
 * Title:  <br>
 * Description: <br>
 * Fecha creación: 28/07/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author 
 *
 */
class ReadDataUDP extends Thread {
	

	private static final Logger log = Logger.getLogger(ReadDataUDP.class);
	
	DatagramPacket paquete;

	ReadDataUDP(DatagramPacket paquete) {
		this.paquete = paquete;
		setPriority(MAX_PRIORITY - 1);
		start();
	}

	
	public void run() {
		log.info("Lanzado el hilo de lectura de UDP, llega msg de Syslog !!! ");
		String msgRecibido = new String(paquete.getData());
		
		log.debug("1 ------------------>  " + msgRecibido);
		log.debug("2 ------------------>  " + InfoRunServerUDP.MSG_BAJA_SERVER);
		
		if (msgRecibido.trim().equals(InfoRunServerUDP.MSG_BAJA_SERVER)){
			log.warn("MSG de bajada de servidor!!!!");
			log.warn("----> " + msgRecibido);
		}else{
			
			try {
				String hostAddress = paquete.getAddress().getHostAddress();
				log.debug("Info del equipo ---> " + hostAddress);
				Context ctx = new InitialContext();
				EJBSysLogLocal localSYS = (EJBSysLogLocal)ctx.lookup("inSide/EJBSysLog/local");
				localSYS.parserAndSaveSysLog(msgRecibido.trim(), hostAddress);
			} catch (NamingException ex) {
				log.error("No se procesa msg de sys log: " + msgRecibido);
				log.error(ex.getMessage(), ex);
			} catch (InSideException ex) {
				log.error("Errores al procesar el msg de syslog");
				log.error(ex.getMessage(), ex);
			}
		}
	}
	
}
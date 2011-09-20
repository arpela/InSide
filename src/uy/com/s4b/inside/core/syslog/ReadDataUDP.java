package uy.com.s4b.inside.core.syslog;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;


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
		log.debug("Lanzado el hilo de lectura de UDP...");
		String msgRecibido = new String(paquete.getData());
		if (InfoRunServerUDP.MSG_BAJA_SERVER.equalsIgnoreCase(msgRecibido)){
			log.warn("MSG de bajada de servidor!!!!");
			log.warn("----> " + msgRecibido);
		}else{
			log.info("Msg recibido --> " + msgRecibido);
		}
	}
	
}
package uy.com.s4b.inside.core.syslog;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Title:  <br>
 * Description: <br>
 * Fecha creación: 28/07/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author arpela
 *
 */

public class ServerUDP extends Thread {
	
	private static final Log log = LogFactory.getLog(ServerUDP.class);
	
	private int puerto;
	private boolean runinng = true;
	
	public ServerUDP(String nombre, int puerto, ThreadGroup threadGroup) {
		super(threadGroup, nombre);
		this.puerto = puerto;
	}

	
	public void run() {
		try {
			InetAddress host = InetAddress.getLocalHost();
			DatagramSocket socketDgrama = new DatagramSocket(puerto, host);
			
			String ip = new String (host.getHostAddress());
			
			log.warn("Servidor SysLog Levanto!!! ");
			log.warn("Puerto: " + puerto);
			log.warn("IP: " + ip);
			
			while (isRuninng()) {
				// Limitamos la cadena a 99 bytes
				DatagramPacket paquete = new DatagramPacket(new byte[200], 200);
				
				socketDgrama.receive(paquete);
				new ReadDataUDP(paquete);
			}
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * @return the sigoCorriendo
	 */
	public boolean isRuninng() {
		return runinng;
	}
	
	
	/**
	 * @param sigoCorriendo the sigoCorriendo to set
	 */
	public void setRuninng(boolean runinng) {
		this.runinng = runinng;
	}
	
}

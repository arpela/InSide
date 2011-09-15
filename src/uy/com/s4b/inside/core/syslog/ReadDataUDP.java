package uy.com.s4b.inside.core.syslog;

import java.net.DatagramPacket;

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
	
	DatagramPacket paquete;

	ReadDataUDP(DatagramPacket paquete) {
		System.out.println("Recibida una llamada UDP");
		this.paquete = paquete;
		setPriority(MAX_PRIORITY - 1);
		start();
	}

	public void run() {
		System.out.println("Lanzado el hilo UDP...");
		System.out.println("Dato recibido: ----> " + new String(paquete.getData()));
	}
	
}
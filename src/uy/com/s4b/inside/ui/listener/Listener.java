package uy.com.s4b.inside.ui.listener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uy.com.s4b.inside.core.syslog.InfoRunServerUDP;
import uy.com.s4b.inside.core.syslog.ServerUDP;


/**
 * Title: Listener.java <br>
 * Description: <br>
 * Fecha creación: 06/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * 
 * @author Alfredo
 * 
 */

public class Listener implements ServletContextListener {

	private static final Log log = LogFactory.getLog(Listener.class);
	private static final String nombreThread = "Server UDP";
	
	public void contextInitialized(ServletContextEvent event) {
		log.info("##################################");
		log.info("Arranco la app!!!");
		ServerUDP udp = new ServerUDP(nombreThread, InfoRunServerUDP.PUERTO_SERVIDOR_UDP, Thread.currentThread().getThreadGroup());
		udp.start();
		log.info("##################################");
	}
	

	public void contextDestroyed(ServletContextEvent e) {
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		int numThreads = currentGroup.activeCount();
		Thread[] listOfThreads = new Thread[numThreads];
		
		try {
			currentGroup.enumerate(listOfThreads);
			for (int i = 0; i < numThreads; i++){
				if (listOfThreads[i].getName().equalsIgnoreCase(nombreThread)){
					ServerUDP esta = (ServerUDP)listOfThreads[i];
					esta.setRuninng(false);
					byte msg [] = InfoRunServerUDP.MSG_BAJA_SERVER.getBytes();
					DatagramPacket p = new DatagramPacket(msg, msg.length, InetAddress.getByName(InfoRunServerUDP.IP_SERVIDOR), 
							InfoRunServerUDP.PUERTO_SERVIDOR_UDP);
					DatagramSocket soket = new DatagramSocket();
					soket.send(p);
					soket.close();
				}
			}
			
		} catch (NullPointerException ex) {			
			log.error("Problemas en la bajada: " + ex.getMessage());
		} catch (UnknownHostException ex) {
			log.error("Problemas en la bajada: " + ex.getMessage());
		} catch (SocketException ex) {
			log.error("Problemas en la bajada: " + ex.getMessage());
		} catch (IOException ex) {
			log.error("Problemas en la bajada: " + ex.getMessage());
		}
	}

}

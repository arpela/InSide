package uy.com.s4b.inside.ui.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
		ServerUDP udp = new ServerUDP(nombreThread, 514, Thread.currentThread().getThreadGroup());
		udp.start();
		log.info("##################################");
	}
	

	public void contextDestroyed(ServletContextEvent e) {
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		int numThreads = currentGroup.activeCount();
		Thread[] listOfThreads = new Thread[numThreads];

		currentGroup.enumerate(listOfThreads);
		for (int i = 0; i < numThreads; i++){
			log.info("Thread #" + i + " = " + listOfThreads[i].getName());
			if (listOfThreads[i].getName().equalsIgnoreCase(nombreThread)){
				Thread esta = listOfThreads[i];
				esta.interrupt();
			}
		}
		
		
	}

}

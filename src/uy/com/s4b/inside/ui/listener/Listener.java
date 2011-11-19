package uy.com.s4b.inside.ui.listener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.StdScheduler;

import uy.com.s4b.inside.core.common.NamesJobsQuartz;
import uy.com.s4b.inside.core.quartz.GetConfigForDayJob;
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
		starQuarzReadConfig();
		log.info("##################################");
	}
	
	
	private void starQuarzReadConfig(){
		log.info("Se agenda Job configuracion Diaria....");
		try {
			InitialContext ctx = new InitialContext();
			StdScheduler scheduler = (StdScheduler) ctx.lookup("Quartz");
			JobDetail jd =new JobDetail(NamesJobsQuartz.READ_CONFI_DAY.name(),Scheduler.DEFAULT_GROUP, GetConfigForDayJob.class);
			jd.setDurability(false);
			jd.setVolatility(true);
			CronTrigger ct = new CronTrigger("configday","group1","0 0 23 * * ?");
//			CronTrigger ct = new CronTrigger("configday","group1","0 0/1 * * * ?");
			scheduler.scheduleJob(jd,ct);
		} catch (NamingException ex) {
			log.error(ex.getMessage(), ex);
		} catch (SchedulerException ex) {
			log.error(ex.getMessage(), ex);
		} catch (ParseException ex) {
			log.error(ex.getMessage(), ex);
		}
		
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
			stopQuarz();
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

	
	private void stopQuarz(){
		log.info("Se ingresa bajar Job configuracion Diaria...");
		try {
			InitialContext ctx = new InitialContext();
			StdScheduler scheduler = (StdScheduler) ctx.lookup("Quartz");
			scheduler.interrupt(NamesJobsQuartz.READ_CONFI_DAY.name(),Scheduler.DEFAULT_GROUP);
			scheduler.deleteJob(NamesJobsQuartz.READ_CONFI_DAY.name(),Scheduler.DEFAULT_GROUP);
		} catch (NamingException ex) {
			ex.printStackTrace();
		} catch (UnableToInterruptJobException ex) {
			ex.printStackTrace();
		} catch (SchedulerException ex) {
			ex.printStackTrace();
		}
	}
	
}

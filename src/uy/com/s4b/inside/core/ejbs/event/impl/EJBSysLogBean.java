package uy.com.s4b.inside.core.ejbs.event.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdScheduler;

import uy.com.s4b.inside.core.common.NamesJobsQuartz;
import uy.com.s4b.inside.core.common.TypeAction;
import uy.com.s4b.inside.core.common.TypeEvent;
import uy.com.s4b.inside.core.common.UtilsDate;
import uy.com.s4b.inside.core.ejbs.device.EJBDeviceLocal;
import uy.com.s4b.inside.core.ejbs.event.EJBEventInSideLocal;
import uy.com.s4b.inside.core.ejbs.event.EJBSysLogLocal;
import uy.com.s4b.inside.core.ejbs.event.SysLogService;
import uy.com.s4b.inside.core.entity.Device;
import uy.com.s4b.inside.core.entity.EventInSide;
import uy.com.s4b.inside.core.entity.MsgSysLog;
import uy.com.s4b.inside.core.entity.SysLog;
import uy.com.s4b.inside.core.exception.InSideException;
import uy.com.s4b.inside.core.quartz.ReadFilesConfigJob;


/**
 * Title: EJBEventInSideBean.java <br>
 * Description: <br>
 * Fecha creaciï¿½n: 20/10/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@Stateless
@Local(EJBSysLogLocal.class)
@LocalBinding(jndiBinding="inSide/EJBSysLog/local")
public class EJBSysLogBean implements SysLogService {
	
	private static final Logger log = Logger.getLogger(EJBSysLogBean.class);
	private static final String ERROR_GRAVE = "ERROR";
	
	@PersistenceContext
	EntityManager em;
	@EJB
	EJBDeviceLocal ejbDevice;
	
	@EJB
	EJBEventInSideLocal ejbEvent;
	
	/**
	 * 
	 */
	public EJBSysLogBean() {
		
	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.event.SysLogService#parserAndSaveSysLog()
	 */
	@Override
	public void parserAndSaveSysLog(String msg, String ipHost) throws InSideException {
		Device device = ejbDevice.getDeviceByIP(ipHost);
		SysLog sysDevice = parcerMSG(msg);
		sysDevice.setOneDevice(device);
		
		String idMSG = getIdMessage(sysDevice.getMessage());
		MsgSysLog msgSYSLog = getMsgSysLog(idMSG);
		log.info("msgSYSLog: " + msgSYSLog);
		log.info("Equipo: " + (device != null?device.getHostname():null));
		
		if (idMSG.trim().equals(ERROR_GRAVE)){
			String subject = "[" + UtilsDate.getTimeStamp() + "] Mensaje de syslog CRITICAL en equipo " + ipHost;
			String descript = msg.substring(0, 244);
			EventInSide event = getEvent(subject, descript, TypeEvent.ERROR, 1);
			ejbEvent.saveEvent(event);
		}else if (device == null) {
			EventInSide event = getEventUnReachableHost(ipHost);
			ejbEvent.saveEvent(event);
		} else {
			if (msgSYSLog == null){
				log.error("Error de configuracion en la app!!!!!!!!!!!!!!!");
				log.error("Error de configuracion en la app!!!!!!!!!!!!!!!");
				log.error("Error de configuracion en la app!!!!!!!!!!!!!!!");
				return;				
			}else {
				// TODO falta finalizar la recepcion de eventos cuando llega un cambio de configuracion.
				
				if (TypeAction.SAVE_EVENT == msgSYSLog.getAction().getName()){
					
					String subjet = msgSYSLog.getValueSubjetEvent().replaceAll("-", ipHost);
					String descript = msgSYSLog.getDescriptionEvent().replaceAll("-", ipHost);
					EventInSide event = getEvent(subjet, descript, msgSYSLog.getTypeEvent(), 1);
					event.setType(msgSYSLog.getTypeEvent());					
					ejbEvent.saveEvent(event);
					
				}else if (TypeAction.GET_CONFIG == msgSYSLog.getAction().getName()){
					String subjet = msgSYSLog.getValueSubjetEvent().replaceAll("-", ipHost);
					String descript = sysDevice.getMessage();
					EventInSide event = getEvent(subjet, descript, msgSYSLog.getTypeEvent(), 1);
					event.setType(msgSYSLog.getTypeEvent());
					save(sysDevice);
					ejbEvent.saveEvent(event);
					
					//Agendo un evento el quartz para que se dispare en 15;
					getConfQuarzReadConfig(ipHost);
				}
			}
		}
	}
	
	/**
	 * @param ipHost
	 */
	private void getConfQuarzReadConfig(String ipHost){
		log.info("Se ingresa agendar read config....");
		try {
			String timer = new SimpleDateFormat("HHmmssS").format(Calendar.getInstance().getTime());
			String nameJob = timer + NamesJobsQuartz.GET_CONFIG.name(); 
			InitialContext ctx = new InitialContext();
			StdScheduler scheduler = (StdScheduler) ctx.lookup("Quartz");
			JobDetail jd =new JobDetail(nameJob,Scheduler.DEFAULT_GROUP, ReadFilesConfigJob.class);
			
			jd.setRequestsRecovery(true);
			jd.getJobDataMap().put("host", ipHost);
			jd.setDurability(false);
			jd.setVolatility(true);
			
//			CronTrigger ct = new CronTrigger("cronTrigger","group1","0 0/10 * * * ?");
			
			// 30 minutos se ejecuta para recupera archivos
			CronTrigger ct = new CronTrigger(timer + ipHost,"readConfigSysLog","0 0/15 * * * ?");
			scheduler.scheduleJob(jd,ct);
			
		} catch (NamingException ex) {
			log.error(ex.getMessage(),ex);
		} catch (ParseException ex) {
			log.error(ex.getMessage(),ex);
		} catch (SchedulerException ex) {
			log.error(ex.getMessage(),ex);
		}
	}
	
	
	/**
	 * @param sysDevice
	 */
	private void save(SysLog sysDevice) {
		em.persist(sysDevice);
	}

	private EventInSide getEvent(String subjec, String descipcion, TypeEvent typeEvent, int cantidaDias) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, +cantidaDias);
		EventInSide event = new EventInSide();
		event.setDeadline(cal);
		event.setDescription("["+ UtilsDate.getTimeStamp() + "] " + descipcion);
		event.setType(typeEvent);
		event.setValue(subjec);
		return event;
	}
	

	/**
	 * @param ipHost
	 * @return
	 */
	private EventInSide getEventUnReachableHost(String ipHost) {
		SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, +2);
		EventInSide event = new EventInSide();
		event.setDeadline(cal);
		//TODO Esto debe ir al property de msg.
		event.setDescription("["+ formatdate.format(Calendar.getInstance().getTime()) +"] El equipo \""+ ipHost + "\" está fuera del inventario del sistema");
		event.setType(TypeEvent.ERROR);
		event.setValue("Equipo fuera de inventario");
		return event;
	}

	/**
	 * @param idMSG
	 * @return
	 */
	private MsgSysLog getMsgSysLog(String idMSG) {
		Query q = em.createNamedQuery("find.msgsyslog.by.name").setParameter("name", idMSG);
		try {
			return (MsgSysLog)q.getSingleResult();			
		}catch (javax.persistence.NoResultException ex) {
			return null;
		}
	}

	/**
	 * @param message
	 * @return
	 */
	private String getIdMessage(String message) {
		String msg = message.substring(message.indexOf("%") + 1);
		msg = msg.substring(0, msg.indexOf(":"));
		log.info("Id message ---> " + msg);
		
		if (msg.startsWith("SYS-2"))
			return ERROR_GRAVE;
		
		if (msg.startsWith("SYS-3"))
			return ERROR_GRAVE;
		
		return msg;
	}

	/**
	 * @param msg
	 * @return
	 * @throws InSideException 
	 */
	private SysLog parcerMSG(String msg) throws InSideException {
		log.info(msg);
		SysLog retorno = new SysLog();
		retorno.setDateMessage(getDate(msg));
		retorno.setDateReceive(Calendar.getInstance());
		retorno.setMessage(msg.trim());
		log.info(ToStringBuilder.reflectionToString(retorno, ToStringStyle.SHORT_PREFIX_STYLE));
		return retorno;
	}

	/**
	 * @param msg
	 * @return
	 * @throws InSideException 
	 */
	private String getDate(String msg) throws InSideException {
		String fecha = getFechaString(msg);
		log.debug("Fecha obtenida -------> " + fecha);
		return fecha;
	}

	/**
	 * @param substring
	 * @return
	 */
	private String getFechaString(String msg) {
//		String fecha = msg.substring(msg.indexOf(">") + 1, msg.indexOf("%") - 1);
//		String primeraChancha [] = fecha.split(":");
//		String retorno = new String();
//		
//		if (primeraChancha.length == 3){
//			retorno = fecha.substring(0, fecha.length()-1);
//		}else if (primeraChancha.length == 5){
//			String tempFecha = new String();
//			for (int i = 2; i < primeraChancha.length; i++) {
//				tempFecha = tempFecha + ":"+ primeraChancha[i];
//			}
//			tempFecha = tempFecha.substring(1, tempFecha.length() - 4);
//			String a [] = tempFecha.trim().split(" ");
//			for (int i = 0; i < a.length; i++) {
//				if (i == 2){
//					retorno = retorno + " " + 2011 + " " + a[i];
//				}else {
//					retorno = retorno + " " + a[i];
//				}
//			}
//		}
		
		return msg.substring(0, 40).trim();
	}

}

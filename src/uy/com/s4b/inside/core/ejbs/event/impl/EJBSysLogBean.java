package uy.com.s4b.inside.core.ejbs.event.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;

import uy.com.s4b.inside.core.common.TypeEvent;
import uy.com.s4b.inside.core.ejbs.device.EJBDeviceLocal;
import uy.com.s4b.inside.core.ejbs.event.EJBEventInSideLocal;
import uy.com.s4b.inside.core.ejbs.event.EJBSysLogLocal;
import uy.com.s4b.inside.core.ejbs.event.SysLogService;
import uy.com.s4b.inside.core.entity.Device;
import uy.com.s4b.inside.core.entity.EventInSide;
import uy.com.s4b.inside.core.entity.MsgSysLog;
import uy.com.s4b.inside.core.entity.SysLog;
import uy.com.s4b.inside.core.exception.InSideException;


/**
 * Title: EJBEventInSideBean.java <br>
 * Description: <br>
 * Fecha creación: 20/10/2011 <br>
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
		SysLog sysDevice = parcerMSG(msg);
		
		//TODO esta faltando corroborar que hacer con el msg, leer lso msg de la base, fijarnos la accion y agendar, evento, descartar.
		String idMSG = getIdMessage(sysDevice.getMessage());
		MsgSysLog msgSYSLog = getMsgSysLog(idMSG);
		log.info("msgSYSLog: " + msgSYSLog);
		Device device = ejbDevice.getDeviceByIP(ipHost);
		log.info("Equipo: " + (device != null?device.getHostname():null));
		
		if (device == null) {
			EventInSide event = getEvent(ipHost);
			ejbEvent.saveEvent(event);
		} else {
			//TODO agento un evento el quartz para que se dispare en 30;
		}
		
	}

	/**
	 * @param ipHost
	 * @return
	 */
	private EventInSide getEvent(String ipHost) {
		SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, +30);
		EventInSide event = new EventInSide();
		event.setDeadline(cal);
		//TODO Esto debe ir al property de msg.
		event.setDescription("["+ formatdate.format(Calendar.getInstance().getTime()) +"] El equipo no se encuentra en la base de InSide");
		event.setType(TypeEvent.ERROR);
		event.setValue("\""+ ipHost +"\" no lolizado");
		return event;
	}

	/**
	 * @param idMSG
	 * @return
	 */
	private MsgSysLog getMsgSysLog(String idMSG) {
		Query q = em.createNamedQuery("find.msgsyslog.by.name").setParameter("name", idMSG);
		return (MsgSysLog)q.getSingleResult();
	}

	/**
	 * @param message
	 * @return
	 */
	private String getIdMessage(String message) {
		String msg = message.substring(message.indexOf("%") + 1);
		msg = msg.substring(0, msg.indexOf(":"));
		log.info("Id message ---> " + msg);
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
	private Calendar getDate(String msg) throws InSideException {
		try {
			String fecha = getFechaString(msg);
			log.debug("Fecha obtenida -------> " + fecha);
			Date d = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS").parse(fecha.trim());
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			cal.setTime(d);
			cal.set(Calendar.YEAR, year);
			log.debug("Fecha Resultante -------> " + new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss:SS").format(cal.getTime()));
			return cal;
		} catch (ParseException ex) {
			log.error(ex.getMessage(), ex);
			throw new InSideException(ex.getMessage(), ex);
		}
		
	}

	/**
	 * @param substring
	 * @return
	 */
	private String getFechaString(String msg) {
		String fecha = msg.substring(msg.indexOf(">") + 1, msg.indexOf("%") - 1);
		String primeraChancha [] = fecha.split(":");
		String retorno = new String();
		
		if (primeraChancha.length == 3){
			retorno = fecha.substring(0, fecha.length()-1);
		}else if (primeraChancha.length == 5){
			String tempFecha = new String();
			for (int i = 2; i < primeraChancha.length; i++) {
				tempFecha = tempFecha + ":"+ primeraChancha[i];
			}
			tempFecha = tempFecha.substring(1, tempFecha.length() - 4);
			String a [] = tempFecha.trim().split(" ");
			for (int i = 0; i < a.length; i++) {
				if (i == 2){
					retorno = retorno + " " + 2011 + " " + a[i];
				}else {					
					retorno = retorno + " " + a[i];
				}
			}
		}
		return retorno.trim();
	}

}

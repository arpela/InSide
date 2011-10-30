package uy.com.s4b.inside.core.ejbs.event.impl;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import uy.com.s4b.inside.core.ejbs.event.EJBEventInSideLocal;
import uy.com.s4b.inside.core.ejbs.event.EJBEventInSideRemote;
import uy.com.s4b.inside.core.ejbs.event.EventInSideService;
import uy.com.s4b.inside.core.entity.Action;
import uy.com.s4b.inside.core.entity.EventInSide;
import uy.com.s4b.inside.core.entity.MsgSysLog;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: EJBEventInSideBean.java <br>
 * Description: <br>
 * Fecha creación: 25/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@Stateless
@Remote(EJBEventInSideRemote.class)
@Local(EJBEventInSideLocal.class)
@LocalBinding(jndiBinding="inSide/EJBEventInSide/local")
@RemoteBinding(jndiBinding="inSide/EJBEventInSide/remote")
public class EJBEventInSideBean implements EventInSideService {

	@PersistenceContext
	EntityManager em;
	
	/**
	 * 
	 */
	public EJBEventInSideBean() {
		
	}
	

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.event.EventInSideService#listEventEnable()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EventInSide> listEventEnable() throws InSideException {
		Query q = em.createNamedQuery("findAllActiv");
		q.setParameter("pFechaFin", Calendar.getInstance());
		return q.getResultList();
	}
	
	/*
	 * (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.event.EventInSideService#saveEvent(uy.com.s4b.inside.core.entity.EventInSide)
	 */
	public void saveEvent(EventInSide event) throws InSideException {
		em.persist(event);
	}


	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.event.EventInSideService#saveMsgSysLog(uy.com.s4b.inside.core.entity.MsgSysLog)
	 */
	@Override
	public void saveMsgSysLog(MsgSysLog msgSysLog) throws InSideException {
		if (!em.contains(msgSysLog.getAction())){
			msgSysLog.setAction(em.find(Action.class, msgSysLog.getAction().getId()));
		}
		em.merge(msgSysLog);
	}
	
}

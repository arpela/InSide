package uy.com.s4b.inside.ui.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.Context;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.ejbs.event.EJBEventInSideLocal;
import uy.com.s4b.inside.core.entity.EventInSide;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: EvenBakingBean.java <br>
 * Description: <br>
 * Fecha creación: 25/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@ManagedBean(name="events")
@RequestScoped
public class EventBakingBean {
	
	
	private static final Logger log = Logger.getLogger(EventBakingBean.class);
	
	@EJB
	private EJBEventInSideLocal ejbEvents; 
	
	private List<EventInSide> listaEventos;
	
	/**
	 * 
	 */
	public EventBakingBean() {
		
	}

	/**
	 * @return the listaEventos
	 */
	public List<EventInSide> getListaEventos() {
		try {
			this.listaEventos = ejbEvents.listEventEnable();
		} catch (InSideException ex) {
			log.error(ex.getMessage(), ex);
		}
		return listaEventos;
	}

	/**
	 * @param listaEventos the listaEventos to set
	 */
	public void setListaEventos(List<EventInSide> listaEventos) {
		this.listaEventos = listaEventos;
	}
	
}

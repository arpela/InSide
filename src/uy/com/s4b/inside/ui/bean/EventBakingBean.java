package uy.com.s4b.inside.ui.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.common.TypeEvent;
import uy.com.s4b.inside.core.ejbs.event.EJBEventInSideLocal;
import uy.com.s4b.inside.core.entity.EventInSide;
import uy.com.s4b.inside.core.exception.InSideException;
import uy.com.s4b.inside.ui.bean.filter.UtilCreatePage;

/**
 * Title: EvenBakingBean.java <br>
 * Description: <br>
 * Fecha creaci�n: 25/09/2011 <br>
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
	
	private String homeListaEvento;
	
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


	public String getHomeListaEvento(){
		StringBuffer retorno = new StringBuffer();
		try {
			int maximo = 12;
			
			List<EventInSide> listaEventos = ejbEvents.listEventEnable(6, TypeEvent.ERROR);
			
			listaEventos.addAll(ejbEvents.listEventEnable(3, TypeEvent.WARN));
			maximo = maximo - listaEventos.size();
			
			listaEventos.addAll(ejbEvents.listEventEnable(maximo, TypeEvent.INFO));
			
//			List<EventInSide> listaAcotada = null;
//			
//			if(listaEventos.size() > 12){				
//				listaAcotada = listaEventos.subList(0, 13);
//			}else{				
//				listaAcotada = listaEventos;
//			}
			
			new UtilCreatePage().createHomeListaEvento(retorno, listaEventos);
		} catch (InSideException ex) {
			retorno = new StringBuffer();
			retorno.append("<tr><td colspan=\"4\" class=\"networks\">");
			retorno.append("Problemas al cargar los eventos");
			retorno.append("</td></tr>");
		}
		homeListaEvento =  retorno.toString();
		return homeListaEvento;
	}
	
	
	
	/**
	 * @param listaEventos the listaEventos to set
	 */
	public void setListaEventos(List<EventInSide> listaEventos) {
		this.listaEventos = listaEventos;
	}
	
}

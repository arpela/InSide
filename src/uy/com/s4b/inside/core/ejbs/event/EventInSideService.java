package uy.com.s4b.inside.core.ejbs.event;

import java.util.List;

import uy.com.s4b.inside.core.entity.EventInSide;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * 
 * Title: EJBNetworkLocal.java <br>
 * Description: <br>
 * Fecha creación: 21/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
public interface EventInSideService {

	
	/**
	 * Retorna la lista de Eventos activos en el sistema
	 * @return
	 * @throws InSideException
	 */
	public List<EventInSide> listEventEnable() throws InSideException;

	
	/**
	 * Metodo encargado de salvar los eventos recibido.
	 * @param event
	 * @throws InSideException
	 */
	public void saveEvent(EventInSide event) throws InSideException;

}

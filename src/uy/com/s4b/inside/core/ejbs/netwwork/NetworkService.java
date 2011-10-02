package uy.com.s4b.inside.core.ejbs.netwwork;

import java.util.List;

import uy.com.s4b.inside.core.entity.Network;
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
public interface NetworkService {

	
	/**
	 * Retorna la lista de Netwoks que se encuentran en el sistema.
	 * @return
	 * @throws InSideException
	 */
	public List<Network> listNetwork() throws InSideException;

	
	/**
	 * @param nameDivice
	 * @return
	 */
	List<Network> listNetworkByNameDevice(String nameDivice) throws InSideException;
	
	/**
	 * @param nameDivice
	 * @return
	 * @throws InSideException
	 */
	List<Network> listNetworkByNetwork(String nameDivice) throws InSideException;

	/**
	 * @param nameSite
	 * @return
	 * @throws InSideException
	 */
	List<Network> listNetworkBySite(String nameSite) throws InSideException;

	
	/**
	 * @param nameZone
	 * @return
	 */
	List<Network> listNetworkByZone(String nameZone)throws InSideException;
}

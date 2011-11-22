package uy.com.s4b.inside.core.ejbs.version;

import java.util.Calendar;
import java.util.List;

import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * 
 * Title: EJBNetworkLocal.java <br>
 * Description: <br>
 * Fecha creaci�n: 21/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
public interface VersionService {

	
	/**
	 * @param id
	 * @return
	 * @throws InSideException
	 */
	Version getVersion(Integer id) throws InSideException;


	/**
	 * 
	 * @param idDevices
	 * @return
	 * @throws InSideException
	 */
	Version [] getVersionDosDevice(Integer idDevices) throws InSideException;

	/**
	 * @param idDevices
	 * @return
	 * @throws InSideException
	 */
	List<Version> getAllVersionDevice(Integer idDevices) throws InSideException;
	
	
	/**
	 * @return
	 * @throws InSideException
	 */
	List<Version> getAllVersion() throws InSideException;
	
	
	/**
	 * 
	 * @param d
	 * @throws InSideException
	 */
	void save(Version d) throws InSideException;
	

	/**
	 * @param id
	 * @param desdeDer
	 * @param desdeIzq
	 * @return
	 */
	List<Version> getVersionDeviceWithDate(Integer id, Calendar desdeDer, Calendar desdeIzq) throws InSideException;

	
	/**
	 * @param valueOf
	 * @return
	 */
	public Version[] getDosUltimasVersiones(Integer idVersion) throws InSideException;

}

package uy.com.s4b.inside.core.ejbs.device;

import java.util.List;

import uy.com.s4b.inside.core.entity.Device;
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
public interface DeviceService {

	
	/**
	 * @param id
	 * @return
	 * @throws InSideException
	 */
	Device getDevice(Integer id) throws InSideException;
	
	
	/**
	 * 
	 * @param d
	 * @throws InSideException
	 */
	void save(Device d) throws InSideException;
	
	/**
	 * @param ipHost
	 * @return 
	 */
	Device getDeviceByIP(String ipHost)throws InSideException;


	/**
	 * @return
	 * @throws InSideException
	 */
	public List<Device> getAllDevice() throws InSideException;
}

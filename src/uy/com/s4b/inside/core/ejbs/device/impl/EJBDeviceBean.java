package uy.com.s4b.inside.core.ejbs.device.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import uy.com.s4b.inside.core.ejbs.device.DeviceService;
import uy.com.s4b.inside.core.ejbs.device.EJBDeviceLocal;
import uy.com.s4b.inside.core.ejbs.device.EJBDeviceRemote;
import uy.com.s4b.inside.core.entity.Device;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: EJBDeviceBean.java <br>
 * Description: <br>
 * Fecha creación: 28/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

@Stateless()
@Local(EJBDeviceLocal.class)
@Remote(EJBDeviceRemote.class)
@LocalBinding(jndiBinding="inSide/EJBDevice/local")
@RemoteBinding(jndiBinding="inSide/EJBDevice/remote")
public class EJBDeviceBean implements DeviceService {

	
	private static final Logger log = Logger.getLogger(EJBDeviceBean.class);
	
	@PersistenceContext
	EntityManager em;
	
	/**
	 * 
	 */
	public EJBDeviceBean() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.device.
	 * DeviceService#save(uy.com.s4b.inside.core.entity.Device)
	 */
	@Override
	public void save(Device d) throws InSideException {
		log.info("Ingresa salvar device: " + d.toString());
		em.persist(d);
	}
	
	
	@Override
	public Device getDevice(Integer id) throws InSideException {
		log.info("Se ingresa a recuperar el dispocitivo: " + id);
		return (Device)em.find(Device.class, id);
	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.device.
	 * DeviceService#getDeviceByIP(java.lang.String)
	 */
	@Override
	public Device getDeviceByIP(String ipHost) throws InSideException {
		try {
			Query q = em.createNamedQuery("find.Device.ip").setParameter("ipEquipo", ipHost);
			//TODO se debe buscar por alguna otra de interfaz ahora no lo vamos hacer.
			return (Device)q.getSingleResult();
		}catch (NoResultException ex) {
			return null;
		}
	}
	
}

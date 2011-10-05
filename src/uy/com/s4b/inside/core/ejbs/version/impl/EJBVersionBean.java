package uy.com.s4b.inside.core.ejbs.version.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import uy.com.s4b.inside.core.ejbs.version.EJBVersionLocal;
import uy.com.s4b.inside.core.ejbs.version.EJBVersionRemote;
import uy.com.s4b.inside.core.ejbs.version.VersionService;
import uy.com.s4b.inside.core.entity.Version;
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
@Local(EJBVersionLocal.class)
@Remote(EJBVersionRemote.class)
@LocalBinding(jndiBinding="inSide/EJBVersion/local")
@RemoteBinding(jndiBinding="inSide/EJBVersion/remote")

public class EJBVersionBean implements VersionService {

	
	private static final Logger log = Logger.getLogger(EJBVersionBean.class);
	
	@PersistenceContext
	EntityManager em;
	/**
	 * 
	 */
	public EJBVersionBean() {
		
	}
	
	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.version.
	 * VersionService#getDevice(java.lang.Integer)
	 */
	@Override
	public Version getVersion(Integer id) throws InSideException {
//		Version retorno = em.find(Version.class, id);
//		Hibernate.initialize(retorno.getOneDevice().getColInterfaceDevice());
//		Hibernate.initialize(retorno.getOneDevice().getColVersion());
//		Hibernate.initialize(retorno.getOneDevice().getColVersion());
		
		return em.find(Version.class, id);
	}
	
	
	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.version.
	 * VersionService#save(uy.com.s4b.inside.core.entity.Version)
	 */
	@Override
	public void save(Version ver) throws InSideException {
		log.info("Ingreso a salver version");
		em.persist(ver);
	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.version.VersionService#getVersionDevice(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Version[] getVersionDosDevice(Integer idDevices) throws InSideException {
		Version retorno [] = new Version[2];
		Query q = em.createNamedQuery("findVersionDevice");
		List<Version> lista = q.setParameter("pid", idDevices).getResultList();
		if (lista.size() >= 2){
			retorno[0] = lista.get(0);
			retorno[1] = lista.get(1);
			
		}
		return retorno;
	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.version.VersionService#getAllVersionDevice(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Version> getAllVersionDevice(Integer idDevices) throws InSideException {
		Query q = em.createNamedQuery("findVersionDevice");
		List<Version> lista = q.setParameter("pid", idDevices).getResultList();		
		return lista;
	}


}

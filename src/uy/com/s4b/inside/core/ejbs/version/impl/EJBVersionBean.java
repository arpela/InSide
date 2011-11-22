package uy.com.s4b.inside.core.ejbs.version.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import uy.com.s4b.inside.core.common.TypeConfig;
import uy.com.s4b.inside.core.ejbs.version.EJBVersionLocal;
import uy.com.s4b.inside.core.ejbs.version.EJBVersionRemote;
import uy.com.s4b.inside.core.ejbs.version.VersionService;
import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: EJBDeviceBean.java <br>
 * Description: <br>
 * Fecha creaci�n: 28/09/2011 <br>
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

	/* 
	 * (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.version.VersionService#
	 * getVersionDevice(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Version[] getVersionDosDevice(Integer idDevices) throws InSideException {
		Version retorno [] = new Version[2];
		Query q = em.createNamedQuery("findVersionDevice");
		try {
			List<Version> lista = q.setParameter("pid", idDevices).getResultList();
			if (lista.size() >= 2){
				retorno[0] = lista.get(0);
				retorno[1] = lista.get(1);
			}			
			return retorno;
		}catch (javax.persistence.EntityNotFoundException  ex) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.version.VersionService#getAllVersionDevice(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Version> getAllVersionDevice(Integer idDevices) throws InSideException {
		try {
			Query q = em.createNamedQuery("findVersionDevice");
			List<Version> lista = q.setParameter("pid", idDevices).getResultList();	
			return lista;
		}catch (javax.persistence.EntityNotFoundException ex) {
			log.error(ex.getMessage());
			return new ArrayList<Version>();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Version> getAllVersion() throws InSideException {
		Query q = em.createNamedQuery("findVersionAll");
		List<Version> lista = q.getResultList();		
		return lista;
	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.version.VersionService#getVersionDeviceWithDate(java.lang.Integer, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Version> getVersionDeviceWithDate(Integer id, Calendar desdeDer,
			Calendar hastaDer) throws InSideException {
		Query q = em.createNamedQuery("find.Version.Device.withDate");
		q.setParameter("pid", id);
		q.setParameter("fDesde", desdeDer, TemporalType.DATE);
		q.setParameter("fHasta", hastaDer, TemporalType.DATE);
		List<Version> lista = q.getResultList();
		return lista;
	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.version.VersionService#getDosVersiones(java.lang.Integer)
	 */
	@Override
	public Version[] getDosUltimasVersiones(Integer idVersion) throws InSideException {
		Version version1  = this.getVersion(idVersion);
		List<Version> lista = getAllVersionDevice(version1.getOneDevice().getId());
		Version version2 = null;
		if (!lista.isEmpty()){
			version2 = lista.get(0);
		}else{
			version2 = new Version();
			version2.setConfig("");
			version2.setOneDevice(version1.getOneDevice());
			version2.setType(TypeConfig.Running);
		}
		
		Version retorno [] = {version1, version2};
		return retorno;
	}


}

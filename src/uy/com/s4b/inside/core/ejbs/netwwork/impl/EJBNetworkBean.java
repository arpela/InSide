package uy.com.s4b.inside.core.ejbs.netwwork.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.jboss.ejb3.annotation.LocalBinding;

import uy.com.s4b.inside.core.ejbs.netwwork.EJBNetworkLocal;
import uy.com.s4b.inside.core.ejbs.netwwork.NetworkService;
import uy.com.s4b.inside.core.entity.Network;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: EJBNetwork.java <br>
 * Description: <br>
 * Fecha creación: 22/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@Stateless
@Local(EJBNetworkLocal.class)
@LocalBinding(jndiBinding="inSide/EJBNetwork/local")
public class EJBNetworkBean implements NetworkService {

	@PersistenceContext()
	EntityManager em;
	
	
	/**
	 * 
	 */
	public EJBNetworkBean() {
		
	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.netwwork.
	 * NetworkService#listNetworkByNameDevice(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Network> listNetworkByNetwork(String nameNetwokr) {
		Query query = em.createNamedQuery("findByNameNetwork");
		query.setParameter("pName", nameNetwokr);
		
		List<Network> resultado = query.getResultList();
		if ((resultado != null) && (resultado.size() > 0)){
			for (Network network : resultado) {
				Hibernate.initialize(network.getColSite());
			}
		}
		return resultado;
	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.netwwork.NetworkService#listNetwork()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Network> listNetwork() throws InSideException {
		Query query = em.createNamedQuery("findAll");
		List<Network> resultado = query.getResultList();
		if ((resultado != null) && (resultado.size() > 0)){
			for (Network network : resultado) {
				Hibernate.initialize(network.getColSite());
			}
		}
		
		return resultado;
	}


	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.netwwork.
	 * NetworkService#listNetworkByNameDevice(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Network> listNetworkByNameDevice(String nameDivice) {
		
		Query query = em.createNativeQuery("select n.* from network n, device d, site s, zone z where " +
			"n.id = s.networkid and s.id = z.siteid and z.id = d.zoneid and d.hostname = :pName", Network.class);
		
		query.setParameter("pName", nameDivice);
		
		List<Network> resultado = query.getResultList();
		if ((resultado != null) && (resultado.size() > 0)){
			for (Network network : resultado) {
				Hibernate.initialize(network.getColSite());
			}
		}
		return resultado;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Network> listNetworkBySite(String nameSite) {
		Query query = em.createNativeQuery("select n.* from network n, site s where n.id = s.networkid " +
			"and s.name = :pName", Network.class);
		query.setParameter("pName", nameSite);
		
		List<Network> resultado = query.getResultList();
		if ((resultado != null) && (resultado.size() > 0)){
			for (Network network : resultado) {
				Hibernate.initialize(network.getColSite());
			}
		}
		return resultado;
	}


	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.netwwork.NetworkService#listNetworkByZone(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Network> listNetworkByZone(String nameZone)
			throws InSideException {
		Query query = em.createNativeQuery("select n.* from network n, site s, zone z where n.id = s.networkid " +
			"and s.id = z.siteid and z.name = :pName", Network.class);
		query.setParameter("pName", nameZone);
		
		List<Network> resultado = query.getResultList();
		if ((resultado != null) && (resultado.size() > 0)){
			for (Network network : resultado) {
				Hibernate.initialize(network.getColSite());
			}
		}
		return resultado;
	}
	
}

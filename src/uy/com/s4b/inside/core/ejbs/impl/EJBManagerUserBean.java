package uy.com.s4b.inside.core.ejbs.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;

import uy.com.s4b.inside.core.ejbs.EJBManagerUserLocal;
import uy.com.s4b.inside.core.ejbs.ManagerUserService;
import uy.com.s4b.inside.core.entity.User;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: EJBManagerUserBean.java <br>
 * Description: <br>
 * Fecha creaci�n: 08/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
//@ManagedBean(name="login")

@Stateless
@Local(EJBManagerUserLocal.class)
@LocalBinding(jndiBinding="inSide/EJBManagerUser")
public class EJBManagerUserBean implements ManagerUserService {

	private static final Logger log = Logger.getLogger(EJBManagerUserBean.class);
	
	@PersistenceContext
	EntityManager em;
	

	/**
	 * 
	 */
	public EJBManagerUserBean() {

	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.ManagerUserService#saveUser(uy.com.s4b.inside.core.entity.User)
	 */
	@Override
	public void saveUser(User u) throws InSideException {
		log.info("Salvo el usuario: " + u.getName());
		
		throw new InSideException("Not yet implement!!");
	}

	
	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.ManagerUserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String name, String pass) throws InSideException {
		log.info("Autenticate user: " + name);
//		em.find(User.class, name);
		return null;
	}
	
	
	
}

package uy.com.s4b.inside.core.ejbs.manager.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import uy.com.s4b.inside.core.ejbs.manager.EJBManagerUserLocal;
import uy.com.s4b.inside.core.ejbs.manager.EJBManagerUserRemote;
import uy.com.s4b.inside.core.ejbs.manager.ManagerUserService;
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
@Remote(EJBManagerUserRemote.class)
@Local(EJBManagerUserLocal.class)
@RemoteBinding(jndiBinding="inSide/EJBManagerUser/remote")
@LocalBinding(jndiBinding="inSide/EJBManagerUser/local")
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
		log.info("Save user: " + u.getName());
		try {	
			em.persist(u);			
		} catch (EntityExistsException e) {
			throw new InSideException(e.getMessage(), e);
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			throw new InSideException("generalError", e.getMessage(), e);
		}
	}

	
	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.ManagerUserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String name, String pass) throws InSideException {
		log.info("Autenticate user: " + name);
		Query q = em.createNamedQuery("loginUser");
		q.setParameter("pNick", name);
		q.setParameter("pPass", pass);
		try {
			User retorno = (User)q.getSingleResult();			
			return retorno;
		}catch (NoResultException ex) {
			log.error(ex.getMessage(), ex);
			throw new InSideException("login.error.noautentica", ex.getMessage(), ex);
		}
	}
	
	
	
}

package uy.com.s4b.inside.core.ejbs.command.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import uy.com.s4b.inside.core.common.TypeCommand;
import uy.com.s4b.inside.core.ejbs.command.CommandService;
import uy.com.s4b.inside.core.ejbs.command.EJBCommandLocal;
import uy.com.s4b.inside.core.ejbs.command.EJBCommandRemote;
import uy.com.s4b.inside.core.entity.Command;
import uy.com.s4b.inside.core.entity.Ios;
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
@Local(EJBCommandLocal.class)
@Remote(EJBCommandRemote.class)
@LocalBinding(jndiBinding="inSide/EJBCommandBean/local")
@RemoteBinding(jndiBinding="inSide/EJBCommandBean/remote")
public class EJBCommandBean implements CommandService {

	
	private static final Logger log = Logger.getLogger(EJBCommandBean.class);
	
	@PersistenceContext
	EntityManager em;
	
	/**
	 * 
	 */
	public EJBCommandBean() {
		
	}

	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.command.CommandService#getDevice(java.lang.Integer)
	 */
	@Override
	public Command getCommand(Integer id) throws InSideException {
		return em.find(Command.class, id);
	}

	/* 
	 * (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.command.CommandService#
	 * save(uy.com.s4b.inside.core.entity.Command)
	 * 
	 */
	@Override
	public void save(Command d) throws InSideException {
		log.info("Se ingresa a salvar comando");
		em.persist(d);
	}
	
	/**
	 * 
	 */
	public List<Command> getListCommandForIosAndType(Ios ios, TypeCommand t) throws InSideException {
		log.info("Se ingresa a recuperar los commandos del ios: " + ios.getId());
		log.info("Tipo de commando generico: " + ios.getId());
		Query q = em.createNamedQuery("findCommandsWhitTypeCommand").
				setParameter("typeCommand", t).
				setParameter("id", ios.getId());
		Ios r = (Ios)q.getSingleResult();
		
		return r.getColCommand();
	}

	
}

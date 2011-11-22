package uy.com.s4b.inside.core.ejbs.ignoreline.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.LocalBinding;

import uy.com.s4b.inside.core.ejbs.ignoreline.EJBIgnoreLineLocal;
import uy.com.s4b.inside.core.ejbs.ignoreline.IgnoreLineService;
import uy.com.s4b.inside.core.entity.IgnoreLine;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: EJBIgnoreLineBean.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 20/11/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */

@Stateless
@Local(EJBIgnoreLineLocal.class)
@LocalBinding(jndiBinding="inSide/EJBIgnoreLine/local")
public class EJBIgnoreLineBean implements IgnoreLineService {
	
	@PersistenceContext()
	EntityManager em;

	public EJBIgnoreLineBean() {
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IgnoreLine> getAllIgnoreLine() throws InSideException {
		Query query = em.createNamedQuery("findAll.IgnoreLine");
		return query.getResultList();
		
	}
	
	
	/* (non-Javadoc)
	 * @see uy.com.s4b.inside.core.ejbs.ignoreline.
	 * IgnoreLineService#getAllIgnoreLineValue()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllIgnoreLineValue() {
		List<String> resultado = new ArrayList<String>();
		
		Query query = em.createNamedQuery("findAll.IgnoreLine");		
		List<IgnoreLine> resListIgnoreLine = query.getResultList();
		if ((resListIgnoreLine != null) && (resListIgnoreLine.size() > 0)){
			for (IgnoreLine ignoreLine : resListIgnoreLine) {
				resultado.add(ignoreLine.getValue());
			}
		}
		return resultado;
	}


}

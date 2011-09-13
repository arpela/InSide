package uy.com.s4b.inside.web.bakingbean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.ejbs.EJBManagerUserLocal;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: Navega.java <br>
 * Description: <br>
 * Fecha creación: 12/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@ManagedBean(name="navegar")
public class Navega implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	EJBManagerUserLocal mangerUser;
	
	@ManagedProperty(value="#{login}")
	private LoginBakingBean loginBaking;
	
	private static final Logger log = Logger.getLogger(LoginBakingBean.class);
	
	
	/**
	 * 
	 */
	public Navega() {

	}
	
	
	public String accionLogin(){
		log.info("Ingreso hacer login del usuario!!! ");
		String retorno = "p";
		try {
			mangerUser.login(loginBaking.getUserBean().getName(), loginBaking.getUserBean().getPass());
		} catch (InSideException ex) {
			log.error(ex.getMessage(), ex);
		}
		log.info("Pagina de redireccion --->> " + retorno);
		log.info("fin de ejecucion!!!");
		return retorno;
	}

//	Since there are no explicit navigation rules in faces-config.xml,
//	these return values correspond to page1.xhtml, page2.xhtml, and
//	page3.xhtml (in same folder as page that has the form).
	/**
	 * @param loginBaking the loginBaking to set
	 */
	public void setLoginBaking(LoginBakingBean loginBaking) {
		this.loginBaking = loginBaking;
	}

	
	
}

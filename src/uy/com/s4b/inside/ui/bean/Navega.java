package uy.com.s4b.inside.ui.bean;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.common.CriptPassword;
import uy.com.s4b.inside.core.ejbs.manager.EJBManagerUserLocal;
import uy.com.s4b.inside.core.entity.User;
import uy.com.s4b.inside.core.exception.InSideException;
import uy.com.s4b.inside.ui.bundle.BundleUtil;

/**
 * Title: Navega.java <br>
 * Description: <br>
 * Fecha creaci�n: 12/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@ManagedBean(name="navegate")
@RequestScoped
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
		String retorno = "login";
		try {
			String pass = new CriptPassword().getHashSH1(loginBaking.getUserBean().getPass());
			User u = mangerUser.login(loginBaking.getUserBean().getNick(), pass);
			retorno = "site/home";
			
		} catch (InSideException ex) {
			log.error(ex.getKeyMSGError());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						BundleUtil.getMessageResource(ex.getKeyMSGError()), 
						BundleUtil.getMessageResource(ex.getKeyMSGError())));
		} catch (NoSuchAlgorithmException ex) {
			// TODO cambia la exception y agregar key !!!
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						BundleUtil.getMessageResource("generalError"), 
						BundleUtil.getMessageResource("generalError")));
		}
		
		log.debug("Pagina de redireccion --->> " + retorno);
		log.info("fin de Login!!!");
		return retorno;
	}


	
	/**
	 * hace el cambio de idima
	 * @return
	 */
	public String changeLeguage(){
		FacesContext miContexto = FacesContext.getCurrentInstance();
		Locale miLocale = new Locale("en","US");
		miContexto.getViewRoot().setLocale(miLocale);
		return "login";
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

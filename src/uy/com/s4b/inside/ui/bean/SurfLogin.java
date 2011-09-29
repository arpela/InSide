package uy.com.s4b.inside.ui.bean;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.common.CriptPassword;
import uy.com.s4b.inside.core.ejbs.manager.EJBManagerUserLocal;
import uy.com.s4b.inside.core.ejbs.netwwork.EJBNetworkLocal;
import uy.com.s4b.inside.core.entity.Network;
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
public class SurfLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	EJBManagerUserLocal ejbMangerUser;
	
	@EJB
	EJBNetworkLocal ejbNetwork;
	
	@ManagedProperty(value="#{login}")
	private LoginBakingBean loginBaking;
	
	private static final Logger log = Logger.getLogger(LoginBakingBean.class);
	
	
	/**
	 * 
	 */
	public SurfLogin() {

	}
	
	
	
	public String accionLogin(){
		// setear el id de session para que luego se escriba en el log.
		
		log.info("Ingreso hacer login del usuario!!! ");
		String retorno = null;
		try {
			String pass = new CriptPassword().getHashSH1(loginBaking.getUserBean().getPass());
			User u = ejbMangerUser.login(loginBaking.getUserBean().getNick(), pass);
			loginBaking.setUserBean(u);
			
			
			List<Network> listNetwork = ejbNetwork.listNetwork();
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("listaNetwork", listNetwork);
			
			retorno = "site/home";
		} catch (InSideException ex) {
			log.error(ex.getKeyMSGError());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						BundleUtil.getMessageResource(ex.getKeyMSGError()), 
						BundleUtil.getMessageResource(ex.getKeyMSGError())));
		}
		log.debug("Pagina de redireccion --->> " + retorno);
		log.info("fin de Login!!!");
		return retorno;
	}

	
	
	public void accionLogout() {
		try {
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletRequest req = (HttpServletRequest)faces.getExternalContext().getRequest();
			Enumeration<String> en =  req.getSession().getAttributeNames();
			for (; en.hasMoreElements();) {
				String key = en.nextElement();
				req.removeAttribute(key);
			}
			req.getSession().invalidate();
			log.info("Log out");
			faces.getExternalContext().redirect(req.getContextPath());
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		}
	}
	
	
	
	/**
	 * hace el cambio de idioma por ahora no utilizado...
	 * @return
	 */
	public String changeLeguage(){
		FacesContext context = FacesContext.getCurrentInstance();
		Locale miLocale = new Locale("en","US");
		context.getViewRoot().setLocale(miLocale);
		return "login";
	}

	
	/**
	 * @param loginBaking the loginBaking to set
	 */
	public void setLoginBaking(LoginBakingBean loginBaking) {
		this.loginBaking = loginBaking;
	}

	
	
}

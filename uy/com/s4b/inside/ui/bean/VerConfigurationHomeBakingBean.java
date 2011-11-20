package uy.com.s4b.inside.ui.bean;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.ejbs.version.EJBVersionLocal;
import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.exception.InSideException;
import uy.com.s4b.inside.ui.bundle.BundleUtil;

/**
 * Title: LoginBakingBean.java <br>
 * Description: <br>
 * Fecha creaci�n: 08/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@ManagedBean(name="configuracion")
@RequestScoped
public class VerConfigurationHomeBakingBean implements Serializable {
	
	
	private static final long serialVersionUID = 1517974968928262899L;
	private static final Logger log = Logger.getLogger(VerConfigurationHomeBakingBean.class);
	private String dato;
	
	
	
	
	/**
	 * 
	 */
	public VerConfigurationHomeBakingBean() {
		this.setDato("");
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> a = context.getExternalContext().getRequestParameterMap();
		getVersion(a.get("id"));
	}
	
	
	private void getVersion(String idVersion){
		log.info("se ingresa a recuperar la version: " + idVersion);
		try {
			EJBVersionLocal ejbVersion = (EJBVersionLocal)new InitialContext().lookup("inSide/EJBVersion/local");
			Version ver = ejbVersion.getVersion(Integer.valueOf(idVersion));
			String unaVersion = ver.getConfig();
			String listaLinea [] = unaVersion.split("\n");
			StringBuffer pageDer = new StringBuffer();
			pageDer.append("<table width=\"470\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			for (int i = 0; i < listaLinea.length; i++) {
				pageDer.append("<tr><td td bgcolor=\"");
				pageDer.append("\" class=\"table\"><span>");
				pageDer.append(i + " - " + listaLinea[i]);
				pageDer.append("</span></td></tr>");
			}
			pageDer.append("</table>");
			this.setDato(pageDer.toString());
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage(), ex);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						BundleUtil.getMessageResource("generalError"), 
						BundleUtil.getMessageResource("generalError")));
		} catch (InSideException ex) {
			log.error(ex.getKeyMSGError());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						BundleUtil.getMessageResource(ex.getKeyMSGError()), 
						BundleUtil.getMessageResource(ex.getKeyMSGError())));
		} catch (NamingException ex) {
			log.error(ex.getMessage(), ex);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						BundleUtil.getMessageResource("generalError"), 
						BundleUtil.getMessageResource("generalError")));
		}
	}

	/**
	 * @return the dato
	 */
	public String getDato() {
		return dato;
	}

	/**
	 * @param dato the dato to set
	 */
	public void setDato(String dato) {
		this.dato = dato;
	}
	
	

}

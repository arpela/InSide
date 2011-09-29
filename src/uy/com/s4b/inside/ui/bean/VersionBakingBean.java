package uy.com.s4b.inside.ui.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.common.DiffLine;
import uy.com.s4b.inside.core.ejbs.report.EJBReportDiffLocal;
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
@ManagedBean(name="versionBakingBean")
@RequestScoped
public class VersionBakingBean implements Serializable {
	
	private static final Logger log = Logger.getLogger(VersionBakingBean.class);

	@EJB
	EJBVersionLocal ejbVersion;
	
	@EJB
	EJBReportDiffLocal ejbDiff;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1648325814768374866L;

	
	public String selectVersionHome(){
		String retorno = null;
		log.info("se ingresa a recuperar la version");
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> map = context.getExternalContext().getRequestParameterMap();
		String idDevice = (String) map.get("id");
		log.info("Codigo de divece");
		
		try {
			
			HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
			Version dosVersiones [] =  ejbVersion.getVersionDevice(Integer.valueOf(idDevice));
			Map<Integer, DiffLine> estructura = ejbDiff.doDiff(dosVersiones[0].getConfig(), dosVersiones[1].getConfig());
			log.info(estructura);
			
			String unaVersion = dosVersiones[1].getConfig();
			
			/* Der */
			String listaLinea [] = unaVersion.split("\n");
			StringBuffer pageDer = new StringBuffer();
			Map<Integer, DiffLine> lineasNuevas = new HashMap<Integer, DiffLine>(500);
			
			for (int i = 0; i < listaLinea.length; i++) {
				int key = i + 1;
				String color = "";
				if (estructura.containsKey(key)){
					color = estructura.get(key).getValue();
					
					if (estructura.get(key) == DiffLine.NEW){
						lineasNuevas.put(i, DiffLine.NEW);
					}
				}
				pageDer.append("<tr><td td bgcolor=\"");
				pageDer.append(color);
				pageDer.append("\" class=\"table\"><span>");
				pageDer.append(i + " - " + listaLinea[i]);
				pageDer.append("</span></td></tr>");
			}
			/* ************************ */
			
			/* izq */
			unaVersion = dosVersiones[0].getConfig();
			listaLinea = unaVersion.split("\n");
			StringBuffer pageIzq = new StringBuffer();
			int numero = 0;
			for (int i = 0; i < listaLinea.length; i++) {
				
//				int key = i + 1;
				
				String color = "";
				
//				if (estructura.containsKey(key)){
//					if (estructura.get(key) != DiffLine.NEW)
//						color = estructura.get(key).getValue();
//				}
				
				if (lineasNuevas.containsKey(i)){
					pageIzq.append("<tr><td td bgcolor=\"");
					pageIzq.append(lineasNuevas.get(i).getValue());
					pageIzq.append("\" class=\"table\"><span>");
					pageIzq.append(numero + " -" );
					pageIzq.append("</span></td></tr>");
					numero++;
				}
				
				pageIzq.append("<tr><td td bgcolor=\"");
				pageIzq.append(color);
				pageIzq.append("\" class=\"table\"><span>");
				pageIzq.append(numero + " - " + listaLinea[i]);
				pageIzq.append("</span></td></tr>");
				numero++;
			}

			
			unaVersion = dosVersiones[1].getConfig();
			
			session.setAttribute("page1", pageIzq.toString());
			session.setAttribute("page2", pageDer.toString());
			
			retorno = "verReporeteHome";
			
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
		}
		
		log.info("Resultado de la navegacion --->  " + retorno);
		return retorno;
	}
	
	
	
	public String page2Report(){
		String retorno = null;
		
		return retorno;
	}
	
	
}

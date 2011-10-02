package uy.com.s4b.inside.ui.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.common.DiffLine;
import uy.com.s4b.inside.core.ejbs.netwwork.EJBNetworkLocal;
import uy.com.s4b.inside.core.ejbs.report.EJBReportDiffLocal;
import uy.com.s4b.inside.core.ejbs.version.EJBVersionLocal;
import uy.com.s4b.inside.core.entity.Network;
import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.exception.InSideException;
import uy.com.s4b.inside.ui.bean.filter.UtilCreatePage;
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
@SessionScoped
public class VersionBakingBean implements Serializable {
	
	private static final Logger log = Logger.getLogger(VersionBakingBean.class);

	private String treeNetwork;
	
	private boolean rerenderTree;
	
	@EJB
	EJBVersionLocal ejbVersion;
	
	@EJB
	EJBNetworkLocal ejbNetwork;
	
	@EJB
	EJBReportDiffLocal ejbDiff;
	
	
	
	
	/**
	 * 
	 */
	public VersionBakingBean() {
		rerenderTree = false;
	}
	
	
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
			Version dosVersiones [] =  ejbVersion.getVersionDosDevice(Integer.valueOf(idDevice));
			
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
	
	
	public String verReporte(){
		String retorno = null;
		log.info("se ingresa a recuperar la version");
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> map = context.getExternalContext().getRequestParameterMap();
		String idVersion1 = (String) map.get("radio1");
		String idVersion2 = (String) map.get("radio2");
		
		log.info("Codigo de divece");
		
		try {
			
			HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
			log.info("IDES idVersion1 ---> " + idVersion1);
			log.info("IDES idVersion2 ---> " + idVersion2);
			
			Version dosVersiones [] =  getDosVersion(idVersion1, idVersion2);
			
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
	
	
	
	
	public String getTreeNetwork(){
		StringBuffer retorno = new StringBuffer();
		if (!rerenderTree){
			try {
				List<Network> listNetwork = ejbNetwork.listNetwork();
				new UtilCreatePage().createTree(retorno, listNetwork);
			} catch (InSideException ex) {
				retorno = new StringBuffer();
				retorno.append("<tr><td colspan=\"4\" class=\"networks\">");
				retorno.append("Problemas al cargar el �rbol de dispositivos");
				retorno.append("</td></tr>");
			}
			treeNetwork =  retorno.toString();
		}
		return treeNetwork;
	}
	
	/**
	 * @param idVersion1
	 * @param idVersion2
	 * @return
	 * @throws InSideException 
	 * @throws NumberFormatException 
	 */
	private Version[] getDosVersion(String idVersion1, String idVersion2) throws NumberFormatException, InSideException {
		Version retorno [] = new Version[2];
		List<Version> listaVersiones = ejbVersion.getAllVersionDevice(Integer.valueOf("1"));
		int i = 0;
		for (Version version : listaVersiones) {
			if ((version.getId().equals(Integer.valueOf(idVersion1))) || 
					(version.getId().equals(Integer.valueOf(idVersion2)))){
				retorno[i] = version;
				i++;
			}
		}
		
		if (idVersion1.equals(idVersion2)){
			retorno[1] = retorno[0];
		}
		return retorno;
	}


	public String page2Report(){
		String retorno = null;
		log.info("para el paso dos !!!!!!!!!!!!!!!!!!!!");
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
		Map<String, String[]> map = context.getExternalContext().getRequestParameterValuesMap();
		String listaelect [] = map.get("equipos");
		
		try {
			
			if ((listaelect != null) && (listaelect.length == 2)){
				
				for (int i = 0; i < listaelect.length; i++) {
					log.info(" ---> " + listaelect[i]);
				}
				
				List<Version> listaVersiones1 = ejbVersion.getAllVersionDevice(Integer.valueOf(listaelect[0]));
				
				List<Version> listaVersiones2 = ejbVersion.getAllVersionDevice(Integer.valueOf(listaelect[1]));
				
				
				session.setAttribute("paginaLista1", getPagina(listaVersiones1, "radio1"));
				session.setAttribute("paginaLista2", getPagina(listaVersiones2, "radio2"));
				
				session.setAttribute("equipo1", listaelect[0]);
				session.setAttribute("equipo2", listaelect[1]);
				
				retorno = "paso2reporte";
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						"Debe seleccionar dos equipos.","Debe seleccionar dos equipos."));
			}
			
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage(), ex);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						BundleUtil.getMessageResource("generalError"), 
						BundleUtil.getMessageResource("generalError")));
		} catch (InSideException ex) {
			log.error(ex.getMessage(), ex);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						BundleUtil.getMessageResource("generalError"), BundleUtil.getMessageResource("generalError")));
		}
		return retorno;
	}


	/**
	 * @param listaVersiones
	 * @return
	 */
	private String getPagina(List<Version> listaVersiones, String name) {
		StringBuffer retorno = new StringBuffer();
		
		for (Version version : listaVersiones) {			
			retorno.append("<li style=\"background:url(#{request.contextPath}/resources/img/B.png) left no-repeat;\">");
			retorno.append("<input id=\"" + name + "\" type=\"radio\" name=\"" + name + "\" value=\"" + version.getId() + "\"/>");
			retorno.append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(version.getDate().getTime()));
			retorno.append("</li>");
		}
		
		return retorno.toString();
	}


	/**
	 * @return the rerenderTree
	 */
	public boolean isRerenderTree() {
		return rerenderTree;
	}


	/**
	 * @param rerenderTree the rerenderTree to set
	 */
	public void setRerenderTree(boolean rerenderTree) {
		this.rerenderTree = rerenderTree;
	}


	/**
	 * @param treeNetwork the treeNetwork to set
	 */
	public void setTreeNetwork(String treeNetwork) {
		this.treeNetwork = treeNetwork;
	}
	
}

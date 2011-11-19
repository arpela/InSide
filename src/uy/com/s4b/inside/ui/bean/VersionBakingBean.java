package uy.com.s4b.inside.ui.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.ejbs.device.EJBDeviceLocal;
import uy.com.s4b.inside.core.ejbs.netwwork.EJBNetworkLocal;
import uy.com.s4b.inside.core.ejbs.report.EJBReportDiffLocal;
import uy.com.s4b.inside.core.ejbs.report.impl.difflib.Delta;
import uy.com.s4b.inside.core.ejbs.version.EJBVersionLocal;
import uy.com.s4b.inside.core.entity.Network;
import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.exception.InSideException;
import uy.com.s4b.inside.ui.bean.filter.UtilCreatePage;
import uy.com.s4b.inside.ui.bundle.BundleUtil;

/**
 * Title: LoginBakingBean.java <br>
 * Description: <br>
 * Fecha creaciï¿½n: 08/09/2011 <br>
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
	EJBDeviceLocal ejbDevice;
	
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

	
	public List<Version> getListaUltimasVersiones(){
		List<Version>  retorno = null;
		try {
			List<Version>  lista = ejbVersion.getAllVersion();
			if (lista.size() > 6){	
				retorno = lista.subList(0, 6);
			}else{				
				retorno = lista;
			}
		} catch (InSideException ex) {
			retorno = new ArrayList<Version>();
		}
		return retorno;
	}
	
	
	
	public String selectVersionHome(){
		String retorno = null;
		log.info("se ingresa a recuperar la version");
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> map = context.getExternalContext().getRequestParameterMap();
//		String idDevice = (String) map.get("id");
		String idVersion = (String) map.get("id");
		log.info("Codigo de divece");
		
		try {
			// TODO esto esta malaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
			
			HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
//			Version dosVersiones [] =  ejbVersion.getVersionDosDevice(Integer.valueOf(idDevice));
			
			Version dosVersiones [] =  ejbVersion.getDosUltimasVersiones(Integer.valueOf(idVersion));
			
			String unaVersion = dosVersiones[0].getConfig();
			
			Map<Integer, Delta> estructura = ejbDiff.doDiff(unaVersion, dosVersiones[1].getConfig());
			log.info(estructura);
						
			String listaLinea [] = unaVersion.split("\n");
			StringBuffer pageDer = new StringBuffer();
			StringBuffer pageIzq = new StringBuffer();
			
			for (int i = 0; i < listaLinea.length; i++) {
				
				String color = "";
				if (estructura.containsKey(i)){
					color = estructura.get(i).getType().getValue();
					
					switch (estructura.get(i).getType()) {
						case INSERT:
							for (Iterator iterator = estructura.get(i).getRevised().getLines().iterator(); iterator.hasNext();) {
								String textoDelta = (String) iterator.next();
								
								pageIzq.append("<tr><td td bgcolor=\"");
								pageIzq.append(color);
								pageIzq.append("\" class=\"table\"><span>");
								pageIzq.append(i + "  ");
								pageIzq.append("</span></td></tr>");
								
								pageDer.append("<tr><td td bgcolor=\"");
								pageDer.append(color);
								pageDer.append("\" class=\"table\"><span>");
								pageDer.append(i + " - " + textoDelta);
								pageDer.append("</span></td></tr>");
								i++;
							}
							i--;
							break;
						case CHANGE:
							int j = i;
							for (Iterator iterator = estructura.get(i).getOriginal().getLines().iterator(); iterator.hasNext();) {
								String textoDelta = (String) iterator.next();
																
								pageIzq.append("<tr><td td bgcolor=\"");
								pageIzq.append(color);
								pageIzq.append("\" class=\"table\"><span>");
								pageIzq.append(j + " - " + textoDelta);
								pageIzq.append("</span></td></tr>");
								j++;
							}
							int k = i;
							for (Iterator iterator = estructura.get(i).getRevised().getLines().iterator(); iterator.hasNext();) {
								String textoDelta = (String) iterator.next();
																
								pageDer.append("<tr><td td bgcolor=\"");
								pageDer.append(color);
								pageDer.append("\" class=\"table\"><span>");
								pageDer.append(k + " - " + textoDelta);
								pageDer.append("</span></td></tr>");
								k++;			
							}
							i = (j>k)?--j:--k;
							break;
						case DELETE:
							for (Iterator iterator = estructura.get(i).getOriginal().getLines().iterator(); iterator.hasNext();) {
								String textoDelta = (String) iterator.next();
																
								pageIzq.append("<tr><td td bgcolor=\"");
								pageIzq.append(color);
								pageIzq.append("\" class=\"table\"><span>");
								pageIzq.append(i + " - " + textoDelta);
								pageIzq.append("</span></td></tr>");
								
								pageDer.append("<tr><td td bgcolor=\"");
								pageDer.append(color);
								pageDer.append("\" class=\"table\"><span>");
								pageDer.append(i + "  ");
								pageDer.append("</span></td></tr>");
								i++;
							}
							i--;
							break;
					}
				} else {
					pageIzq.append("<tr><td td bgcolor=\"");
					pageIzq.append(color);
					pageIzq.append("\" class=\"table\"><span>");
					pageIzq.append(i + " - " + listaLinea[i]);
					pageIzq.append("</span></td></tr>");
					
					pageDer.append("<tr><td td bgcolor=\"");
					pageDer.append(color);
					pageDer.append("\" class=\"table\"><span>");
					pageDer.append(i + " - " + listaLinea[i]);
					pageDer.append("</span></td></tr>");
				}
			}
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
			String unaVersion = dosVersiones[0].getConfig();
			String segundVersion = dosVersiones[1].getConfig();
			
			Map<Integer, Delta> estructura = ejbDiff.doDiff(unaVersion, segundVersion);
			log.info(estructura);
			
			String listaLinea [] = unaVersion.split("\n");
			StringBuffer pageDer = new StringBuffer();
			StringBuffer pageIzq = new StringBuffer();
						
			for (int i = 0; i < listaLinea.length; i++) {
				
				String color = "";
				if (estructura.containsKey(i)){
					color = estructura.get(i).getType().getValue();
					
					switch (estructura.get(i).getType()) {
						case INSERT:
							for (Iterator iterator = estructura.get(i).getRevised().getLines().iterator(); iterator.hasNext();) {
								String textoDelta = (String) iterator.next();
								
								pageIzq.append("<tr><td td bgcolor=\"");
								pageIzq.append(color);
								pageIzq.append("\" class=\"table\"><span>");
								pageIzq.append(i + "  ");
								pageIzq.append("</span></td></tr>");
								
								pageDer.append("<tr><td td bgcolor=\"");
								pageDer.append(color);
								pageDer.append("\" class=\"table\"><span>");
								pageDer.append(i + " - " + textoDelta);
								pageDer.append("</span></td></tr>");
								i++;
							}
							i--;
							break;
						case CHANGE:
							int j = i;
							for (Iterator iterator = estructura.get(i).getOriginal().getLines().iterator(); iterator.hasNext();) {
								String textoDelta = (String) iterator.next();
																
								pageIzq.append("<tr><td td bgcolor=\"");
								pageIzq.append(color);
								pageIzq.append("\" class=\"table\"><span>");
								pageIzq.append(j + " - " + textoDelta);
								pageIzq.append("</span></td></tr>");
								j++;
							}
							int k = i;
							for (Iterator iterator = estructura.get(i).getRevised().getLines().iterator(); iterator.hasNext();) {
								String textoDelta = (String) iterator.next();
																
								pageDer.append("<tr><td td bgcolor=\"");
								pageDer.append(color);
								pageDer.append("\" class=\"table\"><span>");
								pageDer.append(k + " - " + textoDelta);
								pageDer.append("</span></td></tr>");
								k++;			
							}
							i = (j>k)?--j:--k;
							break;
						case DELETE:
							for (Iterator iterator = estructura.get(i).getOriginal().getLines().iterator(); iterator.hasNext();) {
								String textoDelta = (String) iterator.next();
																
								pageIzq.append("<tr><td td bgcolor=\"");
								pageIzq.append(color);
								pageIzq.append("\" class=\"table\"><span>");
								pageIzq.append(i + " - " + textoDelta);
								pageIzq.append("</span></td></tr>");
								
								pageDer.append("<tr><td td bgcolor=\"");
								pageDer.append(color);
								pageDer.append("\" class=\"table\"><span>");
								pageDer.append(i + "  ");
								pageDer.append("</span></td></tr>");
								i++;
							}
							i--;
							break;
					}
				} else {
					pageIzq.append("<tr><td td bgcolor=\"");
					pageIzq.append(color);
					pageIzq.append("\" class=\"table\"><span>");
					pageIzq.append(i + " - " + listaLinea[i]);
					pageIzq.append("</span></td></tr>");
					
					pageDer.append("<tr><td td bgcolor=\"");
					pageDer.append(color);
					pageDer.append("\" class=\"table\"><span>");
					pageDer.append(i + " - " + listaLinea[i]);
					pageDer.append("</span></td></tr>");
				}
			}
		
			session.setAttribute("izqVersion", dosVersiones[0]);
			session.setAttribute("derVersion", dosVersiones[1]);
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
	
	
	public String vovler(){
		this.setRerenderTree(false);
		return "paso1reporte";
	}
	
	
	/**
	 * Dibuja el arbol de la home
	 * @return
	 */
	public String getTreeNetworkHome(){
		StringBuffer retorno = new StringBuffer();
		try {
			List<Network> listNetwork = ejbNetwork.listNetwork();
			new UtilCreatePage().createTreeHome(retorno, listNetwork);
		} catch (InSideException ex) {
			retorno = new StringBuffer();
			retorno.append("<tr><td colspan=\"4\" class=\"networks\">");
			retorno.append("Problemas al cargar el árbol de dispositivos");
			retorno.append("</td></tr>");
		}
		treeNetwork =  retorno.toString();
		return treeNetwork;
	}
	
	
	
	/**
	 * Dibuja el arbol del filtro de reporte
	 * @return
	 */
	public String getTreeNetwork(){
		StringBuffer retorno = new StringBuffer();
		if (!rerenderTree){
			try {
				List<Network> listNetwork = ejbNetwork.listNetwork();
				new UtilCreatePage().createTree(retorno, listNetwork);
			} catch (InSideException ex) {
				retorno = new StringBuffer();
				retorno.append("<tr><td colspan=\"4\" class=\"networks\">");
				retorno.append("Problemas al cargar el ï¿½rbol de dispositivos");
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
		retorno[0] = ejbVersion.getVersion(Integer.valueOf(idVersion1));
		retorno[1] = ejbVersion.getVersion(Integer.valueOf(idVersion2));
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
			
			if ((listaelect != null) && (listaelect.length >= 1)){
				String idUno = listaelect[0];
				String idDos = "";
				if (listaelect.length == 1){					
					idDos = listaelect[0];
				}else{
					idDos = listaelect[1];					
				}
				
				List<Version> listaVersiones1 = ejbVersion.getAllVersionDevice(Integer.valueOf(idUno));
				List<Version> listaVersiones2 = ejbVersion.getAllVersionDevice(Integer.valueOf(idDos));
				session.setAttribute("paginaLista1", getPaginaRadioVersiones(listaVersiones1, "radio1"));
				session.setAttribute("paginaLista2", getPaginaRadioVersiones(listaVersiones2, "radio2"));
				session.setAttribute("equipo1", ejbDevice.getDevice(Integer.valueOf(idUno)));
				session.setAttribute("equipo2", ejbDevice.getDevice(Integer.valueOf(idDos)));
				retorno = "paso2reporte";
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						"Debe seleccionar un equipos.","Debe seleccionar un equipos."));
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
	public static String getPaginaRadioVersiones(List<Version> listaVersiones, String name) {
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

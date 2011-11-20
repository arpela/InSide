package uy.com.s4b.inside.ui.bean.filter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.ejbs.netwwork.EJBNetworkLocal;
import uy.com.s4b.inside.core.ejbs.version.EJBVersionLocal;
import uy.com.s4b.inside.core.entity.Device;
import uy.com.s4b.inside.core.entity.Network;
import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.exception.InSideException;
import uy.com.s4b.inside.ui.bean.VersionBakingBean;
import uy.com.s4b.inside.ui.bundle.BundleUtil;

/**
 * Title: EvenBakingBean.java <br>
 * Description: <br>
 * Fecha creaci�n: 25/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@ManagedBean(name="fileterReport")
@RequestScoped
public class FileterReportBakingBean {
	
	
	private static final Logger log = Logger.getLogger(FileterReportBakingBean.class);
	
	private Date desdeDer;
	private Date hastaDer;
	
	private Date desdeIzq;
	private Date hastaIzq;
	
	private String nameDivice;
	private String nameNetwork;
	private String nameSite;
	private String nameZone;
		        
	
	@ManagedProperty(value="#{versionBakingBean}")
	private VersionBakingBean version;
	
	@EJB
	EJBVersionLocal ejbVersion;

	@EJB
	EJBNetworkLocal ejbNetwork;
	
	/**
	 * 
	 */
	public FileterReportBakingBean() {
		
	}
	
	public void clear(){
		version.setRerenderTree(false);
		this.setNameDivice("");
		this.setNameNetwork("");
		this.setNameSite("");
		this.setNameZone("");
	}
	
	/**
	 * 
	 */
	public void find(){
		version.setRerenderTree(true);
		StringBuffer retorno = new StringBuffer();
		try {
			List<Network> listNetwork = null;
			if ((nameDivice != null) && (!nameDivice.trim().equals(""))){				
				listNetwork = ejbNetwork.listNetworkByNameDevice(nameDivice);
				createTree(retorno, listNetwork, this.nameDivice);
			}else  if ((nameNetwork != null) && (!nameNetwork.trim().equals(""))){				
				listNetwork = ejbNetwork.listNetworkByNetwork(nameNetwork);
				createTree(retorno, listNetwork, this.nameNetwork);
			}else  if ((nameSite != null) && (!nameSite.trim().equals(""))){				
				listNetwork = ejbNetwork.listNetworkBySite(nameSite);
				createTree(retorno, listNetwork, this.nameSite);
			}else  if ((nameZone != null) && (!nameZone.trim().equals(""))){				
				listNetwork = ejbNetwork.listNetworkByZone(nameZone);
				createTree(retorno, listNetwork, this.nameZone);
			}
		} catch (InSideException ex) {
			retorno = new StringBuffer();
			retorno.append("<tr><td colspan=\"4\" class=\"networks\">");
			retorno.append("Problemas al cargar el �rbol de dispositivos");
			retorno.append("</td></tr>");
		}
		version.setTreeNetwork(retorno.toString());
	}
	
	
	/**
	 * @param retorno
	 * @param listNetwork
	 */
	private void createTree(StringBuffer retorno, List<Network> listNetwork, String strBusqueda) {
		if (listNetwork != null && (listNetwork.size() > 0)) {
			new UtilCreatePage().createTree(retorno, listNetwork);
		} else {
			log.info("no hay resultado para ----> " + strBusqueda);
			retorno = new StringBuffer();
			retorno.append("<tr><td colspan=\"4\" class=\"networks\">");
			retorno.append("No se obtuvo resultado para \"" + strBusqueda + "\"");
			retorno.append("</td></tr>");
		}
		
	}

	/**
	 * Atiende la peticion de recargar la pagina del filtro
	 */
	public void filterDer(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
		Device d = (Device)session.getAttribute("equipo1");
		try {
			Calendar calDesde = Calendar.getInstance();
			Calendar calHasta = Calendar.getInstance();
			calDesde.setTime(this.getDesdeDer());
			calHasta.setTime(this.getHastaDer());
			List<Version> listaVersiones = ejbVersion.getVersionDeviceWithDate(d.getId(), calDesde, calHasta);
			session.setAttribute("paginaLista1", VersionBakingBean.getPaginaRadioVersiones(listaVersiones, "radio1"));
		} catch (InSideException ex) {
			log.error(ex.getMessage(), ex);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						BundleUtil.getMessageResource("generalError"), BundleUtil.getMessageResource("generalError")));
		}
	}
	
	public void filterIzq(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
		Device d = (Device)session.getAttribute("equipo2");
		try {
			Calendar calDesde = Calendar.getInstance();
			Calendar calHasta = Calendar.getInstance();
			calDesde.setTime(this.getDesdeIzq());
			calHasta.setTime(this.getHastaIzq());
			List<Version> listaVersiones = ejbVersion.getVersionDeviceWithDate(d.getId(), calDesde, calHasta);
			session.setAttribute("paginaLista2", VersionBakingBean.getPaginaRadioVersiones(listaVersiones, "radio2"));
		} catch (InSideException ex) {
			log.error(ex.getMessage(), ex);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage (FacesMessage.SEVERITY_ERROR, 
						BundleUtil.getMessageResource("generalError"), BundleUtil.getMessageResource("generalError")));
		}
	}
	

	/**
	 * @return the nameDivice
	 */
	public String getNameDivice() {
		return nameDivice;
	}

	/**
	 * @param nameDivice the nameDivice to set
	 */
	public void setNameDivice(String nameDivice) {
		this.nameDivice = nameDivice;
	}



	/**
	 * @param version the version to set
	 */
	public void setVersion(VersionBakingBean version) {
		this.version = version;
	}


	/**
	 * @return the nameSite
	 */
	public String getNameSite() {
		return nameSite;
	}



	/**
	 * @return the nameZone
	 */
	public String getNameZone() {
		return nameZone;
	}


	/**
	 * @param nameSite the nameSite to set
	 */
	public void setNameSite(String nameSite) {
		this.nameSite = nameSite;
	}



	/**
	 * @param nameZone the nameZone to set
	 */
	public void setNameZone(String nameZone) {
		this.nameZone = nameZone;
	}


	/**
	 * @return the nameNetwork
	 */
	public String getNameNetwork() {
		return nameNetwork;
	}


	/**
	 * @param nameNetwork the nameNetwork to set
	 */
	public void setNameNetwork(String nameNetwork) {
		this.nameNetwork = nameNetwork;
	}

	/**
	 * @return the desdeDer
	 */
	public Date getDesdeDer() {
		return desdeDer;
	}

	/**
	 * @return the hastaDer
	 */
	public Date getHastaDer() {
		return hastaDer;
	}

	/**
	 * @return the desdeIzq
	 */
	public Date getDesdeIzq() {
		return desdeIzq;
	}

	/**
	 * @return the hastaIzq
	 */
	public Date getHastaIzq() {
		return hastaIzq;
	}

	/**
	 * @param desdeDer the desdeDer to set
	 */
	public void setDesdeDer(Date desdeDer) {
		this.desdeDer = desdeDer;
	}

	/**
	 * @param hastaDer the hastaDer to set
	 */
	public void setHastaDer(Date hastaDer) {
		this.hastaDer = hastaDer;
	}

	/**
	 * @param desdeIzq the desdeIzq to set
	 */
	public void setDesdeIzq(Date desdeIzq) {
		this.desdeIzq = desdeIzq;
	}

	/**
	 * @param hastaIzq the hastaIzq to set
	 */
	public void setHastaIzq(Date hastaIzq) {
		this.hastaIzq = hastaIzq;
	}

	
}

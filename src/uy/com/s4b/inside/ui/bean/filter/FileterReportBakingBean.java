package uy.com.s4b.inside.ui.bean.filter;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.ejbs.netwwork.EJBNetworkLocal;
import uy.com.s4b.inside.core.entity.Network;
import uy.com.s4b.inside.core.exception.InSideException;
import uy.com.s4b.inside.ui.bean.VersionBakingBean;

/**
 * Title: EvenBakingBean.java <br>
 * Description: <br>
 * Fecha creación: 25/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@ManagedBean(name="fileterReport")
@RequestScoped
public class FileterReportBakingBean {
	
	
	private static final Logger log = Logger.getLogger(FileterReportBakingBean.class);
	
	private String nameDivice;
	
	private String nameNetwork;
	
	private String nameSite;
	
	private String nameZone;
		        
	
	@ManagedProperty(value="#{versionBakingBean}")
	private VersionBakingBean version;
	

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
			retorno.append("Problemas al cargar el árbol de dispositivos");
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

	
}

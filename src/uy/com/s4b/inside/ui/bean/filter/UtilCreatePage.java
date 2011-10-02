package uy.com.s4b.inside.ui.bean.filter;

import java.util.List;

import javax.faces.context.FacesContext;

import uy.com.s4b.inside.core.entity.Device;
import uy.com.s4b.inside.core.entity.Network;
import uy.com.s4b.inside.core.entity.Site;
import uy.com.s4b.inside.core.entity.Zone;

/**
 * Title: UtilCreatePage.java <br>
 * Description: <br>
 * Fecha creación: 01/10/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

public class UtilCreatePage {

	/**
	 * 
	 */
	public UtilCreatePage() {
		
	}

	
	/**
	 * @param retorno
	 * @param listNetwork
	 */
	public void createTree(StringBuffer retorno, List<Network> listNetwork) {
		String nameAPP = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		
		//network
		for (Network network : listNetwork) {
			retorno.append("<tr><td colspan=\"4\" class=\"networks\">");
			retorno.append("<ul class=\"network expand\"><li class=\"more\"><span>" + network.getName() + "</span></li>");

			//site
			retorno.append("<ul class=\"site expand\" style=\"display: none\">");
			for (Site site : network.getColSite()) {
				retorno.append("<li class=\"more\"><span>"+ site.getName() +"</span></li>");
			
				//zona
				retorno.append("<ul class=\"zone expand\" style=\"display: none\">");
				for (Zone zone : site.getColZone()) {
					retorno.append("<li class=\"more\"><span>" + zone.getName() + "</span></li>");	
					// equipo
					retorno.append("<ul class=\"node expand\" style=\"display: none\">");
					for (Device device : zone.getColDevice()) {
						
						retorno.append("<li style=\"background-image: url('"+ nameAPP +"/resources/img/icono2.jpg')\">"); 
						retorno.append("<span><input type=\"checkbox\" name=\"equipos\" id=\"equipos\" value=\""+ device.getId() +"\"/>"+ device.getHostname() + "</span></li>");
						
					}
					retorno.append("</ul>");		
				}
				retorno.append("</ul>");					
			}
			retorno.append("</ul>");				
			retorno.append("</ul></td></tr>");
		}
	}
}

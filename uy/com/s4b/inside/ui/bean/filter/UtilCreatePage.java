package uy.com.s4b.inside.ui.bean.filter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;

import uy.com.s4b.inside.core.common.TypeEvent;
import uy.com.s4b.inside.core.entity.Device;
import uy.com.s4b.inside.core.entity.EventInSide;
import uy.com.s4b.inside.core.entity.Network;
import uy.com.s4b.inside.core.entity.Site;
import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.entity.Zone;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: UtilCreatePage.java <br>
 * Description: <br>
 * Fecha creaci�n: 01/10/2011 <br>
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
	
	
	public void createTreeHome(StringBuffer retorno, List<Network> listNetwork) {
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
						// TODO debemos cabiar por temas del icono
						retorno.append("<li style=\"background-image: url('"+ nameAPP +"/resources/img/icono2.jpg')\">"); 
						retorno.append("<span>"+ device.getHostname() + "</span></li>");
						
						putConfiguration(retorno, device, nameAPP);
					}
					retorno.append("</ul>&nbsp;");
				}
				retorno.append("</ul>");
			}
			retorno.append("</ul>");				
			retorno.append("</ul></td></tr>");
		}
	}


	/**
	 * @param device
	 */
	private void putConfiguration(StringBuffer p, Device device, String nameAPP) {
		Set<Version> listVersino = device.getColVersion();
		int i = 0;
		p.append("<ul class=\"subnode expand\" style=\"display:none\">");
		for (Version version : listVersino) {
			if (i > 1)
				break;
			p.append("<li style=\"background-image: url('#{request.contextPath}/resources/img/R.png')\">");
			p.append("<span><a id=\"VERTODO" + version.getId() + "\" href=\"" + nameAPP + "/site/verReporte.jsf?id="+ version.getId() +"\">");
			p.append(new SimpleDateFormat("dd/MM/yyyy").format(version.getDate().getTime()));
			p.append("</a></span></li>");
			i++;
		}
		p.append("</ul>");		
	}


	/**
	 * @param retorno
	 * @param listaEventos
	 */
	public void createHomeListaEvento(StringBuffer retorno,
			List<EventInSide> listaEventos) throws InSideException {
		String nameAPP = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		retorno.append("<table width=\"420\" class=\"events\" style=\"border-collapse: inherit\">");
		retorno.append("<tr>");
		retorno.append("<th class=\"event\">Evento</th>");
		retorno.append("<th>Info</th>");
		retorno.append("<th>Cuidado</th>");
		retorno.append("<th>Cr�tico</th>");
		retorno.append("</tr>");
		int i = 0;
		for (EventInSide eventInSide : listaEventos) {
			String bloque = "bloque"+i;
			retorno.append("<tr>");
			retorno.append("<td class=\"event links_modal\"><a href=\"#q\" onclick=\"$('#"+ bloque+", #bloque').toggle(); return false;\">"+ eventInSide.getValue() +"</a></td>");
			
			if (eventInSide.getType() == TypeEvent.INFO){
				retorno.append("<td><img src=\""+ nameAPP +"/resources/img/green_event.png\" alt=\"verde\" /></td>");
				retorno.append("<td>&nbsp;</td>");
				retorno.append("<td>&nbsp;</td>");
				
			}else if (eventInSide.getType() == TypeEvent.WARN){				
				retorno.append("<td>&nbsp;</td>");
				retorno.append("<td><img src=\""+ nameAPP +"/resources/img/yellow_event.png\" alt=\"verde\" /></td>");
				retorno.append("<td>&nbsp;</td>");
				
			}else if (eventInSide.getType() == TypeEvent.ERROR){
				retorno.append("<td>&nbsp;</td>");
				retorno.append("<td>&nbsp;</td>");
				retorno.append("<td><img src=\""+ nameAPP +"/resources/img/red_event.png\" alt=\"verde\" /></td>");
			}
			
			retorno.append("</tr>");
			retorno.append("");
			retorno.append("<tr>");
			retorno.append("<td colspan=\"4\" id=\""+bloque+"\" style=\"display:none; color:#999; text-align:left; padding:5px 5px 5px 32px; border:0\"> ");
			retorno.append(eventInSide.getDescription());
			retorno.append("<br />");
			retorno.append("</td>");
			retorno.append("</tr>");
			i++;
		}
		
		retorno.append("</table>");
	}
}

package uy.com.s4b.inside.core.syslog;

/**
 * Title: IPServerRunUDP.java <br>
 * Description: <br>
 * Fecha creación: 16/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

public interface InfoRunServerUDP {

	/* 
	 * Ip del servidor donde levanta jboos, en la misma ip, dejamos escuchando el 
	 * servidor de udp encargado de recibir los msg del los dispositivos.
	 */
	String IP_SERVIDOR = System.getProperty("jboss.bind.address");
	
	/*
	 * Puerto en el cual nos quedamos escuchando los syslog
	 */
	Integer PUERTO_SERVIDOR_UDP =  514;
	
	/* 
	 * Msg enviado para bajar el servidor de UDP
	 */
	String MSG_BAJA_SERVER = "UNDEPLOY APP";
	
}

package uy.com.s4b.inside.ui.listener;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * 
 * Title: LoginFilter.java <br>
 * Description: <br>
 * Fecha creación: 10/09/2010 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
public class LoginFilter implements Filter {

	private final static Logger log = Logger.getLogger(LoginFilter.class);
	
	@SuppressWarnings("unused")
	private FilterConfig config;
	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest =(HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse =(HttpServletResponse) servletResponse;
		HttpSession session = httpRequest.getSession(false);
		httpResponse.addHeader("Pragma", "no-cache");
		httpResponse.addHeader("Cache-Control", "no-cache");
		httpResponse.setHeader("Cache-Control", "no-store");
		httpResponse.addHeader("Cache-Control", "must-revalidate");
		httpResponse.addHeader("Expires", "Mon,8 Aug 2006 10:00:00 GMT");
		StringBuffer path = httpRequest.getRequestURL();
		
		if (session != null){
			if ((!session.isNew()) && (session.getAttribute("login") != null)){
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}else{
				log.error("Es nueva la session ---> " + session.isNew());
				log.error("Esta intentado acceder a: \"" + path  +  "\" - Desde la ip: \"" + httpRequest.getRemoteHost() + "\"");
				httpResponse.sendRedirect(httpRequest.getContextPath());
			}
		}else{
			log.error("Esta intentado acceder a: \"" + path  +  "\" - Desde la ip: \"" + httpRequest.getRemoteHost() + "\"");
			httpResponse.sendRedirect(httpRequest.getContextPath());
		}
	}

	/**
	 * 
	 */
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
}

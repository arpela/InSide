package uy.com.s4b.inside.ui.bean;

import java.io.Serializable;
import java.util.Random;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.ejbs.manager.EJBManagerUserLocal;
import uy.com.s4b.inside.core.entity.User;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: LoginBakingBean.java <br>
 * Description: <br>
 * Fecha creaci�n: 08/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@ManagedBean(name="login")
@SessionScoped
public class LoginBakingBean implements Serializable {

	
	
	private User userBean;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1110892548731141861L;

	/**
	 * 
	 */
	public LoginBakingBean() {
		userBean = new User();
	}
	
	/**
	 * @param userBean the userBean to set
	 */
	public void setUserBean(User userBean) {
		this.userBean = userBean;
	}
	
	
	/**
	 * @return the userBean
	 */
	public User getUserBean() {
		return userBean;
	}
	
	
	/**
	 * Informa si el usuario esta logueado
	 * @return
	 */
	public boolean isLoginIn(){
		return ((userBean.getName() != null));
	}
}

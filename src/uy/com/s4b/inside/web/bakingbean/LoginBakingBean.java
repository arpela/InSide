package uy.com.s4b.inside.web.bakingbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import uy.com.s4b.inside.core.entity.User;

/**
 * Title: LoginBakingBean.java <br>
 * Description: <br>
 * Fecha creación: 08/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@ManagedBean(name="login")
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

}

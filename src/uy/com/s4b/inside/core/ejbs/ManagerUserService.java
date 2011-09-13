package uy.com.s4b.inside.core.ejbs;

import uy.com.s4b.inside.core.entity.User;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: ManagerUserService.java <br>
 * Description: <br>
 * Fecha creación: 08/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

public interface ManagerUserService {

	User login(String name, String pass) throws InSideException;
	
	void saveUser(User u) throws InSideException;
	
}

package uy.com.s4b.inside.core.ejbs.command;

import java.util.List;

import uy.com.s4b.inside.core.common.TypeCommand;
import uy.com.s4b.inside.core.entity.Command;
import uy.com.s4b.inside.core.entity.Ios;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * 
 * Title: EJBNetworkLocal.java <br>
 * Description: <br>
 * Fecha creaci�n: 21/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
public interface CommandService {

	
	/**
	 * @param id
	 * @return
	 * @throws InSideException
	 */
	Command getCommand(Integer id) throws InSideException;
	
	
	/**
	 * 
	 * @param d
	 * @throws InSideException
	 */
	void save(Command d) throws InSideException;
	
	
	public List<Command> getListCommandForIosAndType(Ios ios, TypeCommand t) throws InSideException ;
}

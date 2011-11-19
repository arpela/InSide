package uy.com.s4b.inside.test;

import uy.com.s4b.inside.core.ssh.ClientSSH;

/**
 * Title: TestSSH.java <br>
 * Description: <br>
 * Fecha creación: 03/11/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */
public class TestSSH {
	
	
	public static void main(String[] args) {
		String hostname = "10.1.1.1";
		String username = "admin";
		String password = "npi2011";
		String passwordEXEC = "npi2011";
		String s;

		try {
			ClientSSH cliSSH = new ClientSSH(hostname, username, password, passwordEXEC);
			cliSSH.connect();

			s = cliSSH.execCommand("enable");
			s = cliSSH.execCommand("copy running-config tftp://10.1.1.2/conf7.cfg");
			s = cliSSH.execCommand("\n");
			System.out.println(s);
			cliSSH.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}
}
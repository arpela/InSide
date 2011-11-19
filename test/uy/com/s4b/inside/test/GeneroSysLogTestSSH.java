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
public class GeneroSysLogTestSSH {
	
	
	public static void main(String[] args) {
		String hostname = "10.1.1.1";
		String username = "admin";
		String password = "npi2011";
		String passwordEXEC = "npi2011";
		String s;

		try {
			for (int i = 0; i < 100; i++) {
				System.out.println("Largo ejecucion nro: " + i);
				ClientSSH cliSSH = new ClientSSH(hostname, username, password, passwordEXEC);
				cliSSH.connect();
				
				s = cliSSH.execCommand("enable");
				s = cliSSH.execCommand("clock set 15:15:15 Nov 10 2011");
				s = cliSSH.execCommand("\n");
//				s = cliSSH.execCommand("exit");
				
				System.out.println(s);
				cliSSH.disconnect();
				Thread.sleep(600000);
				System.out.println("Despierto la ejeucion: " + i);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}
}
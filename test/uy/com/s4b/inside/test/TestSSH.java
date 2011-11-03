package uy.com.s4b.inside.test;

import uy.com.s4b.inside.core.ssh.ClientSSH;





public class TestSSH
{
	public static void main(String[] args)
	{
		String hostname = "192.168.1.150";
		String username = "admin";
		String password = "npi2011";
		String passwordEXEC = "admin";
		String s;

		try {
			ClientSSH cliSSH = new ClientSSH(hostname, username, password, passwordEXEC);
			cliSSH.connect();
			
			s = cliSSH.execCommand("enable");
			s = cliSSH.execCommand("copy running-config tftp://192.168.1.120/conf3.cfg");
			s = cliSSH.execCommand("\n");
			System.out.println(s);
		
			cliSSH.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}
}
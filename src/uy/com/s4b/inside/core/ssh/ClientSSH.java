package uy.com.s4b.inside.core.ssh;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.session.SessionOutputReader;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;

import uy.com.s4b.inside.core.exception.InSideException;


/**
 * Title: ClientSSH.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 02/11/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */

public class ClientSSH {
	
	private static final Log log = LogFactory.getLog(ClientSSH.class);
	
	private String hostname;
	private String username;
	private String password;
	private String passwordEXEC;
	SshClient ssh = null;
	SessionChannelClient session = null;
	SessionOutputReader sor = null;
	
	public ClientSSH(String hostname, String username, String password, String passwordEXEC) {
		super();
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		this.passwordEXEC = passwordEXEC;
	}

	public void connect() throws InSideException {
		try {
			ssh = new SshClient();  
			ssh.connect(hostname, new IgnoreHostKeyVerification());
			
			/* Authenticate.
			 * If you get an IOException saying something like
			 * "Authentication method password not supported by the server at this stage."
			 */
			PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
			pwd.setUsername(username);
			pwd.setPassword(password);
			
			int result = ssh.authenticate(pwd);
				
			if (result != AuthenticationProtocolState.COMPLETE){				
				throw new InSideException("Login to " + hostname + " " + username + "/" + password + " failed");
			} 
				
			session = 	ssh.openSessionChannel();
			sor = new SessionOutputReader(session);
			// Request a pseudo terminal, if you do not you may not see the prompt
			session.requestPseudoTerminal("gogrid",80,48, 0 , 0, "");
			// Start the users shell
			session.startShell();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new InSideException("Login to " + hostname + " " + username + "/" + password + " failed");
		}
		
	}
	
	
	public void disconnect() throws InSideException {
		try {
			session.close();
			ssh.disconnect();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new InSideException("Disconnection failed");
		}
	}
	
	public String execCommand(String command) throws InSideException {
		try {
			String res = "";
			if (session!=null && sor!=null){
				int outputPos = 0 ;
				String answer = null;
				String aux = null;
								
				session.getOutputStream().write((command+"\n").getBytes());
				
				Thread.sleep(1000*2);
				aux = sor.getOutput();   
				answer = aux.substring(outputPos);
				outputPos = aux.length();
				
				String str[] = answer.split("\n");
				String prompt = str[str.length-1];   // get the last prompt
				prompt = prompt.trim().toUpperCase();
				if (prompt.contains("PASSWORD")){	// if the last prompt is "password 
													// then we input password.
					session.getOutputStream().write((passwordEXEC+"\n").getBytes());
					prompt = "";
				} else {
					prompt = str[str.length-2]; 
				}
				res = prompt;
			}
				
			return res;
		} catch (IOException eio) {
			log.error(eio.getMessage(), eio);
			throw new InSideException("ExecCommand failed");
		} catch (InterruptedException ei) {
			log.error(ei.getMessage(), ei);
			throw new InSideException("ExecCommand failed");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new InSideException("ExecCommand failed");
		}
	}
	

}

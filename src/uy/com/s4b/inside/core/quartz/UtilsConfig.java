package uy.com.s4b.inside.core.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.common.CriptPassword;
import uy.com.s4b.inside.core.common.TypeCommand;
import uy.com.s4b.inside.core.common.TypeConfig;
import uy.com.s4b.inside.core.common.TypeEvent;
import uy.com.s4b.inside.core.common.UtilsString;
import uy.com.s4b.inside.core.ejbs.command.EJBCommandLocal;
import uy.com.s4b.inside.core.ejbs.event.EJBEventInSideLocal;
import uy.com.s4b.inside.core.ejbs.report.EJBReportDiffLocal;
import uy.com.s4b.inside.core.ejbs.report.impl.difflib.Delta;
import uy.com.s4b.inside.core.ejbs.version.EJBVersionLocal;
import uy.com.s4b.inside.core.entity.Command;
import uy.com.s4b.inside.core.entity.Device;
import uy.com.s4b.inside.core.entity.EventInSide;
import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.exception.InSideException;
import uy.com.s4b.inside.core.ssh.ClientSSH;
import uy.com.s4b.inside.core.syslog.InfoRunServerUDP;

/**
 * Title: UtilsConfig.java <br>
 * Description: <br>
 * Fecha creaciï¿½n: 02/11/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

public class UtilsConfig {

	
	private static final Logger log = Logger.getLogger(UtilsConfig.class);
	
	
	/**
	 * 
	 */
	public UtilsConfig() {

	}

	
	public Object getLocalsObject(String name){
		try {
			Context ctx = new InitialContext();
			Object retorno = ctx.lookup(name);
			return retorno;
		} catch (NamingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * Salva un evento del tipo error cuando no se pudo obtenr alguna configuracion.
	 * @param ipEquipo
	 */
	public void saveEventErrorReadConfi(String ipEquipo){
		try {
			EJBEventInSideLocal ejbEvent = (EJBEventInSideLocal)getLocalsObject("inSide/EJBEventInSide/local");
			SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, +2);
			EventInSide event = new EventInSide();
			event.setDeadline(cal);
			event.setDescription("["+ formatdate.format(Calendar.getInstance().getTime()) +"] La tarea Obtener configuración " +
					"para el dispositivo \""+ ipEquipo +"\" no se ejecutó");
			event.setType(TypeEvent.ERROR);
			event.setValue("Tarea agendada no ejecutada");
			ejbEvent.saveEvent(event);
		} catch (InSideException ex) {
			log.error(ex.getMessage(),ex);
		}
	}
	
	public void saveErrorDiffStartRunning(String nombre){
		try {
			EJBEventInSideLocal ejbEvent = (EJBEventInSideLocal)getLocalsObject("inSide/EJBEventInSide/local");
			SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, +2);
			EventInSide event = new EventInSide();
			event.setDeadline(cal);
			event.setDescription("["+ formatdate.format(Calendar.getInstance().getTime()) +"] " +
					"Hay diferencias en la configuración Running y StartUP.");
			event.setType(TypeEvent.ERROR);
			event.setValue("["+ nombre +"] Diferencia en Startup/Running");
			ejbEvent.saveEvent(event);
		} catch (InSideException ex) {
			log.error(ex.getMessage(),ex);
		}
	}
	
	
	public void moveFile(File oneFile) {
		String nameFile = oneFile.getPath() + "_" + new SimpleDateFormat
				("dd-MM-yyyy HHmmss").format(Calendar.getInstance().getTime());
		File dest = new File(nameFile);
		oneFile.renameTo(dest);
	}
	
	/**
	 * @param oneFile
	 * @param ejbVersion 
	 * @param typoConf 
	 * @param ejbDevices
	 * @return
	 */
	public boolean tengoDiferencias(StringBuffer oneFile, EJBVersionLocal ejbVersion, Integer idDevice, TypeConfig typoConf) {
		boolean resultado = false;
		try {
			Version versionBD  = ejbVersion.getVersionDevice(idDevice, typoConf);
			if (versionBD == null){
				resultado = true;
			} else {
				try {
					EJBReportDiffLocal ejbDiff = (EJBReportDiffLocal)new InitialContext().lookup("inSide/EJBReportDiff/local");
					Map<Integer, Delta> estructura = ejbDiff.doDiff(versionBD.getConfig(), oneFile.toString());
					if (tengoDiferencias(estructura, versionBD.getConfig().split("\n"),"!")){
						resultado = true;
					}
				} catch (NamingException ex) {
					log.error(ex.getMessage(), ex);
				}
			}
		} catch (InSideException ex) {
			log.info(ex.getMessage(), ex);
		}
		return resultado;
	}
	
	/**
	 * @param files
	 * @return
	 * @throws InSideException 
	 */
	public boolean tieneDiferenciasRunninStarup(File[] files) throws InSideException {
		boolean resultado = false;
		if (files.length == 2){
			if (files[0] == null){
				resultado = false;
			} else if (files[1] == null){
				resultado = false;
			} else {
				try {
					EJBReportDiffLocal ejbDiff = (EJBReportDiffLocal)new InitialContext().lookup("inSide/EJBReportDiff/local");
					StringBuffer running = getFile(files[0]);
					StringBuffer starup = getFile(files[1]);
					Map<Integer, Delta> estructura = ejbDiff.doDiff(running.toString(), starup.toString());
					if (tengoDiferencias(estructura, running.toString().split("\n"),"!")){
						resultado = true;
					}
				} catch (NamingException ex) {
					log.error(ex.getMessage(), ex);
				}
			}
		}
		return resultado;
	}
	
	
	public StringBuffer getFile(File oneFile) throws InSideException {
		StringBuffer retorno = new StringBuffer();
		try {
			BufferedReader r = new BufferedReader(new FileReader(oneFile));
			String linea = r.readLine();
			while (linea != null){
				retorno.append(linea);
				retorno.append("\n");
				linea = r.readLine();
			}
			r.close();
			return retorno;
		} catch (FileNotFoundException e) {			
			throw new InSideException(e.getMessage(), e);
		} catch (IOException e) {
			throw new InSideException(e.getMessage(), e);
		}
	}
	
	
	public boolean tengoDiferencias(Map<Integer, Delta> estructura, String[] listaLinea, String... listIgnoreLine){
		boolean resIgnoreLine;
		boolean resultado = false;
		for (int i = 0; i < listaLinea.length; i++) {
			//se busca si se tiene que ignorar el comando de la linea
			resIgnoreLine = UtilsString.searchTextInList(listaLinea[i], listIgnoreLine);
			if (estructura.containsKey(i) && !resIgnoreLine){
				switch (estructura.get(i).getType()) {
					case INSERT:
						System.out.println("hay insert");
						resultado =true;
						break;
					case CHANGE:
						System.out.println("hay cambio");
						resultado =true;
						break;
					case DELETE:
						System.out.println("hay delete");
						resultado =true;
						break;
				}
			}
		}
		return resultado;
	}
	
	public File ejecutarComando(Device device, TypeConfig tipCon, TypeCommand typeCommand) throws Exception{
		log.info(" ########################################################### ");
		log.info(" ################## Ejecucion de comando ################### ");
		String pathFiles = System.getProperties().getProperty("uy.com.s4b.incos.inside.pathtftpfiles");
		String ipTFTP = InfoRunServerUDP.IP_SERVIDOR;
		
		EJBCommandLocal service =  (EJBCommandLocal) getLocalsObject("inSide/EJBCommandBean/local");
		String password = new CriptPassword().desencripta(device.getPassword());
		String passwordEXEC = new CriptPassword().desencripta(device.getSnmpPassword());
		ClientSSH cliSSH = new ClientSSH(device.getIp(), device.getUser(), password, passwordEXEC);
		try {
			cliSSH.connect();
			String fileName = tipCon + "-" + device.getIp() + ".cfg";
			List<Command> l = service.getListCommandForIosAndType(device.getIos(), typeCommand);
			String s = null;
			for (Command command : l) {
				String valueCommand = "";
				if (command.getValue().contains("copy")){
					valueCommand = command.getValue() + " tftp://" + ipTFTP + "/" + fileName;
				}else{
					valueCommand = command.getValue();
				}
				log.info("comando en crudo ---> " + command.getValue() + " resultado ---> " + valueCommand);
				s = cliSSH.execCommand(valueCommand);
			}
			log.info("Resultado de la ejecucion del comando: " + s);
			return new File(pathFiles + "/" + fileName);
		} finally {
			cliSSH.disconnect();
			log.info(" ########################################################### ");
		}
	}


	/**
	 * @param retorno
	 * @throws InterruptedException 
	 */
	public void hacerWaitArchivo(File[] retorno) throws InterruptedException {
		// Espero por el TFTP que tiene una demora.
		for (int i = 0; i < retorno.length; i++) {
			int j = 0;
			while ((!retorno[i].exists()) && (j < 5)){
				Thread.sleep(1000*6);
				j++;
			}
		}
	}
	

}

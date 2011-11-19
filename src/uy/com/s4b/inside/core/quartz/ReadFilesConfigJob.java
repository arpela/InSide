package uy.com.s4b.inside.core.quartz;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;

import uy.com.s4b.inside.core.common.CriptPassword;
import uy.com.s4b.inside.core.common.TypeCommand;
import uy.com.s4b.inside.core.common.TypeConfig;
import uy.com.s4b.inside.core.ejbs.command.EJBCommandLocal;
import uy.com.s4b.inside.core.ejbs.device.EJBDeviceLocal;
import uy.com.s4b.inside.core.ejbs.version.EJBVersionLocal;
import uy.com.s4b.inside.core.entity.Command;
import uy.com.s4b.inside.core.entity.Device;
import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.exception.InSideException;
import uy.com.s4b.inside.core.ssh.ClientSSH;
import uy.com.s4b.inside.core.syslog.InfoRunServerUDP;

/**
 * Title: Test1.java <br>
 * Description: <br>
 * Fecha creaci�n: 30/10/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * 
 * @author Alfredo
 * 
 */

public class ReadFilesConfigJob implements org.quartz.Job {
	
	
	private static final Logger log = Logger.getLogger(ReadFilesConfigJob.class);
	
	
	public ReadFilesConfigJob() {
		
	}

	
	public void execute(org.quartz.JobExecutionContext jobExecutionContext) throws org.quartz.JobExecutionException {
		log.info("Se ejecuta un lectura de configuracion: " + new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime()));
		boolean finalizoError = false;
		UtilsConfig util = new UtilsConfig();
		try {
			String ipHost = (String)jobExecutionContext.getMergedJobDataMap().get("host");
			try {
				EJBDeviceLocal ejbDevices = (EJBDeviceLocal)util.getLocalsObject("inSide/EJBDevice/local");
				Device oneDevice = ejbDevices.getDeviceByIP(ipHost);
				
				File files [] = executeCommand(oneDevice, util);
				
				// TODO esta deshabilitado
	//			files = util.tieneDiferenciasRunninStarup(files);
				
				for (int i = 0; i < files.length; i++) {
					File oneFile = files[i];
					log.info("Archivo: " + oneFile.getName());
					log.info("Equipo: " + ipHost);
					EJBVersionLocal ejbVersion = (EJBVersionLocal)util.getLocalsObject("inSide/EJBVersion/local");
					if (oneFile.exists()){
						StringBuffer file = util.getFile(oneFile);
						if (util.tengoDiferencias (file, ejbVersion, oneDevice.getId())){
							Version oneVersion = new Version();
							oneVersion.setConfig(file.toString());
							oneVersion.setDate(Calendar.getInstance());
							oneVersion.setOneDevice(oneDevice);
							oneVersion.setType((oneFile.getName().startsWith("Runn")?TypeConfig.Running:TypeConfig.StartUp));
							ejbVersion.save(oneVersion);
						}
						util.moveFile(oneFile);
					}else{
						util.salvarEventoERRORReadConfi(ipHost);
					}
				}
			}finally{
				if (finalizoError){
					util.salvarEventoERRORReadConfi(ipHost);
				}
				/* Parada del job en ejecucion lo saco */
				Scheduler scheduler = jobExecutionContext.getScheduler();
				scheduler.deleteJob(jobExecutionContext.getJobDetail().getName(), jobExecutionContext.getJobDetail().getGroup());
			}
		} catch (InSideException ex) {
			finalizoError = true;
			log.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			finalizoError = true;
			log.error(ex.getMessage(), ex);
		}
	}

	
	/**
	 * @param jobExecutionContext
	 * @return 
	 */
	private File [] executeCommand(Device device, UtilsConfig util) {
		File [] retorno = new File [2];
		
		String pathFiles = System.getProperties().getProperty("uy.com.s4b.incos.inside.pathtftpfiles");
		String ipTFTP = InfoRunServerUDP.IP_SERVIDOR;
		
		try {
			String password = new CriptPassword().desencripta(device.getPassword());
			String passwordEXEC = new CriptPassword().desencripta(device.getSnmpPassword());
			String s = null;
			log.info(" ############################################# ");
			ClientSSH cliSSH = new ClientSSH(device.getIp(), device.getUser(), password, passwordEXEC);
			cliSSH.connect();
			
			EJBCommandLocal service =  (EJBCommandLocal) util.getLocalsObject("inSide/EJBCommandBean/local");
			
			// TODO todo esto puede y debe ser mejoradooooooooooooooooooo 
			
			// Running
			String fileName = TypeConfig.Running + "-" + device.getIp() + ".cfg";
			List<Command> l = service.getListCommandForIosAndType(device.getIos(), TypeCommand.GET_CONFIG_RUNNING);
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
			retorno[0] = new File(pathFiles + "/" + fileName);
			
			//Startup
			fileName = TypeConfig.StartUp + "-" +  device.getIp() + ".cfg";
			l = service.getListCommandForIosAndType(device.getIos(), TypeCommand.GET_CONFIG_STARTUP);
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
			retorno[1] = new File(pathFiles + "/" + fileName);
			
			log.info("Resultado de la ejecucion del comando: " + s);
			cliSSH.disconnect();
			
			hacerWaitArchivo(retorno);
			
			log.info(" ############################################# ");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return retorno;
	}


	/**
	 * @param retorno
	 * @throws InterruptedException 
	 */
	private void hacerWaitArchivo(File[] retorno) throws InterruptedException {
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
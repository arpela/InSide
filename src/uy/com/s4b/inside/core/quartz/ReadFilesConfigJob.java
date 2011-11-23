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
				if(util.tieneDiferenciasRunninStarup(files)){
					util.saveErrorDiffStartRunning(oneDevice.getIp());
				}
				
				for (int i = 0; i < files.length; i++) {
					File oneFile = files[i];
					log.info("Archivo: " + oneFile.getName());
					log.info("Equipo: " + ipHost);
					EJBVersionLocal ejbVersion = (EJBVersionLocal)util.getLocalsObject("inSide/EJBVersion/local");
					if (oneFile.exists()){
						StringBuffer file = util.getFile(oneFile);
						TypeConfig typoConf = (oneFile.getName().startsWith("Runn")?TypeConfig.Running:TypeConfig.StartUp);
						if (util.tengoDiferencias (file, ejbVersion, oneDevice.getId(), typoConf)){
							Version oneVersion = new Version();
							oneVersion.setConfig(file.toString());
							oneVersion.setDate(Calendar.getInstance());
							oneVersion.setOneDevice(oneDevice);
							oneVersion.setType(typoConf);
							ejbVersion.save(oneVersion);
						}
						util.moveFile(oneFile);
					}else{
						util.saveEventErrorReadConfi(ipHost);
					}
				}
			}finally{
				if (finalizoError){
					util.saveEventErrorReadConfi(ipHost);
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
		try {
			retorno[0] = util.ejecutarComando(device, TypeConfig.Running , TypeCommand.GET_CONFIG_RUNNING);
			retorno[1] = util.ejecutarComando(device, TypeConfig.StartUp , TypeCommand.GET_CONFIG_STARTUP);
			util.hacerWaitArchivo(retorno);
		} catch (Exception e) {
			log.error("Error al ejecutar comando....", e);
		}
		return retorno;
	}

}
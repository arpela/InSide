package uy.com.s4b.inside.core.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.common.TypeEvent;
import uy.com.s4b.inside.core.ejbs.event.EJBEventInSideLocal;
import uy.com.s4b.inside.core.ejbs.report.EJBReportDiffLocal;
import uy.com.s4b.inside.core.ejbs.report.impl.difflib.Delta;
import uy.com.s4b.inside.core.ejbs.version.EJBVersionLocal;
import uy.com.s4b.inside.core.entity.EventInSide;
import uy.com.s4b.inside.core.entity.Version;
import uy.com.s4b.inside.core.exception.InSideException;

/**
 * Title: UtilsConfig.java <br>
 * Description: <br>
 * Fecha creaci�n: 02/11/2011 <br>
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
	public void salvarEventoERRORReadConfi(String ipEquipo){
		try {
			EJBEventInSideLocal ejbEvent = (EJBEventInSideLocal)getLocalsObject("inSide/EJBEventInSide/local");
			SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, +2);
			EventInSide event = new EventInSide();
			event.setDeadline(cal);
			event.setDescription("["+ formatdate.format(Calendar.getInstance().getTime()) +"] La tarea Obtener configuracion para el dispositivo \""+ 
					ipEquipo +"\" no se ejecut�");
			event.setType(TypeEvent.ERROR);
			event.setValue("Tarea agendada no ejecutada");
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
	 * @param ejbDevices
	 * @return
	 */
	public boolean tengoDiferencias(StringBuffer oneFile, EJBVersionLocal ejbVersion, Integer idDevice) {
		try {
			Version dosUltimasVersiones [] = ejbVersion.getVersionDosDevice(idDevice);
			if (dosUltimasVersiones == null)
				return true;
			
			EJBReportDiffLocal ejbDiff =  (EJBReportDiffLocal)getLocalsObject("inSide/EJBReportDiff/local");
			if (dosUltimasVersiones != null){
				if (dosUltimasVersiones[0] == null)
					return true;
				
				Map<Integer, Delta> estructura = ejbDiff.doDiff(dosUltimasVersiones[0].getConfig(), oneFile.toString());
				return (estructura.size() > 0);
			}
		} catch (InSideException ex) {
			log.info(ex.getMessage(), ex);
		}
		return true;
	}
	
	/**
	 * @param files
	 * @return
	 * @throws InSideException 
	 */
	public File[] tieneDiferenciasRunninStarup(File[] files) throws InSideException {
		
		if (files.length == 2){
			if (files[0] == null){
				// todo salvo informacion no pude obtern running
			} else if (files[1] == null){
				// todo salvo informacion no pude obtern startup
			} else {
//				StringBuffer running = getFile(files[0]);
//				StringBuffer starup = getFile(files[1]);
//				if ()
			}
		}
		return files;
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

}

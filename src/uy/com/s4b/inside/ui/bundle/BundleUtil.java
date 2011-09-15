package uy.com.s4b.inside.ui.bundle;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

/**
 * Title: BundleUtil.java <br>
 * Description: Clase para servicio para levantar el bundel correspondiente a Local<br>
 * Fecha creación: 23/02/2009 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * 
 * @author Alfredo
 * 
 */
public class BundleUtil {

	private static final Logger log = Logger.getLogger(BundleUtil.class);
	
	/**
	 * @param key
	 * @param arguments
	 * @return
	 */
	public static String getMessageResource(String key, Object[] arguments) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String messageBundleName = facesContext.getApplication()
					.getMessageBundle();
			Locale locale = facesContext.getViewRoot().getLocale();
			ResourceBundle bundle = ResourceBundle.getBundle(messageBundleName, locale);
			String resourceString = bundle.getString(key);
			MessageFormat format = new MessageFormat(resourceString, facesContext.getViewRoot().getLocale());
			return format.format(arguments);
		} catch (MissingResourceException e) {
			log.error("###########################");
			log.error("Error al levantar la key: " + key + "\n" + e.getMessage(), e);
			log.error("###########################");
			return "Key no localiza...";
		}
	}

	/**
	 * @param key
	 * @return
	 */
	public static String getMessageResource(String key) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		try {
			String nameFileI18N = facesContext.getApplication().getMessageBundle();
			ResourceBundle bundle = ResourceBundle.getBundle(nameFileI18N, facesContext.getViewRoot().getLocale());
			return bundle.getString(key);
			
		} catch (MissingResourceException e) {
			log.error("###########################");
			log.error("Error al levantar la key: " + key + "\n" + e.getMessage(), e);
			log.error("###########################");
			return "Key no localiza...";
		}
	}

}

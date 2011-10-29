package uy.com.s4b.inside.core.entity;

import java.io.Serializable;
import javax.persistence.Transient;


/**
 * Title: DeviceModulePK.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 24/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */

public class DeviceModulePK implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 7386829004255252621L;
	
	private Device device;
	private Module module;

	/**
	 * 
	 */
	public DeviceModulePK(Device device, Module module) {
		this.device = device;
		this.module = module;
		
	}

	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * @return the module
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(Module module) {
		this.module = module;
	}
	
		
	@Override
	public boolean equals(Object o) { 
        return ((o instanceof DeviceModulePK) && 
    		device.getId().equals(((DeviceModulePK)o).getDevice().getId()) &&
    		module.getId().equals(((DeviceModulePK) o).getModule().getId()));
    }
	
	@Override
    public int hashCode() { 
        return device.getId() + module.getId();

    }
}

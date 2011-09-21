package uy.com.s4b.inside.core.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Title: DeviceModul.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 20/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */
@Table(name="devicemodule")
@Entity
public class DeviceModule implements Serializable {

	
	@Transient
	private static final long serialVersionUID = -1339546239144397948L;
	
	
	@Id
	@ManyToOne(optional=false)
	@JoinColumn(name="deviceId") 
	private Device device;
	
	@Id
	@ManyToOne(optional=false)
	@JoinColumn(name="moduleId") 
	private Module module;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private Date dateInstall;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private Date dateUnInstall;
	
	
	/**
	 * 
	 */
	public DeviceModule() {
		
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

	/**
	 * @return the dateInstall
	 */
	public Date getDateInstall() {
		return dateInstall;
	}

	/**
	 * @param dateInstall the dateInstall to set
	 */
	public void setDateInstall(Date dateInstall) {
		this.dateInstall = dateInstall;
	}

	/**
	 * @return the dateUnInstall
	 */
	public Date getDateUnInstall() {
		return dateUnInstall;
	}

	/**
	 * @param dateUnInstall the dateUnInstall to set
	 */
	public void setDateUnInstall(Date dateUnInstall) {
		this.dateUnInstall = dateUnInstall;
	}
	
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

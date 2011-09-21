/**
 * 
 */
package uy.com.s4b.inside.core.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * Title: Device.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 15/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */

@Table(name="device")
@Entity
public class Device implements Serializable {

	@Transient
	private static final long serialVersionUID = 7124486562837643664L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	
	@Column(insertable=true, nullable=false, unique=false)
	private String hostname;
	
	
	@Column(insertable=true, nullable=false, unique=false)
	private String ip;
	
	
	@ManyToOne(optional=false)
	@JoinColumn(name="modelId") 
	private Model model;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String serialNumber;
	

	@ManyToOne(optional=false)
	@JoinColumn(name="iosId") 
	private Ios ios;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String user;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String password;
	
	
	@OneToMany(cascade=CascadeType.DETACH)
	private Set<InterfaceDevice> colInterfaceDevice;
	
	
	@OneToMany(cascade=CascadeType.DETACH)
	private Set<Version> colVersion;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String snmpId;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String snmpComunidadR;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String snmpVersion;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String snmpPassword;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	@Lob
	private String notes;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private Date dateCreate;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private Timestamp dateModified;

		
	@OneToMany(cascade=CascadeType.DETACH)
	private Set<DeviceModule> colDeviceModule;
	
	
	/**
	 * 
	 */
	public Device() {
		
	}

	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}


	/**
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}


	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}


	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}


	/**
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}


	/**
	 * @param model the model to set
	 */
	public void setModel(Model model) {
		this.model = model;
	}


	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}


	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}


	/**
	 * @return the ios
	 */
	public Ios getIos() {
		return ios;
	}


	/**
	 * @param ios the ios to set
	 */
	public void setIos(Ios ios) {
		this.ios = ios;
	}


	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the interfaceDevice
	 */
	public Set<InterfaceDevice> getColInterfaceDevice() {
		return colInterfaceDevice;
	}


	/**
	 * @param interfaceDevice the interfaceDevice to set
	 */
	public void setColInterfaceDevice(Set<InterfaceDevice> colInterfaceDevice) {
		this.colInterfaceDevice = colInterfaceDevice;
	}


	/**
	 * @return the colVersion
	 */
	public Set<Version> getColVersion() {
		return colVersion;
	}


	/**
	 * @param colVersion the colVersion to set
	 */
	public void setColVersion(Set<Version> colVersion) {
		this.colVersion = colVersion;
	}


	/**
	 * @return the snmpId
	 */
	public String getSnmpId() {
		return snmpId;
	}


	/**
	 * @param snmpId the snmpId to set
	 */
	public void setSnmpId(String snmpId) {
		this.snmpId = snmpId;
	}


	/**
	 * @return the snmpComunidadR
	 */
	public String getSnmpComunidadR() {
		return snmpComunidadR;
	}


	/**
	 * @param snmpComunidadR the snmpComunidadR to set
	 */
	public void setSnmpComunidadR(String snmpComunidadR) {
		this.snmpComunidadR = snmpComunidadR;
	}


	/**
	 * @return the snmpVersion
	 */
	public String getSnmpVersion() {
		return snmpVersion;
	}


	/**
	 * @param snmpVersion the snmpVersion to set
	 */
	public void setSnmpVersion(String snmpVersion) {
		this.snmpVersion = snmpVersion;
	}


	/**
	 * @return the snmpPassword
	 */
	public String getSnmpPassword() {
		return snmpPassword;
	}


	/**
	 * @param snmpPassword the snmpPassword to set
	 */
	public void setSnmpPassword(String snmpPassword) {
		this.snmpPassword = snmpPassword;
	}


	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}


	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}


	/**
	 * @return the dateCreate
	 */
	public Date getDateCreate() {
		return dateCreate;
	}


	/**
	 * @param dateCreate the dateCreate to set
	 */
	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}


	/**
	 * @return the dateModified
	 */
	public Timestamp getDateModified() {
		return dateModified;
	}


	/**
	 * @param dateModified the dateModified to set
	 */
	public void setDateModified(Timestamp dateModified) {
		this.dateModified = dateModified;
	}


	/**
	 * @return the colDeviceModule
	 */
	public Set<DeviceModule> getColDeviceModule() {
		return colDeviceModule;
	}


	/**
	 * @param colDeviceModule the colDeviceModule to set
	 */
	public void setColDeviceModule(Set<DeviceModule> colDeviceModule) {
		this.colDeviceModule = colDeviceModule;
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

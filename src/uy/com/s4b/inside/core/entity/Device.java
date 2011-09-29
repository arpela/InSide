package uy.com.s4b.inside.core.entity;



import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	
	
//	@ManyToOne(optional=false)
//	@JoinColumn(name="modelId") 
//	private Model model;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String serialNumber;
	

//	@ManyToOne(optional=false)
//	@JoinColumn(name="iosId") 
//	private Ios ios;
//	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String user;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String password;
	
	
	@OneToMany(cascade=CascadeType.DETACH)
	@JoinColumn(name="deviceId")
	private Set<InterfaceDevice> colInterfaceDevice;
	
	
//	@OneToMany(cascade=CascadeType.DETACH)
//	@JoinColumn(name="deviceId")
//	private Set<Version> colVersion;
	
	@OneToMany(cascade=CascadeType.MERGE, mappedBy="oneDevice")
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
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateCreate;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateModified;

		
//	@OneToMany(cascade=CascadeType.DETACH)
//	@JoinColumn(name="deviceId")
//	private Set<DeviceModule> colDeviceModule;
	
	
	/**
	 * 
	 */
	public Device() {
		
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



	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}



	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}



	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}





	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}




	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}



	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}



	/**
	 * @return the colInterfaceDevice
	 */
	public Set<InterfaceDevice> getColInterfaceDevice() {
		return colInterfaceDevice;
	}



	/**
	 * @return the colVersion
	 */
	public Set<Version> getColVersion() {
		return colVersion;
	}



	/**
	 * @return the snmpId
	 */
	public String getSnmpId() {
		return snmpId;
	}



	/**
	 * @return the snmpComunidadR
	 */
	public String getSnmpComunidadR() {
		return snmpComunidadR;
	}



	/**
	 * @return the snmpVersion
	 */
	public String getSnmpVersion() {
		return snmpVersion;
	}



	/**
	 * @return the snmpPassword
	 */
	public String getSnmpPassword() {
		return snmpPassword;
	}



	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}



	/**
	 * @return the dateCreate
	 */
	public Calendar getDateCreate() {
		return dateCreate;
	}



	/**
	 * @return the dateModified
	 */
	public Calendar getDateModified() {
		return dateModified;
	}







	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}



	/**
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}



	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}




	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}



	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}



	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}



	/**
	 * @param colInterfaceDevice the colInterfaceDevice to set
	 */
	public void setColInterfaceDevice(Set<InterfaceDevice> colInterfaceDevice) {
		this.colInterfaceDevice = colInterfaceDevice;
	}



	/**
	 * @param colVersion the colVersion to set
	 */
	public void setColVersion(Set<Version> colVersion) {
		this.colVersion = colVersion;
	}



	/**
	 * @param snmpId the snmpId to set
	 */
	public void setSnmpId(String snmpId) {
		this.snmpId = snmpId;
	}



	/**
	 * @param snmpComunidadR the snmpComunidadR to set
	 */
	public void setSnmpComunidadR(String snmpComunidadR) {
		this.snmpComunidadR = snmpComunidadR;
	}

	/**
	 * @param snmpVersion the snmpVersion to set
	 */
	public void setSnmpVersion(String snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	/**
	 * @param snmpPassword the snmpPassword to set
	 */
	public void setSnmpPassword(String snmpPassword) {
		this.snmpPassword = snmpPassword;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}


	/**
	 * @param dateCreate the dateCreate to set
	 */
	public void setDateCreate(Calendar dateCreate) {
		this.dateCreate = dateCreate;
	}

	/**
	 * @param dateModified the dateModified to set
	 */
	public void setDateModified(Calendar dateModified) {
		this.dateModified = dateModified;
	}
}

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
	@JoinColumn(name="model_id") 
	private Model model;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String serial_number;
	

	@ManyToOne(optional=false)
	@JoinColumn(name="ios_id") 
	private Ios ios;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String user;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String password;
	
	
	@OneToMany(cascade=CascadeType.DETACH)
	private Set<InterfaceDevice> interfaceDevice;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String snmp_id;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String snmp_comunidadR;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String snmp_version;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private String snmp_password;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	@Lob
	private String notes;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private Date date_create;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private Timestamp date_last_modified;
	
	
	
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
	 * @return the serial_number
	 */
	public String getSerial_number() {
		return serial_number;
	}



	/**
	 * @param serial_number the serial_number to set
	 */
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
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
	public Set<InterfaceDevice> getInterfaceDevice() {
		return interfaceDevice;
	}



	/**
	 * @param interfaceDevice the interfaceDevice to set
	 */
	public void setInterfaceDevice(Set<InterfaceDevice> interfaceDevice) {
		this.interfaceDevice = interfaceDevice;
	}



	/**
	 * @return the snmp_id
	 */
	public String getSnmp_id() {
		return snmp_id;
	}



	/**
	 * @param snmp_id the snmp_id to set
	 */
	public void setSnmp_id(String snmp_id) {
		this.snmp_id = snmp_id;
	}



	/**
	 * @return the snmp_comunidadR
	 */
	public String getSnmp_comunidadR() {
		return snmp_comunidadR;
	}



	/**
	 * @param snmp_comunidadR the snmp_comunidadR to set
	 */
	public void setSnmp_comunidadR(String snmp_comunidadR) {
		this.snmp_comunidadR = snmp_comunidadR;
	}



	/**
	 * @return the snmp_version
	 */
	public String getSnmp_version() {
		return snmp_version;
	}



	/**
	 * @param snmp_version the snmp_version to set
	 */
	public void setSnmp_version(String snmp_version) {
		this.snmp_version = snmp_version;
	}



	/**
	 * @return the snmp_password
	 */
	public String getSnmp_password() {
		return snmp_password;
	}



	/**
	 * @param snmp_password the snmp_password to set
	 */
	public void setSnmp_password(String snmp_password) {
		this.snmp_password = snmp_password;
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
	 * @return the date_create
	 */
	public Date getDate_create() {
		return date_create;
	}



	/**
	 * @param date_create the date_create to set
	 */
	public void setDate_create(Date date_create) {
		this.date_create = date_create;
	}



	/**
	 * @return the date_last_modified
	 */
	public Timestamp getDate_last_modified() {
		return date_last_modified;
	}



	/**
	 * @param date_last_modified the date_last_modified to set
	 */
	public void setDate_last_modified(Timestamp date_last_modified) {
		this.date_last_modified = date_last_modified;
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

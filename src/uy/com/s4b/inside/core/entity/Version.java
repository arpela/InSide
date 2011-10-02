package uy.com.s4b.inside.core.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Title: Version.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 20/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */
@Table(name="version")
@Entity
@NamedQueries({
	@NamedQuery(name="findVersionDevice", query="from Version v where v.oneDevice.id = :pid order by v.date desc")
})
public class Version implements Serializable {

	@Transient
	private static final long serialVersionUID = -3388648274104951488L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	
	@Column(insertable=true, nullable=false, unique=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	@Lob
	private String config;
	
	
	@ManyToOne (fetch=FetchType.EAGER,cascade=CascadeType.DETACH)
    @JoinColumn(name="deviceId", nullable=false)
    private Device oneDevice;
	
	
	/**
	 * 
	 */
	public Version() {
		
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
	 * @return the date
	 */
	public Calendar getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}


	/**
	 * @return the config
	 */
	public String getConfig() {
		return config;
	}


	/**
	 * @param config the config to set
	 */
	public void setConfig(String config) {
		this.config = config;
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
	 * @return the oneDevice
	 */
	public Device getOneDevice() {
		return oneDevice;
	}


	/**
	 * @param oneDevice the oneDevice to set
	 */
	public void setOneDevice(Device oneDevice) {
		this.oneDevice = oneDevice;
	}

}

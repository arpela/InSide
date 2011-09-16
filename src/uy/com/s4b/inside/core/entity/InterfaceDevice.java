/**
 * 
 */
package uy.com.s4b.inside.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author pablo
 *
 */
@Table(name="interface")
@Entity
public class InterfaceDevice implements Serializable {

	@Transient
	private static final long serialVersionUID = -3762025265849324892L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	
	@Column(insertable=true, nullable=false, unique=true)
	private String name;
	
	
	@Column(insertable=true, nullable=false, unique=false)
	private String ip;
	
	
	@Column(insertable=true, nullable=false, unique=false)
	private Integer index;
	
	
	@Column(insertable=true, nullable=false, unique=false)
	private String mac;
	
	
	@ManyToOne(optional=false)
	@JoinColumn(name="typeinterface_id") 
	private TypeInterface typeInterface;
	
	
	/**
	 * 
	 */
	public InterfaceDevice() {
		
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}


	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}


	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}


	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}


	/**
	 * @return the typeInterface
	 */
	public TypeInterface getTypeInterface() {
		return typeInterface;
	}


	/**
	 * @param typeInterface the typeInterface to set
	 */
	public void setTypeInterface(TypeInterface typeInterface) {
		this.typeInterface = typeInterface;
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

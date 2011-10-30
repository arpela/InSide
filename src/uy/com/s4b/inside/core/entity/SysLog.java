package uy.com.s4b.inside.core.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Title: SysLog.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 20/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */
@Table(name="syslog")
@Entity
public class SysLog implements Serializable {

	@Transient
	private static final long serialVersionUID = 8124313013614504959L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	
	@Column(insertable=true, nullable=false, unique=false)
	@Lob
	private String message;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateMessage;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateReceive;
	
	
	
	/**
	 * 
	 */
	public SysLog() {
		
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
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
	 * @return the dateReceive
	 */
	public Calendar getDateReceive() {
		return dateReceive;
	}


	/**
	 * @param dateReceive the dateReceive to set
	 */
	public void setDateReceive(Calendar dateReceive) {
		this.dateReceive = dateReceive;
	}


	/**
	 * @param dateMessage the dateMessage to set
	 */
	public void setDateMessage(Calendar dateMessage) {
		this.dateMessage = dateMessage;
	}


	/**
	 * @return the dateMessage
	 */
	public Calendar getDateMessage() {
		return dateMessage;
	}

}

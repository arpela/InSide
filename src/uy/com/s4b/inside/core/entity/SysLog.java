package uy.com.s4b.inside.core.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
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
	private String dateMessage;
	
	
	@Column(insertable=true, nullable=true, unique=false)
	private Timestamp dateReceive;
	
	
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


	/**
	 * @return the dateMessage
	 */
	public String getDateMessage() {
		return dateMessage;
	}


	/**
	 * @param dateMessage the dateMessage to set
	 */
	public void setDateMessage(String dateMessage) {
		this.dateMessage = dateMessage;
	}


	/**
	 * @return the dateReceive
	 */
	public Timestamp getDateReceive() {
		return dateReceive;
	}


	/**
	 * @param dateReceive the dateReceive to set
	 */
	public void setDateReceive(Timestamp dateReceive) {
		this.dateReceive = dateReceive;
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

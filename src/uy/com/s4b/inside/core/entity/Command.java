package uy.com.s4b.inside.core.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * Title: Command.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 20/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * 
 * @author Pablo
 * 
 */
@Table(name = "command")
@Entity
public class Command implements Serializable {

	@Transient
	private static final long serialVersionUID = -1613251536960951229L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer id;

	@Column(insertable = true, nullable = false, unique = false)
	private String value;
	
	@Column(insertable = true, nullable = false, unique = false)
	private int orden;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade={CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "fk_CommandGeneric", nullable = false, updatable = true, insertable = true)
	private GenericCommand commandGeneric;
	
	
	/**
	 * 
	 */
	public Command() {

	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}


	/**
	 * @return the commandGeneric
	 */
	public GenericCommand getCommandGeneric() {
		return commandGeneric;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}


	/**
	 * @param orden the orden to set
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}


	/**
	 * @param commandGeneric the commandGeneric to set
	 */
	public void setCommandGeneric(GenericCommand commandGeneric) {
		this.commandGeneric = commandGeneric;
	}

}

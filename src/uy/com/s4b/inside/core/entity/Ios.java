/**
 * 
 */
package uy.com.s4b.inside.core.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Title: Ios.java <br>
 * Description: <br>
 * Fecha creaci�n: 01/10/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author pablo
 *
 */
@Table(name="ios")
@Entity
@NamedQueries({
	@NamedQuery(name="findCommandsWhitTypeCommand", 
			query="select i from Ios i inner join FETCH i.colCommand c where i.id = :id and c.commandGeneric.typeCommand = :typeCommand order by c.orden asc")
})
public class Ios implements Serializable {

	@Transient
	private static final long serialVersionUID = -7942373572559359263L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	@Column(insertable=true, nullable=false, unique=true)
	private String name;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch= FetchType.LAZY)
	@JoinTable(name="ios_command", 
		joinColumns={@JoinColumn(name="id_ios")}, inverseJoinColumns={@JoinColumn(name="id_command")})
	private List<Command> colCommand;
	
	
	/**
	 * 
	 */
	public Ios() {
		
	}
	
	
	/**
	 * Funcion encargada de correjir imperfecciones 
	 * en los datos del entity
	 */
	@PrePersist()
	public void initEntity(){
		this.name = WordUtils.capitalizeFully(this.name);
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
	 * @return the colCommand
	 */
	public List<Command> getColCommand() {
		return colCommand;
	}


	/**
	 * @param colCommand the colCommand to set
	 */
	public void setColCommand(List<Command> colCommand) {
		this.colCommand = colCommand;
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

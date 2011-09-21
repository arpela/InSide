package uy.com.s4b.inside.core.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.WordUtils;
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
 * @author Pablo
 *
 */
@Table(name="command")
@Entity
public class Command implements Serializable {

	@Transient
	private static final long serialVersionUID = -1613251536960951229L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	
	@Column(insertable=true, nullable=false, unique=true)
	private String description;
	
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="command_parameter", 
		joinColumns={@JoinColumn(name="id_command")}, inverseJoinColumns={@JoinColumn(name="id_parameter")})
	private Set<Parameter> colParameters;
	
	
	/**
	 * 
	 */
	public Command() {
		
	}
	
	
	/**
	 * Funcion encargada de correjir imperfecciones 
	 * en los datos del entity
	 */
	@PrePersist()
	public void initEntity(){
		this.description = WordUtils.capitalizeFully(this.description);
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the colParameters
	 */
	public Set<Parameter> getColParameters() {
		return colParameters;
	}


	/**
	 * @param colParameters the colParameters to set
	 */
	public void setColParameters(Set<Parameter> colParameters) {
		this.colParameters = colParameters;
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

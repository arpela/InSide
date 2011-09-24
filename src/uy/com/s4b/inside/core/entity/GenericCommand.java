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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Title: GenericCommand.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 20/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */

@Table(name="genericcommand")
@Entity
public class GenericCommand implements Serializable {

	
	@Transient
	private static final long serialVersionUID = -4110089497129685546L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	
	@Column(insertable=true, nullable=false, unique=true)
	private String description;
	
	
	@OneToMany(cascade=CascadeType.DETACH)
	@JoinColumn(name="genericCommandId")
	private Set<Command> colCommand;
	
	
	/**
	 * 
	 */
	public GenericCommand() {
		
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
	 * @return the colCommand
	 */
	public Set<Command> getColCommand() {
		return colCommand;
	}


	/**
	 * @param colCommand the colCommand to set
	 */
	public void setColCommand(Set<Command> colCommand) {
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

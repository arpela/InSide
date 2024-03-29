/**
 * 
 */
package uy.com.s4b.inside.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="model")
@Entity
public class Model implements Serializable {
	
	@Transient
	private static final long serialVersionUID = -2092045630925129696L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	
	@Column(insertable=true, nullable=false, unique=true)
	@Enumerated(EnumType.STRING)
	private uy.com.s4b.inside.core.common.TypeDevice name;
	
//	@ManyToOne(optional=false)
//	@JoinColumn(name="serialId")
//	private Serial serial;
	
	
	public Model() {
		
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
	 * @return the name
	 */
	public uy.com.s4b.inside.core.common.TypeDevice getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(uy.com.s4b.inside.core.common.TypeDevice name) {
		this.name = name;
	}
	

}

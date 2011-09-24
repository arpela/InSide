package uy.com.s4b.inside.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author pablo
 * 
 */

@Table(name="serie")
@Entity
public class Serial implements Serializable {

	@Transient
	private static final long serialVersionUID = 6668829217481430973L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	
	@Column(insertable=true, nullable=false, unique=true)
	private String name;
	
	
	@ManyToOne(optional=false)
	@JoinColumn(name="typedeviceId") 
	private TypeDevice typeDevice;
	
	
	@ManyToOne(optional=false)
	@JoinColumn(name="iosId") 
	private Ios ios;
	
	
	public Serial() {
		
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
	 * @return the typeDevice
	 */
	public TypeDevice getTypeDevice() {
		return typeDevice;
	}


	/**
	 * @param typeDevice the typeDevice to set
	 */
	public void setTypeDevice(TypeDevice typeDevice) {
		this.typeDevice = typeDevice;
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

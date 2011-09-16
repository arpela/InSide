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
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author pablo
 *
 */
@Table(name="ios")
@Entity
public class Ios implements Serializable {

	@Transient
	private static final long serialVersionUID = -7942373572559359263L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	
	@Column(insertable=true, nullable=false, unique=true)
	private String name;
		
	
	/**
	 * 
	 */
	public Ios() {
		
	}

}

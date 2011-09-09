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
 * Title: UserEntity.java <br>
 * Description: <br>
 * Fecha creación: 08/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

@Table(name="user")
@Entity
public class User implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 6689611221434578413L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	@Column(nullable=false, insertable=true, unique=true)
	private String name;
	
	@Column(name = "password", unique=false, nullable=false)
	private String pass;
	
	
	/**
	 * 
	 */
	public User() {
		
	}
	
	/**
	 * @param id
	 * @param name
	 * @param pass
	 */
	public User(Integer id, String name, String pass) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
	}
	


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}


	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

}

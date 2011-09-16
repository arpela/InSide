package uy.com.s4b.inside.core.entity;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
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
import org.apache.log4j.Logger;

import uy.com.s4b.inside.core.common.CriptPassword;

/**
 * Title: UserEntity.java <br>
 * Description: <br>
 * Fecha creaci�n: 08/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */

@Table(name="user")
@Entity
@NamedQueries({
	@NamedQuery(name="loginUser", query="from User u where u.nick = :pNick and u.pass = :pPass"),
})
public class User implements Serializable {
	
	@Transient
	private transient static final Logger log = Logger.getLogger(User.class);
	
	@Transient
	private static final long serialVersionUID = 6689611221434578413L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	@Column(nullable=false, insertable=true, unique=true)
	private String nick;
	
	@Column(nullable=false, insertable=true, unique=false)
	private String name;
	
	@Column(nullable=false, insertable=true, unique=false)
	private String surname;
	
	@Column(name = "password", unique=false, nullable=false)
	private String pass;
	
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="user_profile", 
		joinColumns={@JoinColumn(name="id_user")}, inverseJoinColumns={@JoinColumn(name="id_profile")})
	private Set<Profile> myProfiles;
	
	
	/**
	 * 
	 */
	public User() {
		
	}
	
	/**
	 * @param id
	 * @param name
	 * @param pass
	 * @param myProfiles
	 */
	public User(Integer id, String name, String pass, Set<Profile> myProfiles) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.myProfiles = myProfiles;
	}

	/**
	 * Funcion encargada de correjir imperfecciones 
	 * en los datos del entity y hacer el encriptado de la pass
	 */
	@PrePersist()
	public void initEntity(){
		try {
			this.pass = new CriptPassword().getHashSH1(this.pass);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		}
		this.name = WordUtils.capitalizeFully(this.name);
		this.surname = WordUtils.capitalizeFully(this.surname);
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

	/**
	 * @return the myProfile
	 */
	public Set<Profile> getMyProfiles() {
		return myProfiles;
	}

	/**
	 * @param myProfile the myProfile to set
	 */
	public void setMyProfiles(Set<Profile> myProfile) {
		this.myProfiles = myProfile;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

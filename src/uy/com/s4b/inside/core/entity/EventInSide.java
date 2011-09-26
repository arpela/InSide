package uy.com.s4b.inside.core.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import uy.com.s4b.inside.core.common.TypeEvent;

/**
 * Title: EventInSide.java <br>
 * Description: <br>
 * Fecha creación: 25/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@Table(name="eventinside")
@Entity
@NamedQueries(
		{
			@NamedQuery(name="findAllActiv", query="from EventInSide e where e.deadline < :pFechaFin")
		})
public class EventInSide {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	@Column(insertable=true, nullable=false, unique=false)
	private String value;
	
	@Column(insertable=true, nullable=false, unique=false)
	private String description;
	
	@Column(insertable=true, nullable=false, unique=false)
	@Enumerated(EnumType.STRING)
	private TypeEvent type;
	
	@Column(insertable=true, nullable=false, unique=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar deadline;
	
	
	
	/**
	 * 
	 */
	public EventInSide() {
		
	}

	
	/**
	 * @param id
	 * @param value
	 * @param description
	 * @param type
	 * @param deadline
	 */
	public EventInSide(Integer id, String value, String description,
			TypeEvent type, Calendar deadline) {
		super();
		this.id = id;
		this.value = value;
		this.description = description;
		this.type = type;
		this.deadline = deadline;
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @return the deadline
	 */
	public Calendar getDeadline() {
		return deadline;
	}



	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the type
	 */
	public TypeEvent getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeEvent type) {
		this.type = type;
	}

}

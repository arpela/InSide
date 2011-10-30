package uy.com.s4b.inside.core.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;

/**
 * Title: MsgSysLog.java <br>
 * Description: <br>
 * Fecha creaciÃ³n: 20/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * 
 * @author Pablo
 * 
 */
@Table(name = "msgsyslog")
@Entity
@NamedQueries({
	@NamedQuery(name="find.msgsyslog.by.name", query="from MsgSysLog m where m.idSysLog = :name")
})
public class MsgSysLog implements Serializable {

	@Transient
	private static final long serialVersionUID = -3618912731812624132L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer id;

	@Column(name = "msgSysLog", insertable = true, nullable = true, unique = true)
	private String idSysLog;

	@ManyToOne(cascade = { CascadeType.DETACH }, optional = true)
	@JoinColumn(name = "actionId", insertable = true, updatable = true, nullable = false, unique = false)
	@Cascade(value={org.hibernate.annotations.CascadeType.REFRESH})
	private Action action;

	/**
	 * 
	 */
	public MsgSysLog() {

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

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
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
	 * @return the idSysLog
	 */
	public String getIdSysLog() {
		return idSysLog;
	}

	/**
	 * @param idSysLog
	 *            the idSysLog to set
	 */
	public void setIdSysLog(String idSysLog) {
		this.idSysLog = idSysLog;
	}

}

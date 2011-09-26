package uy.com.s4b.inside.ui.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

/**
 * Title: EvenBakingBean.java <br>
 * Description: <br>
 * Fecha creación: 25/09/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Alfredo
 *
 */
@ManagedBean(name="report")
@RequestScoped
public class ReportBakingBean {
	
	
	private static final Logger log = Logger.getLogger(ReportBakingBean.class);
	
	private String nameDivice;
	
	/**
	 * 
	 */
	public ReportBakingBean() {
		
	}
	
	
	public String accionFind() {
		return null;
	}

	/**
	 * @return the nameDivice
	 */
	public String getNameDivice() {
		return nameDivice;
	}

	/**
	 * @param nameDivice the nameDivice to set
	 */
	public void setNameDivice(String nameDivice) {
		this.nameDivice = nameDivice;
	}

	
}

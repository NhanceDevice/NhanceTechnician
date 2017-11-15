/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: UserAuthVerificationModel.java
*
* Date Author Changes
* 1 Aug, 2017 Sudhanshu Created
*/

package com.nhance.technician.model.newapis;

import java.util.Date;

/**
 * The Class UserAuthVerificationModel.
 */
public class UserAuthVerificationModel extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1650586677485329211L;
	
	/** The nuic. */
	private String nuic;
	
	/** The nac. */
	private String nac;
	
	/** The nac supl. */
	private String nacSupl;
	
	/** The expiry date. */
	private Date expiryDate;
	
	/** The status. */
	private Integer status;
	
	/** The email id. */
	private String emailId;
	

	/**
	 * Gets the nuic.
	 *
	 * @return the nuic
	 */
	public String getNuic() {
		return nuic;
	}

	/**
	 * Sets the nuic.
	 *
	 * @param nuic the new nuic
	 */
	public void setNuic(String nuic) {
		this.nuic = nuic;
	}

	/**
	 * Gets the nac.
	 *
	 * @return the nac
	 */
	public String getNac() {
		return nac;
	}

	/**
	 * Sets the nac.
	 *
	 * @param nac the new nac
	 */
	public void setNac(String nac) {
		this.nac = nac;
	}

	/**
	 * Gets the nac supl.
	 *
	 * @return the nac supl
	 */
	public String getNacSupl() {
		return nacSupl;
	}

	/**
	 * Sets the nac supl.
	 *
	 * @param nacSupl the new nac supl
	 */
	public void setNacSupl(String nacSupl) {
		this.nacSupl = nacSupl;
	}

	/**
	 * Gets the expiry date.
	 *
	 * @return the expiry date
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Sets the expiry date.
	 *
	 * @param expiryDate the new expiry date
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	/**
	 * Gets the email id.
	 *
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Sets the email id.
	 *
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	/**
	 * The Interface ValidateVerifyEmail.
	 */
	public interface ValidateVerifyEmail {}

}

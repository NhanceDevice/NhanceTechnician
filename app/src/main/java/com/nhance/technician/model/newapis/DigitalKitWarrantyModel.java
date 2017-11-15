/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: DigitalKitWarrantyModel.java
*
* Date Author Changes
* 7 Aug, 2017 Sambit Swain Created
*/

package com.nhance.technician.model.newapis;

import java.util.Date;

/**
 * The Class DigitalKitWarrantyModel.
 */
public class DigitalKitWarrantyModel extends MessageModel{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5869440778821388853L;

	/** The warranty start date. */
	private Date warrantyStartDate;
	
	/** The warranty end date. */
	private Date warrantyEndDate;
	
	/** The dk warranty status. */
	private Integer status;
	
	/** The warranty type. */
	private Integer warrantyType;
	
	/** The warranty code. */
	private String warrantyCode;

	/**
	 * Gets the warranty start date.
	 *
	 * @return the warranty start date
	 */
	public Date getWarrantyStartDate() {
		return warrantyStartDate;
	}

	/**
	 * Sets the warranty start date.
	 *
	 * @param warrantyStartDate the new warranty start date
	 */
	public void setWarrantyStartDate(Date warrantyStartDate) {
		this.warrantyStartDate = warrantyStartDate;
	}

	/**
	 * Gets the warranty end date.
	 *
	 * @return the warranty end date
	 */
	public Date getWarrantyEndDate() {
		return warrantyEndDate;
	}

	/**
	 * Sets the warranty end date.
	 *
	 * @param warrantyEndDate the new warranty end date
	 */
	public void setWarrantyEndDate(Date warrantyEndDate) {
		this.warrantyEndDate = warrantyEndDate;
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
	 * Gets the warranty type.
	 *
	 * @return the warranty type
	 */
	public Integer getWarrantyType() {
		return warrantyType;
	}

	/**
	 * Sets the warranty type.
	 *
	 * @param warrantyType the new warranty type
	 */
	public void setWarrantyType(Integer warrantyType) {
		this.warrantyType = warrantyType;
	}

	/**
	 * Gets the warranty code.
	 *
	 * @return the warranty code
	 */
	public String getWarrantyCode() {
		return warrantyCode;
	}

	/**
	 * Sets the warranty code.
	 *
	 * @param warrantyCode the new warranty code
	 */
	public void setWarrantyCode(String warrantyCode) {
		this.warrantyCode = warrantyCode;
	}

	
	
}

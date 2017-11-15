/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ServiceRequestEntityResponse.java
*
* Date Author Changes
* 1 Aug, 2017 Saroj Created
*/
package com.nhance.technician.model.newapis;

/**
 * The Class CustomerModelResponse.
 */
public class ChangePasswordModelResponse extends ResponseMessageModel<ChangePasswordModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7995837752021710288L;

	private ChangePasswordModel message;

	@Override
	public ChangePasswordModel getMessage() {
		return message;
	}

	@Override
	public void setMessage(ChangePasswordModel message) {
		this.message = message;
	}

	
}

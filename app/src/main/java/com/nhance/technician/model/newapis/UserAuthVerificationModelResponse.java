/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: UserAuthVerificationModelResponse.java
*
* Date Author Changes
* 20 Sep, 2017 Sudhanshu Created
*/

package com.nhance.technician.model.newapis;

/**
 * The Class UserAuthVerificationModelResponse.
 */
public class UserAuthVerificationModelResponse extends ResponseMessageModel<UserAuthVerificationModel> {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8630461577246795378L;
	
	/** The message. */
	private UserAuthVerificationModel message;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public UserAuthVerificationModel getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(UserAuthVerificationModel message) {
		this.message = message;
	}

	
}

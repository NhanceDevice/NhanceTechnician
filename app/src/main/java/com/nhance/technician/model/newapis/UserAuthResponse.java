/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: UserAuthResponse.java
*
* Date Author Changes
* 23 Aug, 2017 Saroj Created
*/
package com.nhance.technician.model.newapis;

/**
 * The Class UserAuthResponse.
 */
public class UserAuthResponse extends ResponseMessageModel<UserAuthModel> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8831337324059974578L;
	
	/** The user auth model. */
	private UserAuthModel message;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public UserAuthModel getMessage() {

		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(UserAuthModel message) {
		this.message = message;
	}

}

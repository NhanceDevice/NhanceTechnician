/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: AddressModelResponse.java
*
* Date Author Changes
* 21 Sep, 2017 Sudhanshu Created
*/

package com.nhance.technician.model.newapis;

/**
 * The Class AddressModelResponse.
 */
public class AddressModelResponse extends ResponseMessageModel<AddressModel> {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -507048661720796905L;
	
	/** The message. */
	private AddressModel message;

	
	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public AddressModel getMessage() {
		return message;
	}

	
	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(AddressModel message) {
		this.message = message;
	}

}

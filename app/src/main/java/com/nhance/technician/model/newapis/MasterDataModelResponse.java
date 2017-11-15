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
public class MasterDataModelResponse extends ResponseMessageModel<MasterDataModel> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2526580227347406858L;
	
	/** The message. */
	private MasterDataModel message;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public MasterDataModel getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(MasterDataModel message) {
		this.message = message;
	}

}

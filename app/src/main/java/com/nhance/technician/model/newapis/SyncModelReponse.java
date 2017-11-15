/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: SyncModelReponse.java
*
* Date Author Changes
* 17 Oct, 2017 Saroj Created
*/
package com.nhance.technician.model.newapis;

/**
 * The Class SyncModelReponse.
 */
public class SyncModelReponse extends ResponseMessageModel<SyncModel> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The message. */
	private SyncModel message;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public SyncModel getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(SyncModel message) {
		this.message = message;
		
	}

}

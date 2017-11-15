/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: SyncMasterDataModelResponse.java
*
* Date Author Changes
* 17 Oct, 2017 Saroj Created
*/
package com.nhance.technician.model.newapis;

/**
 * The Class SyncMasterDataModelResponse.
 */
public class SyncMasterDataModelResponse extends ResponseMessageModel<SyncMasterDataModel> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2924276700383026473L;
	
	/** The message. */
	private SyncMasterDataModel message;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public SyncMasterDataModel getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(SyncMasterDataModel message) {
		this.message = message;
	}

}

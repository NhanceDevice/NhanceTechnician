/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ServiceRequestHistoryModelResponse.java
*
* Date Author Changes
* 7 Sep, 2017 Sudhanshu Created
*/

package com.nhance.technician.model.newapis;

/**
 * The Class ServiceRequestHistoryModelResponse.
 */
public class ServiceRequestHistoryModelResponse extends ResponseMessageModel<ServiceRequestHistoryModel> {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7776895297856793682L;
	
	/** The message. */
	private ServiceRequestHistoryModel message;

	
	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public ServiceRequestHistoryModel getMessage() {
		return message;
	}

	
	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(ServiceRequestHistoryModel message) {
		this.message = message;
	}

	
}

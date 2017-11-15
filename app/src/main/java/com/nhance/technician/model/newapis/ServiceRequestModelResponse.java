/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ServiceRequestModelResponse.java
*
* Date Author Changes
* 6 Sep, 2017 Sudhanshu Created
*/

package com.nhance.technician.model.newapis;

/**
 * The Class ServiceRequestModelResponse.
 */
public class ServiceRequestModelResponse extends ResponseMessageModel<ServiceRequestModel> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2116279115282992755L;
	
	/** The message. */
	private ServiceRequestModel message;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public ServiceRequestModel getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(ServiceRequestModel message) {
		this.message = message;
	}

	
}

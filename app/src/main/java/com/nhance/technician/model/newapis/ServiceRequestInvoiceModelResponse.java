/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ServiceRequestInvoiceModelResponse.java
*
* Date Author Changes
* 12 Sep, 2017 Sudhanshu Created
*/
package com.nhance.technician.model.newapis;

/**
 * The Class ServiceRequestInvoiceModelResponse.
 */
public class ServiceRequestInvoiceModelResponse extends ResponseMessageModel<ServiceRequestInvoiceModel> {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1041543106651596393L;
	
	/** The message. */
	private ServiceRequestInvoiceModel message;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public ServiceRequestInvoiceModel getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(ServiceRequestInvoiceModel message) {
		this.message = message;
	}

	
}

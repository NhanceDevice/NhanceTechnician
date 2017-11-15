/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: PurchaseModelResponse.java
*
* Date Author Changes
* 23 Aug, 2017 Swadhin Created
*/
package com.nhance.technician.model.newapis;

/**
 * The Class PurchaseModelResponse.
 */
public class PurchaseModelResponse extends ResponseMessageModel<PurchasePlanModel> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2526580227347406858L;
	
	/** The message. */
	private PurchasePlanModel message;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public PurchasePlanModel getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(PurchasePlanModel message) {
		this.message = message;
	}

}

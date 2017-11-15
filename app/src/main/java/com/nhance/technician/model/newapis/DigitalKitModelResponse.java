/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: DigitalKitModelResponse.java
*
* Date Author Changes
* 7 Aug, 2017 Sambit Swain Created
*/

package com.nhance.technician.model.newapis;

/**
 * The Class DigitalKitModelResponse.
 */
public class DigitalKitModelResponse extends ResponseMessageModel<DigitalKitModel> {

	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1711256252776167734L;
	/** The message. */
	private DigitalKitModel message;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public DigitalKitModel getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(DigitalKitModel message) {
		this.message = message;
	}

}

/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: DocumentModelResponse.java
*
* Date Author Changes
* 3 Aug, 2017 Sambit Swain Created
*/

package com.nhance.technician.model.newapis;

/**
 * The Class DocumentModelResponse.
 */
public class DocumentModelResponse extends ResponseMessageModel<DocumentModel> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -840847493452248130L;
	
	/** The message. */
	private DocumentModel message;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public DocumentModel getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(DocumentModel message) {
		this.message = message;
	}

}

/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: DocumentType.java
*
* Date Author Changes
* 31 Jul, 2017 Sambit Swain Created
*/
package com.nhance.technician.model.newapis.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * The Enum DocumentType.
 */
public enum DocumentType {

	/** The product. */
	PRODUCT( 1, "Product" ),
	
	/** The invoice. */
	INVOICE( 2, "Invoice" ),
	
	/** The documents. */
	DOCUMENTS( 3, "Documents" ),
	
	/** The manuals. */
	MANUALS( 4, "Manuals" ),
	
	/** The video. */
	VIDEO( 5, "Video" ),
	
	/** The customer logo. */
	CUSTOMER_LOGO( 6, "CustomerLogo" ),
	
	/** The user profile. */
	USER_PROFILE( 7, "User Profile"),
	
	/** The notification. */
	NOTIFICATION( 8, "Notification" );
	
	/** The code. */
	private Integer code;
	
	/** The text. */
	private String text;
	
	/** The document type map. */
	private static Map<Integer, String> documentTypeMap = new HashMap<Integer, String>();

	static {
		for ( DocumentType documentType : DocumentType.values() ) {
			documentTypeMap.put( documentType.getCode(), documentType.getText() );
		}
	}
	
	/**
	 * Instantiates a new document type.
	 *
	 * @param code the code
	 * @param text the text
	 */
	private DocumentType(Integer code, String text) {
		this.code = code;
		this.text = text;
	}
	
	/**
	 * Gets the document type map.
	 *
	 * @return the document type map
	 */
	public static Map<Integer, String> getDocumentTypeMap() {
		return documentTypeMap;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Gets the document type map.
	 *
	 * @param code the code
	 * @return the document type map
	 */
	public static String getDocumentTypeMap(final Integer code ) {
		if ( documentTypeMap.get( code ) != null )  {
			return documentTypeMap.get( code );
		}
		return "";
	}
}

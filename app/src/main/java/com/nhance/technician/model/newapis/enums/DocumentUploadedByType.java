/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: DocumentUploadedByType.java
*
* Date Author Changes
* 31 Jul, 2017 Sambit Swain Created
*/

package com.nhance.technician.model.newapis.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * The Enum DocumentUploadedByType.
 */
public enum DocumentUploadedByType {

	/** The consumer. */
	CONSUMER(1, "Consumer"),
	
	/** The brand. */
	BRAND(2, "Brand");
	
	/** The code. */
	private Integer code;
	
	/** The text. */
	private String text;
	
	/** The issues by type map. */
	private static Map<Integer, String> documentUploadedByTypeMap = new HashMap<Integer, String>();

	static {
		for ( DocumentUploadedByType documentUploadedByType : DocumentUploadedByType.values() ) {
			documentUploadedByTypeMap.put( documentUploadedByType.getCode(), documentUploadedByType.getText() );
		}
	}
	
	/**
	 * Instantiates a new issued by type.
	 *
	 * @param code the code
	 * @param text the text
	 */
	private DocumentUploadedByType(Integer code, String text) {
		this.code = code;
		this.text = text;
	}
	
	/**
	 * Gets the issues by type map.
	 *
	 * @return the issues by type map
	 */
	public static Map<Integer, String> getDocumentUploadedByTypeMap() {
		return documentUploadedByTypeMap;
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
	 * Gets the issues by type map.
	 *
	 * @param code the code
	 * @return the issues by type map
	 */
	public static String getDocumentUploadedByTypeMap(final Integer code ) {
		if ( documentUploadedByTypeMap.get( code ) != null )  {
			return documentUploadedByTypeMap.get( code );
		}
		return "";
	}
}

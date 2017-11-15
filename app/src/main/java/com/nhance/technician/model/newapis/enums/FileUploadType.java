/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: FileUploadType.java
*
* Date Author Changes
* 31 Jul, 2017 Sambit Swain Created
*/

package com.nhance.technician.model.newapis.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * The Enum FileUploadType.
 */
public enum FileUploadType {

	/** The fileupload. */
	FILEUPLOAD(1, "File Upload"),
	
	/** The url. */
	URL(2, "URL");
	
	/** The code. */
	private Integer code;
	
	/** The text. */
	private String text;
	
	/** The file upload type map. */
	private static Map<Integer, String> fileUploadTypeMap = new HashMap<Integer, String>();

	static {
		for ( FileUploadType fileUploadType : FileUploadType.values() ) {
			fileUploadTypeMap.put( fileUploadType.getCode(), fileUploadType.getText() );
		}
	}
	
	/**
	 * Instantiates a new file upload type.
	 *
	 * @param code the code
	 * @param text the text
	 */
	private FileUploadType(Integer code, String text) {
		this.code = code;
		this.text = text;
	}
	
	/**
	 * Gets the file upload type map.
	 *
	 * @return the file upload type map
	 */
	public static Map<Integer, String> getFileUploadTypeMap() {
		return fileUploadTypeMap;
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
	 * Gets the file upload type map.
	 *
	 * @param code the code
	 * @return the file upload type map
	 */
	public static String getFileUploadTypeMap(final Integer code ) {
		if ( fileUploadTypeMap.get( code ) != null )  {
			return fileUploadTypeMap.get( code );
		}
		return "";
	}
}

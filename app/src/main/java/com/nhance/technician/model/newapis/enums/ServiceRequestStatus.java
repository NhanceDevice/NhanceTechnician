/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ServiceRequestStatus.java
*
* Date Author Changes
* 6 Sep, 2017 Sudhanshu Created
*/
package com.nhance.technician.model.newapis.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * The Enum ServiceRequestStatus.
 */
public enum ServiceRequestStatus {
	
	/** The new. */
	NEW(1,"New"),
	
	/** The under progress. */
	UNDER_PROGRESS(2,"Underprogress"),
	
	/** The resolved. */
	RESOLVED(4,"Resolved"),
	
	/** The reopen. */
	REOPEN(5,"Reopen"),
	
	/** The closed. */
	CLOSED(6,"Closed");
	
	/** The code. */
	private Integer code;
	
	/** The text. */
	private String text;
	
	/** The service request status map. */
	private static Map<Integer, String> serviceRequestStatusMap = new HashMap<Integer, String>();
	
	static {
		for ( ServiceRequestStatus serviceRequestStatus : ServiceRequestStatus.values() ) {
			serviceRequestStatusMap.put( serviceRequestStatus.getCode(), serviceRequestStatus.getText() );
		}
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
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(Integer code) {
		this.code = code;
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
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Instantiates a new service request status.
	 *
	 * @param code the code
	 * @param text the text
	 */
	private ServiceRequestStatus(Integer code, String text) {
		this.code = code;
		this.text = text;
	}
	
	/**
	 * Gets the service request status map.
	 *
	 * @return the service request status map
	 */
	public static Map<Integer, String> getServiceRequestStatusMap() {
		return serviceRequestStatusMap;
	}
	
	/**
	 * Gets the service request status map.
	 *
	 * @param code the code
	 * @return the service request status map
	 */
	public static String getServiceRequestStatusMap( final Integer code ) {
		if ( serviceRequestStatusMap.get( code ) != null )  {
			return serviceRequestStatusMap.get( code );
		}
		return "";
	}
}

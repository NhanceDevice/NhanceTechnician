/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: Status.java
*
* Date Author Changes
* 2 Aug, 2017 Saroj Created
*/
package com.nhance.technician.model.newapis.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class Status.
 */
public class Status {

	/** The status map. */
	private static Map<Integer, String> statusMap = new HashMap<Integer, String>();

	/** The Constant ACTIVE. */
	public static final Status ACTIVE = new Status(1, "Active");

	/** The Constant INACTIVE. */
	public static final Status INACTIVE = new Status(2, "InActive");

	/** The Constant DELETED. */
	public static final Status DELETED = new Status(3, "Deleted");
	
	/** The Constant NEW. */
	public static final Status NEW = new Status(4, "New");
	
	/** The Constant UPDATED. */
	public static final Status UPDATED = new Status(5, "Updated");
	
	/** The Constant UNCHANGED. */
	public static final Status UNCHANGED = new Status(6, "UnChanged");
	
	/** The Constant APPROVED. */
	public static final Status APPROVED = new Status(7, "Approved");
	
	/** The Constant PENDING. */
	public static final Status PENDING = new Status(8, "Pending");
	
	/** The Constant GENERATED. */
	public static final Status GENERATED = new Status(9, "Generated");
	
	/** The Constant DOWNLOADED. */
	public static final Status DOWNLOADED = new Status(10, "Downloaded");
	
	/** The Constant ONBOARDED. */
	public static final Status ONBOARDED = new Status(11, "Onboarded");
	
	/** The Constant EMAIL_VERIFIED. */
	public static final Status EMAIL_VERIFIED = new Status(12, "Email Verified");
	
	/** The Constant REJECTED. */
	public static final Status REJECTED = new Status(13, "Rejected");
	
	/** The Constant PAID. */
	public static final Status PAID = new Status(14, "Paid");
	
	/** The Constant COMPLETED. */
	public static final Status COMPLETED = new Status(15, "Completed");
	
	/** The Constant TEMP. */
	public static final Status TEMP = new Status(16, "Temp");
	
	public static final Status READ = new Status(17, "Read");
	
	public static final Status UNREAD = new Status(18, "Un Read");
	
	public static final Status FAILED = new Status(19, "Failed");
	
	
	
	/** The code. */
	private Integer code;

	/** The text. */
	private String text;

	/**
	 * Gets the terminal status map.
	 * 
	 * @return the terminal status map
	 */
	public static Map<Integer, String> getStatusMap() {
		return statusMap;
	}

	/**
	 * Instantiates a new status enum.
	 * 
	 * @param code
	 *            the code
	 * @param text
	 *            the text
	 */
	public Status(Integer code, String text) {
		this.code = code;
		this.text = text;
		String exisintgValue = statusMap.put(code, text);
		if (exisintgValue != null) {

			throw new IllegalArgumentException("The code " + code
					+ " already exists in StatusEEnum");
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
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the message.
	 * 
	 * @param code
	 *            the code
	 * @return the message
	 */
	public static String getMessage(final Integer code) {
		if (statusMap.get(code) != null) {
			return statusMap.get(code);
		}
		return String.valueOf(code);
	}

}

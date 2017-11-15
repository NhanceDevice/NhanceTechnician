/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: DigitalKitAlertScheduleModel.java
*
* Date Author Changes
* 7 Aug, 2017 Sambit Swain Created
*/
package com.nhance.technician.model.newapis;

import java.util.Date;

/**
 * The Class DigitalKitAlertScheduleModel.
 */
public class DigitalKitAlertScheduleModel extends MessageModel{
	

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7893198026470585904L;

	/** The schedule date. */
	private Date scheduleDate;
	
	/** The schedule order. */
	private Integer scheduleOrder;
	
	/** The message. */
	private String message;
	
	/** The notification code. */
	private String notificationCode;

	/**
	 * Gets the schedule date.
	 *
	 * @return the schedule date
	 */
	public Date getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * Sets the schedule date.
	 *
	 * @param scheduleDate the new schedule date
	 */
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	/**
	 * Gets the schedule order.
	 *
	 * @return the schedule order
	 */
	public Integer getScheduleOrder() {
		return scheduleOrder;
	}

	/**
	 * Sets the schedule order.
	 *
	 * @param scheduleOrder the new schedule order
	 */
	public void setScheduleOrder(Integer scheduleOrder) {
		this.scheduleOrder = scheduleOrder;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the notification code.
	 *
	 * @return the notification code
	 */
	public String getNotificationCode() {
		return notificationCode;
	}

	/**
	 * Sets the notification code.
	 *
	 * @param notificationCode the new notification code
	 */
	public void setNotificationCode(String notificationCode) {
		this.notificationCode = notificationCode;
	}
	
	
}

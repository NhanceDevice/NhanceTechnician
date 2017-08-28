/* Copyright � EasOfTech 2015. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: Notification
*
* Date Author Changes
* 13 May, 2016 Saroj Created
*/
package com.nhance.technician.model;

/**
 * The Class PushNotification.
 */
public class PushNotification extends Notification{
	
	/** The title. */
	private String title;
	
	/** The message. */
	private String message;
	
	/** The image. */
	private String image;
	
	/** The notification id. */
	private String notificationCode;
	
	/** The type. persistent or non-persistent */
	private int type;
	
	/** The action code. - navigate to app screen(i.e, purchase/service request) or open browser */
	private int actionType;
	
	/** The action. */
	private String action;

	private long time;

	private String notificationReceivedAgo;

	/** The digital kit code. */
	private String digitalKitCode;

	private String propertyOne;

	private String propertyTwo;

	private String propertyThree;

	private String propertyFour;

	private String propertyFive;

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(String image) {
		this.image = image;
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

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Gets the action type.
	 *
	 * @return the action type
	 */
	public Integer getActionType() {
		return actionType;
	}

	/**
	 * Sets the action type.
	 *
	 * @param actionType the new action type
	 */
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets the action.
	 *
	 * @param action the new action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getNotificationReceivedAgo() {
		return notificationReceivedAgo;
	}

	public void setNotificationReceivedAgo(String notificationReceivedAgo) {
		this.notificationReceivedAgo = notificationReceivedAgo;
	}

	/**
	 * Gets the digital kit code.
	 *
	 * @return the digital kit code
	 */
	public String getDigitalKitCode() {
		return digitalKitCode;
	}

	/**
	 * Sets the digital kit code.
	 *
	 * @param digitalKitCode the new digital kit code
	 */
	public void setDigitalKitCode(String digitalKitCode) {
		this.digitalKitCode = digitalKitCode;
	}

	public String getPropertyOne() {
		return propertyOne;
	}

	public void setPropertyOne(String propertyOne) {
		this.propertyOne = propertyOne;
	}

	public String getPropertyTwo() {
		return propertyTwo;
	}

	public void setPropertyTwo(String propertyTwo) {
		this.propertyTwo = propertyTwo;
	}

	public String getPropertyThree() {
		return propertyThree;
	}

	public void setPropertyThree(String propertyThree) {
		this.propertyThree = propertyThree;
	}

	public String getPropertyFour() {
		return propertyFour;
	}

	public void setPropertyFour(String propertyFour) {
		this.propertyFour = propertyFour;
	}

	public String getPropertyFive() {
		return propertyFive;
	}

	public void setPropertyFive(String propertyFive) {
		this.propertyFive = propertyFive;
	}
}

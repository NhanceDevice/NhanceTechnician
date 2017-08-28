/* Copyright © EasOfTech 2015. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: ServiceRequestHistoryDTO.java
*
* Date Author Changes
* 4 May, 2016 Saroj Created
*/
package com.nhance.technician.model;

/**
 * The Class ServiceRequestHistory.
 */
public class ServiceRequestHistory extends BaseModel {

	private int id;

	/** The service request number. */
	private String serviceRequestNumber;

	 /** The stage. */
 	private int stage;

    /**  persistent field. */
    private String message;

    /**  persistent field. */
    private String createdBy;

    /**  persistent field. */
    private Long createdDate;

	private String attachedDocument;

	/** The attached document file name. */
	private String attachedDocumentFileName;

	/** The attached document format. */
	private String attachedDocumentFormat;

	/** The attached document location. */
	private String documentLocation;

	/**  persistent field. */
	private String createdByName;
	/**
	 * Gets the service request number.
	 *
	 * @return the service request number
	 */
	public String getServiceRequestNumber() {
		return serviceRequestNumber;
	}

	/**
	 * Sets the service request number.
	 *
	 * @param serviceRequestNumber the new service request number
	 */
	public void setServiceRequestNumber(String serviceRequestNumber) {
		this.serviceRequestNumber = serviceRequestNumber;
	}

	/**
	 * Gets the stage.
	 *
	 * @return the stage
	 */
	public int getStage() {
		return stage;
	}

	/**
	 * Sets the stage.
	 *
	 * @param stage the new stage
	 */
	public void setStage(int stage) {
		this.stage = stage;
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
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Long getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	public String getAttachedDocument() {
		return attachedDocument;
	}

	public void setAttachedDocument(String attachedDocument) {
		this.attachedDocument = attachedDocument;
	}

	public String getAttachedDocumentFileName() {
		return attachedDocumentFileName;
	}

	public void setAttachedDocumentFileName(String attachedDocumentFileName) {
		this.attachedDocumentFileName = attachedDocumentFileName;
	}

	public String getAttachedDocumentFormat() {
		return attachedDocumentFormat;
	}

	public void setAttachedDocumentFormat(String attachedDocumentFormat) {
		this.attachedDocumentFormat = attachedDocumentFormat;
	}

	public String getDocumentLocation() {
		return documentLocation;
	}

	public void setDocumentLocation(String documentLocation) {
		this.documentLocation = documentLocation;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ServiceRequestHistory{" +
				"serviceRequestNumber='" + serviceRequestNumber + '\'' +
				", stage=" + stage +
				", message='" + message + '\'' +
				", createdBy='" + createdBy + '\'' +
				", createdDate=" + createdDate +
				", attachedDocument='" + attachedDocument + '\'' +
				", attachedDocumentFileName='" + attachedDocumentFileName + '\'' +
				", attachedDocumentFormat='" + attachedDocumentFormat + '\'' +
				", documentLocation='" + documentLocation + '\'' +
				", createdByName='" + createdByName + '\'' +
				'}';
	}
}

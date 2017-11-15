/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ServiceRequestHistoryModel.java
*
* Date Author Changes
* 7 Sep, 2017 Sudhanshu Created
*/
package com.nhance.technician.model.newapis;

/**
 * The Class ServiceRequestHistoryModel.
 */
public class ServiceRequestHistoryModel extends MessageModel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5257596865536340872L;

	/** The message. */
	private String message;
	
	/** The session id. */
	private String sessionId;
	
	/** The token. */
	private String token;
	
	/** The archive id. */
	private String archiveId;
	
	/** The archive duration. */
	private Integer archiveDuration;
	
	/** The archive status. */
	private Integer archiveStatus;
	
	/** The comment. */
	private String comment;
	
	/** The user guid. */
	private String userGuid;
	
	/** The service request guid. */
	private String serviceRequestGuid;
	
	/** The document. */
	DocumentModel document;

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
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Sets the session id.
	 *
	 * @param sessionId the new session id
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the archive id.
	 *
	 * @return the archive id
	 */
	public String getArchiveId() {
		return archiveId;
	}

	/**
	 * Sets the archive id.
	 *
	 * @param archiveId the new archive id
	 */
	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}

	/**
	 * Gets the archive duration.
	 *
	 * @return the archive duration
	 */
	public Integer getArchiveDuration() {
		return archiveDuration;
	}

	/**
	 * Sets the archive duration.
	 *
	 * @param archiveDuration the new archive duration
	 */
	public void setArchiveDuration(Integer archiveDuration) {
		this.archiveDuration = archiveDuration;
	}

	/**
	 * Gets the archive status.
	 *
	 * @return the archive status
	 */
	public Integer getArchiveStatus() {
		return archiveStatus;
	}

	/**
	 * Sets the archive status.
	 *
	 * @param archiveStatus the new archive status
	 */
	public void setArchiveStatus(Integer archiveStatus) {
		this.archiveStatus = archiveStatus;
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets the user guid.
	 *
	 * @return the user guid
	 */
	public String getUserGuid() {
		return userGuid;
	}

	/**
	 * Sets the user guid.
	 *
	 * @param userGuid the new user guid
	 */
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	/**
	 * Gets the service request guid.
	 *
	 * @return the service request guid
	 */
	public String getServiceRequestGuid() {
		return serviceRequestGuid;
	}

	/**
	 * Sets the service request guid.
	 *
	 * @param serviceRequestGuid the new service request guid
	 */
	public void setServiceRequestGuid(String serviceRequestGuid) {
		this.serviceRequestGuid = serviceRequestGuid;
	}

	
	/**
	 * @return the document
	 */
	public DocumentModel getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(DocumentModel document) {
		this.document = document;
	}


	/**
	 * The Interface ValidateCreateServiceRequestHistory.
	 */
	public interface ValidateCreateServiceRequestHistory{}
}

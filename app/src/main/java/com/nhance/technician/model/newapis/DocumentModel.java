/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: DocumentModel.java
*
* Date Author Changes
* 31 Jul, 2017 Sambit Swain Created
*/
package com.nhance.technician.model.newapis;


/**
 * The Class DocumentModel.
 */
public class DocumentModel extends MessageModel {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6247067966531533259L;
	
	/** The document id. */
	private Long documentId;

	/** The document extention. */
	private String documentExtention;
	
	/** The document name. */
	private String documentName;
	
	/** The document type. */
	private Integer documentType;
	
	/** The document path. */
	private String documentPath;
	
	/** The document data. */
	private String documentData;
	
	/** The file upload type. */
	private Integer fileUploadType;
	
	/** The document uploaded by type. */
	private Integer documentUploadedByType;
	
	/** The status. */
	private Integer documentStatus;

	/**
	 * Gets the document id.
	 *
	 * @return the document id
	 */
	public Long getDocumentId() {
		return documentId;
	}

	/**
	 * Sets the document id.
	 *
	 * @param documentId the new document id
	 */
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	/**
	 * Gets the document extention.
	 *
	 * @return the document extention
	 */
	public String getDocumentExtention() {
		return documentExtention;
	}

	/**
	 * Sets the document extention.
	 *
	 * @param documentExtention the new document extention
	 */
	public void setDocumentExtention(String documentExtention) {
		this.documentExtention = documentExtention;
	}

	/**
	 * Gets the document name.
	 *
	 * @return the document name
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * Sets the document name.
	 *
	 * @param documentName the new document name
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * Gets the document type.
	 *
	 * @return the document type
	 */
	public Integer getDocumentType() {
		return documentType;
	}

	/**
	 * Sets the document type.
	 *
	 * @param documentType the new document type
	 */
	public void setDocumentType(Integer documentType) {
		this.documentType = documentType;
	}

	/**
	 * Gets the document path.
	 *
	 * @return the document path
	 */
	public String getDocumentPath() {
		return documentPath;
	}

	/**
	 * Sets the document path.
	 *
	 * @param documentPath the new document path
	 */
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	/**
	 * Gets the document data.
	 *
	 * @return the document data
	 */
	public String getDocumentData() {
		return documentData;
	}

	/**
	 * Sets the document data.
	 *
	 * @param documentData the new document data
	 */
	public void setDocumentData(String documentData) {
		this.documentData = documentData;
	}

	/**
	 * Gets the file upload type.
	 *
	 * @return the file upload type
	 */
	public Integer getFileUploadType() {
		return fileUploadType;
	}

	/**
	 * Sets the file upload type.
	 *
	 * @param fileUploadType the new file upload type
	 */
	public void setFileUploadType(Integer fileUploadType) {
		this.fileUploadType = fileUploadType;
	}

	/**
	 * Gets the document uploaded by type.
	 *
	 * @return the document uploaded by type
	 */
	public Integer getDocumentUploadedByType() {
		return documentUploadedByType;
	}

	/**
	 * Sets the document uploaded by type.
	 *
	 * @param documentUploadedByType the new document uploaded by type
	 */
	public void setDocumentUploadedByType(Integer documentUploadedByType) {
		this.documentUploadedByType = documentUploadedByType;
	}

	/**
	 * Gets the document status.
	 *
	 * @return the document status
	 */
	public Integer getDocumentStatus() {
		return documentStatus;
	}

	/**
	 * Sets the document status.
	 *
	 * @param documentStatus the new document status
	 */
	public void setDocumentStatus(Integer documentStatus) {
		this.documentStatus = documentStatus;
	}

	public interface ValidateCreateDocument{}
	
	public interface ValidateUpdateDocument{}
	
}

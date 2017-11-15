/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ServiceRequestModel.java
*
* Date Author Changes
* 5 Sep, 2017 Sudhanshu Created
*/
package com.nhance.technician.model.newapis;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The Class ServiceRequestModel.
 */
public class ServiceRequestModel extends MessageModel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5514233764561935167L;

	/** The srn. */
	private String srn;
	
	/** The subject. */
	private String subject;
	
	/** The message. */
	private String message;
	
	/** The amount. */
	private Double amount;
	
	/** The digital kit. */
	private String digitalKitGuid;
	
	/** The user guid. TODO.. remove*/
	private String userGuid;
	
	/** The service engineer user id. */
	private String serviceEngineerUserId;
	
	/** The end consumer. */
	private UserModel endConsumer;
	
	/** The address. */
	private String addressId;
	
	/** The sr status. */
	private Integer srStatus;
	
	/** The is viewed. */
	private Integer isViewed;
	
	/** The service type guid. */
	private String serviceTypeGuid;
	
	/** The service type name. */
	private String serviceTypeName;
	
	/** The product name. */
	private String productName;
	
	/** The product image url. */
	private String productImageUrl;
	
	/** The priority. */
	private Integer priority;
	
	/** The srn created date. */
	private Date srnCreatedDate;
	
	/** The status map. */
	private Map<Integer, String> statusMap;
	
	/** The document. */
	private DocumentModel document;
	
	/** The digital kit. */
	private DigitalKitModel digitalKit;
	
	/** The service request extns. */
	private List<ServiceRequestExtnModel> serviceRequestExtns;
	
	/** The service request invoices. */
	private List<ServiceRequestInvoiceModel> serviceRequestInvoices;
	
	/** The service request histories. */
	private List<ServiceRequestHistoryModel> serviceRequestHistories;
	
	
	/**
	 * Gets the srn.
	 *
	 * @return the srn
	 */
	public String getSrn() {
		return srn;
	}

	/**
	 * Sets the srn.
	 *
	 * @param srn the srn to set
	 */
	public void setSrn(String srn) {
		this.srn = srn;
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the digital kit guid.
	 *
	 * @return the digitalKitGuid
	 */
	public String getDigitalKitGuid() {
		return digitalKitGuid;
	}

	/**
	 * Sets the digital kit guid.
	 *
	 * @param digitalKitGuid the digitalKitGuid to set
	 */
	public void setDigitalKitGuid(String digitalKitGuid) {
		this.digitalKitGuid = digitalKitGuid;
	}

	/**
	 * Gets the user guid.
	 *
	 * @return the userGuid
	 */
	public String getUserGuid() {
		return userGuid;
	}

	/**
	 * Sets the user guid.
	 *
	 * @param userGuid the userGuid to set
	 */
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	/**
	 * Gets the service engineer user id.
	 *
	 * @return the service engineer user id
	 */
	public String getServiceEngineerUserId() {
		return serviceEngineerUserId;
	}

	/**
	 * Sets the service engineer user id.
	 *
	 * @param serviceEngineerUserId the new service engineer user id
	 */
	public void setServiceEngineerUserId(String serviceEngineerUserId) {
		this.serviceEngineerUserId = serviceEngineerUserId;
	}

	/**
	 * Gets the end consumer.
	 *
	 * @return the endConsumer
	 */
	public UserModel getEndConsumer() {
		return endConsumer;
	}

	/**
	 * Sets the end consumer.
	 *
	 * @param endConsumer the endConsumer to set
	 */
	public void setEndConsumer(UserModel endConsumer) {
		this.endConsumer = endConsumer;
	}


	/**
	 * Gets the address id.
	 *
	 * @return the address id
	 */
	public String getAddressId() {
		return addressId;
	}

	/**
	 * Sets the address id.
	 *
	 * @param addressId the new address id
	 */
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	/**
	 * Gets the sr status.
	 *
	 * @return the srStatus
	 */
	public Integer getSrStatus() {
		return srStatus;
	}

	/**
	 * Sets the sr status.
	 *
	 * @param srStatus the srStatus to set
	 */
	public void setSrStatus(Integer srStatus) {
		this.srStatus = srStatus;
	}

	/**
	 * Gets the checks if is viewed.
	 *
	 * @return the isViewed
	 */
	public Integer getIsViewed() {
		return isViewed;
	}

	/**
	 * Sets the checks if is viewed.
	 *
	 * @param isViewed the isViewed to set
	 */
	public void setIsViewed(Integer isViewed) {
		this.isViewed = isViewed;
	}

	/**
	 * Gets the service type guid.
	 *
	 * @return the serviceTypeGuid
	 */
	public String getServiceTypeGuid() {
		return serviceTypeGuid;
	}

	/**
	 * Sets the service type guid.
	 *
	 * @param serviceTypeGuid the serviceTypeGuid to set
	 */
	public void setServiceTypeGuid(String serviceTypeGuid) {
		this.serviceTypeGuid = serviceTypeGuid;
	}

	/**
	 * Gets the service type name.
	 *
	 * @return the serviceTypeName
	 */
	public String getServiceTypeName() {
		return serviceTypeName;
	}

	/**
	 * Sets the service type name.
	 *
	 * @param serviceTypeName the serviceTypeName to set
	 */
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	/**
	 * Gets the product name.
	 *
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Sets the product name.
	 *
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Gets the product image url.
	 *
	 * @return the productImageUrl
	 */
	public String getProductImageUrl() {
		return productImageUrl;
	}

	/**
	 * Sets the product image url.
	 *
	 * @param productImageUrl the productImageUrl to set
	 */
	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * Gets the srn created date.
	 *
	 * @return the srn created date
	 */
	public Date getSrnCreatedDate() {
		return srnCreatedDate;
	}

	/**
	 * Sets the srn created date.
	 *
	 * @param srnCreatedDate the new srn created date
	 */
	public void setSrnCreatedDate(Date srnCreatedDate) {
		this.srnCreatedDate = srnCreatedDate;
	}

	/**
	 * Gets the status map.
	 *
	 * @return the status map
	 */
	public Map<Integer, String> getStatusMap() {
		return statusMap;
	}

	/**
	 * Sets the status map.
	 *
	 * @param statusMap the status map
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}

	/**
	 * Gets the document.
	 *
	 * @return the document
	 */
	public DocumentModel getDocument() {
		return document;
	}

	/**
	 * Sets the document.
	 *
	 * @param document the document to set
	 */
	public void setDocument(DocumentModel document) {
		this.document = document;
	}

	/**
	 * Gets the digital kit.
	 *
	 * @return the digitalKit
	 */
	public DigitalKitModel getDigitalKit() {
		return digitalKit;
	}

	/**
	 * Sets the digital kit.
	 *
	 * @param digitalKit the digitalKit to set
	 */
	public void setDigitalKit(DigitalKitModel digitalKit) {
		this.digitalKit = digitalKit;
	}

	/**
	 * Gets the service request extns.
	 *
	 * @return the serviceRequestExtns
	 */
	public List<ServiceRequestExtnModel> getServiceRequestExtns() {
		return serviceRequestExtns;
	}

	/**
	 * Sets the service request extns.
	 *
	 * @param serviceRequestExtns the serviceRequestExtns to set
	 */
	public void setServiceRequestExtns(List<ServiceRequestExtnModel> serviceRequestExtns) {
		this.serviceRequestExtns = serviceRequestExtns;
	}

	/**
	 * Gets the service request invoices.
	 *
	 * @return the serviceRequestInvoices
	 */
	public List<ServiceRequestInvoiceModel> getServiceRequestInvoices() {
		return serviceRequestInvoices;
	}

	/**
	 * Sets the service request invoices.
	 *
	 * @param serviceRequestInvoices the serviceRequestInvoices to set
	 */
	public void setServiceRequestInvoices(List<ServiceRequestInvoiceModel> serviceRequestInvoices) {
		this.serviceRequestInvoices = serviceRequestInvoices;
	}

	/**
	 * Gets the service request histories.
	 *
	 * @return the serviceRequestHistories
	 */
	public List<ServiceRequestHistoryModel> getServiceRequestHistories() {
		return serviceRequestHistories;
	}

	/**
	 * Sets the service request histories.
	 *
	 * @param serviceRequestHistories the serviceRequestHistories to set
	 */
	public void setServiceRequestHistories(List<ServiceRequestHistoryModel> serviceRequestHistories) {
		this.serviceRequestHistories = serviceRequestHistories;
	}

	/**
	 * The Interface ValidateCreateServiceRequest.
	 */
	public interface ValidateCreateServiceRequest{}
	
	/**
	 * The Interface ValidateUpdateServiceRequest.
	 */
	public interface ValidateUpdateServiceRequest{}
	
	/**
	 * The Interface ValidateAssignServiceEngineer.
	 */
	public interface ValidateAssignServiceEngineer{}
	
	/**
	 * The Interface ValidateSetPriority.
	 */
	public interface ValidateSetPriority{}
	
	/**
	 * The Interface ValidatefindServiceRequests.
	 */
	public interface ValidatefindServiceRequests{}

}

/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ServiceRequestInvoiceModel.java
*
* Date Author Changes
* 12 Sep, 2017 Sudhanshu Created
*/
package com.nhance.technician.model.newapis;

import java.util.Date;
import java.util.List;

/**
 * The Class ServiceRequestInvoiceModel.
 */
public class ServiceRequestInvoiceModel extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4664050354028643827L;

	/** The invoice number. */
	private String invoiceNumber;
	
	/** The subject. */
	private String subject;
	
	/** The base amount. */
	private Double baseAmount;
	
	/** The labour amount. */
	private Double labourAmount;
	
	/** The total amount. */
	private Double totalAmount;
	
	/** The discount. */
	private Double discount;
	
	/** The tax amount. */
	private Double taxAmount;
	
	/** The net payable amount. */
	private Double netPayableAmount;
	
	/** The payment mode. */
	private Integer paymentMode;
	
	/** The invoice status. */
	private Integer invoiceStatus;

	/** The invoice link. */
	private String invoiceLink;
	
	/** The document name. */
	private String documentName;
	
	/** The payment date. */
	private Date paymentDate;
	
	/** The payment reference. */
	private String paymentReference;
	
	/** The service request guid. */
	private String serviceRequestGuid;
	
	/** The digital kit guid. */
	private String digitalKitGuid;
	
	/** The service request invoice components. */
	private List<ServiceRequestInvoiceComponentModel> serviceRequestInvoiceComponents;

	/**
	 * Gets the invoice number.
	 *
	 * @return the invoice number
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * Sets the invoice number.
	 *
	 * @param invoiceNumber the new invoice number
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
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
	 * @param subject the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the base amount.
	 *
	 * @return the base amount
	 */
	public Double getBaseAmount() {
		return baseAmount;
	}

	/**
	 * Sets the base amount.
	 *
	 * @param baseAmount the new base amount
	 */
	public void setBaseAmount(Double baseAmount) {
		this.baseAmount = baseAmount;
	}

	/**
	 * Gets the labour amount.
	 *
	 * @return the labour amount
	 */
	public Double getLabourAmount() {
		return labourAmount;
	}

	/**
	 * Sets the labour amount.
	 *
	 * @param labourAmount the new labour amount
	 */
	public void setLabourAmount(Double labourAmount) {
		this.labourAmount = labourAmount;
	}

	/**
	 * Gets the total amount.
	 *
	 * @return the total amount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Sets the total amount.
	 *
	 * @param totalAmount the new total amount
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * Gets the discount.
	 *
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * Sets the discount.
	 *
	 * @param discount the new discount
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * Gets the tax amount.
	 *
	 * @return the tax amount
	 */
	public Double getTaxAmount() {
		return taxAmount;
	}

	/**
	 * Sets the tax amount.
	 *
	 * @param taxAmount the new tax amount
	 */
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * Gets the net payable amount.
	 *
	 * @return the net payable amount
	 */
	public Double getNetPayableAmount() {
		return netPayableAmount;
	}

	/**
	 * Sets the net payable amount.
	 *
	 * @param netPayableAmount the new net payable amount
	 */
	public void setNetPayableAmount(Double netPayableAmount) {
		this.netPayableAmount = netPayableAmount;
	}

	/**
	 * Gets the payment mode.
	 *
	 * @return the payment mode
	 */
	public Integer getPaymentMode() {
		return paymentMode;
	}

	/**
	 * Sets the payment mode.
	 *
	 * @param paymentMode the new payment mode
	 */
	public void setPaymentMode(Integer paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * Gets the invoice status.
	 *
	 * @return the invoice status
	 */
	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 * Sets the invoice status.
	 *
	 * @param invoiceStatus the new invoice status
	 */
	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	/**
	 * Gets the invoice link.
	 *
	 * @return the invoice link
	 */
	public String getInvoiceLink() {
		return invoiceLink;
	}

	/**
	 * Sets the invoice link.
	 *
	 * @param invoiceLink the new invoice link
	 */
	public void setInvoiceLink(String invoiceLink) {
		this.invoiceLink = invoiceLink;
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
	 * Gets the payment date.
	 *
	 * @return the payment date
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * Sets the payment date.
	 *
	 * @param paymentDate the new payment date
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * Gets the payment reference.
	 *
	 * @return the payment reference
	 */
	public String getPaymentReference() {
		return paymentReference;
	}

	/**
	 * Sets the payment reference.
	 *
	 * @param paymentReference the new payment reference
	 */
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
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
	 * Gets the service request invoice components.
	 *
	 * @return the service request invoice components
	 */
	public List<ServiceRequestInvoiceComponentModel> getServiceRequestInvoiceComponents() {
		return serviceRequestInvoiceComponents;
	}

	/**
	 * Sets the service request invoice components.
	 *
	 * @param serviceRequestInvoiceComponents the new service request invoice components
	 */
	public void setServiceRequestInvoiceComponents(
			List<ServiceRequestInvoiceComponentModel> serviceRequestInvoiceComponents) {
		this.serviceRequestInvoiceComponents = serviceRequestInvoiceComponents;
	}
	
	/**
	 * Gets the digital kit guid.
	 *
	 * @return the digital kit guid
	 */
	public String getDigitalKitGuid() {
		return digitalKitGuid;
	}

	/**
	 * Sets the digital kit guid.
	 *
	 * @param digitalKitGuid the new digital kit guid
	 */
	public void setDigitalKitGuid(String digitalKitGuid) {
		this.digitalKitGuid = digitalKitGuid;
	}

	/**
	 * The Interface validateCreateServiceRequestInvoice.
	 */
	public interface validateCreateServiceRequestInvoice{}
	
	/**
	 * The Interface validateUpdateServiceRequestInvoice.
	 */
	public interface validateUpdateServiceRequestInvoice{}
	
	/**
	 * The Interface ValidateuploadInvoiceComponent.
	 */
	public interface ValidateuploadInvoiceComponent{}
	
}

/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: DigitalKitModel.java
*
* Date Author Changes
* 7 Aug, 2017 Sambit Swain Created
*/
package com.nhance.technician.model.newapis;

import java.util.List;

/**
 * The Class DigitalKitModel.
 */
public class DigitalKitModel extends MessageModel{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2092613599201622502L;

	/** The digital kit code. */
	private String digitalKitCode;
	
	/** The status. */
	private Integer dkStatus;
	
	/** The product serial number. */
	private String productSerialNumber;
	
	/** The purchage date. */
	private Long purchaseDate;
	
	/** The invoice number. */
	private String invoiceNumber;
	
	/** The amount. */
	private Double amount;
	
	/** The issue by type. */
	private Integer issueByType;
	
	/** The is downloaded. */
	private Integer isDownloaded;
	
	/** The is slno validated. */
	private Integer isSlnoValidated;
	
	/** The is purchage date validated. */
	private Integer isPurchaseDateValidated;
	
	/** The is invoice validated. */
	private Integer isInvoiceValidated;
	
	/** The outlet code. */
	private String outletCode; //TODO:
	
	/** The list digital kit alert schedule. */
	private List<DigitalKitAlertScheduleModel> listDigitalKitAlertScheduleModel;
	
	/** The digital kit warranty. */
	private DigitalKitWarrantyModel digitalKitWarrantyModel;
	
	/** The document. */
	private List<DocumentModel> documentModel;
	
	/** The user guid. */
	private String userGuid;
	
	/** The product guid. */
	private String productGuid;
	
	/** The user name. */
	private String consumerName;
	
	/** The user mobile number. */
	private String consumerMobileNumber;
	
	/** The user email id. */
	private String consumerEmailId;
	
	/** The product model number. */
	private String productModelNumber;
	
	/** The product sub category. */
	private String productSubCategory;
	
	/** The manufacturer name. */
	private String manufacturerName;
	
	/** The product sub category. */
	private String productSubCategoryGuid;
	
	/** The manufacturer name. */
	private String manufacturerGuid;
	
	/** The warranty period. */
	private Integer warrantyPeriod;
	
	/** The product category guid. */
	private String productCategoryGuid;
	
	private String productCategory;
	
	
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

	/**
	 * Gets the dk status.
	 *
	 * @return the dk status
	 */
	public Integer getDkStatus() {
		return dkStatus;
	}

	/**
	 * Sets the dk status.
	 *
	 * @param dkStatus the new dk status
	 */
	public void setDkStatus(Integer dkStatus) {
		this.dkStatus = dkStatus;
	}

	/**
	 * Gets the product serial number.
	 *
	 * @return the product serial number
	 */
	public String getProductSerialNumber() {
		return productSerialNumber;
	}

	/**
	 * Sets the product serial number.
	 *
	 * @param productSerialNumber the new product serial number
	 */
	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
	}


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
	 * @param amount the new amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the issue by type.
	 *
	 * @return the issue by type
	 */
	public Integer getIssueByType() {
		return issueByType;
	}

	/**
	 * Sets the issue by type.
	 *
	 * @param issueByType the new issue by type
	 */
	public void setIssueByType(Integer issueByType) {
		this.issueByType = issueByType;
	}

	/**
	 * Gets the checks if is downloaded.
	 *
	 * @return the checks if is downloaded
	 */
	public Integer getIsDownloaded() {
		return isDownloaded;
	}

	/**
	 * Sets the checks if is downloaded.
	 *
	 * @param isDownloaded the new checks if is downloaded
	 */
	public void setIsDownloaded(Integer isDownloaded) {
		this.isDownloaded = isDownloaded;
	}

	/**
	 * Gets the checks if is slno validated.
	 *
	 * @return the checks if is slno validated
	 */
	public Integer getIsSlnoValidated() {
		return isSlnoValidated;
	}

	/**
	 * Sets the checks if is slno validated.
	 *
	 * @param isSlnoValidated the new checks if is slno validated
	 */
	public void setIsSlnoValidated(Integer isSlnoValidated) {
		this.isSlnoValidated = isSlnoValidated;
	}


	/**
	 * Gets the purchase date.
	 *
	 * @return the purchase date
	 */
	public Long getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * Sets the purchase date.
	 *
	 * @param purchaseDate the new purchase date
	 */
	public void setPurchaseDate(Long purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * Gets the checks if is purchase date validated.
	 *
	 * @return the checks if is purchase date validated
	 */
	public Integer getIsPurchaseDateValidated() {
		return isPurchaseDateValidated;
	}

	/**
	 * Sets the checks if is purchase date validated.
	 *
	 * @param isPurchaseDateValidated the new checks if is purchase date validated
	 */
	public void setIsPurchaseDateValidated(Integer isPurchaseDateValidated) {
		this.isPurchaseDateValidated = isPurchaseDateValidated;
	}

	/**
	 * Gets the checks if is invoice validated.
	 *
	 * @return the checks if is invoice validated
	 */
	public Integer getIsInvoiceValidated() {
		return isInvoiceValidated;
	}

	/**
	 * Sets the checks if is invoice validated.
	 *
	 * @param isInvoiceValidated the new checks if is invoice validated
	 */
	public void setIsInvoiceValidated(Integer isInvoiceValidated) {
		this.isInvoiceValidated = isInvoiceValidated;
	}

	/**
	 * Gets the product sub category guid.
	 *
	 * @return the product sub category guid
	 */
	public String getProductSubCategoryGuid() {
		return productSubCategoryGuid;
	}

	/**
	 * Sets the product sub category guid.
	 *
	 * @param productSubCategoryGuid the new product sub category guid
	 */
	public void setProductSubCategoryGuid(String productSubCategoryGuid) {
		this.productSubCategoryGuid = productSubCategoryGuid;
	}

	/**
	 * Gets the manufacturer guid.
	 *
	 * @return the manufacturer guid
	 */
	public String getManufacturerGuid() {
		return manufacturerGuid;
	}

	/**
	 * Sets the manufacturer guid.
	 *
	 * @param manufacturerGuid the new manufacturer guid
	 */
	public void setManufacturerGuid(String manufacturerGuid) {
		this.manufacturerGuid = manufacturerGuid;
	}

	/**
	 * Sets the product sub category.
	 *
	 * @param productSubCategory the new product sub category
	 */
	public void setProductSubCategory(String productSubCategory) {
		this.productSubCategory = productSubCategory;
	}

	/**
	 * Gets the product sub category.
	 *
	 * @return the product sub category
	 */
	public String getProductSubCategory() {
		return productSubCategory;
	}
	/**
	 * Gets the outlet code.
	 *
	 * @return the outlet code
	 */
	public String getOutletCode() {
		return outletCode;
	}

	/**
	 * Sets the outlet code.
	 *
	 * @param outletCode the new outlet code
	 */
	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	/**
	 * Gets the list digital kit alert schedule model.
	 *
	 * @return the list digital kit alert schedule model
	 */
	public List<DigitalKitAlertScheduleModel> getListDigitalKitAlertScheduleModel() {
		return listDigitalKitAlertScheduleModel;
	}

	/**
	 * Sets the list digital kit alert schedule model.
	 *
	 * @param listDigitalKitAlertScheduleModel the new list digital kit alert schedule model
	 */
	public void setListDigitalKitAlertScheduleModel(List<DigitalKitAlertScheduleModel> listDigitalKitAlertScheduleModel) {
		this.listDigitalKitAlertScheduleModel = listDigitalKitAlertScheduleModel;
	}

	/**
	 * Gets the digital kit warranty model.
	 *
	 * @return the digital kit warranty model
	 */
	public DigitalKitWarrantyModel getDigitalKitWarrantyModel() {
		return digitalKitWarrantyModel;
	}

	/**
	 * Sets the digital kit warranty model.
	 *
	 * @param digitalKitWarrantyModel the new digital kit warranty model
	 */
	public void setDigitalKitWarrantyModel(DigitalKitWarrantyModel digitalKitWarrantyModel) {
		this.digitalKitWarrantyModel = digitalKitWarrantyModel;
	}

	/**
	 * Gets the document model.
	 *
	 * @return the document model
	 */
	public List<DocumentModel> getDocumentModel() {
		return documentModel;
	}

	/**
	 * Sets the document model.
	 *
	 * @param documentModel the new document model
	 */
	public void setDocumentModel(List<DocumentModel> documentModel) {
		this.documentModel = documentModel;
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
	 * Gets the product guid.
	 *
	 * @return the product guid
	 */
	public String getProductGuid() {
		return productGuid;
	}

	/**
	 * Sets the product guid.
	 *
	 * @param productGuid the new product guid
	 */
	public void setProductGuid(String productGuid) {
		this.productGuid = productGuid;
	}

	
	/**
	 * Gets the consumer name.
	 *
	 * @return the consumer name
	 */
	public String getConsumerName() {
		return consumerName;
	}

	/**
	 * Sets the consumer name.
	 *
	 * @param consumerName the new consumer name
	 */
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	/**
	 * Gets the consumer mobile number.
	 *
	 * @return the consumer mobile number
	 */
	public String getConsumerMobileNumber() {
		return consumerMobileNumber;
	}

	/**
	 * Sets the consumer mobile number.
	 *
	 * @param consumerMobileNumber the new consumer mobile number
	 */
	public void setConsumerMobileNumber(String consumerMobileNumber) {
		this.consumerMobileNumber = consumerMobileNumber;
	}

	/**
	 * Gets the consumer email id.
	 *
	 * @return the consumer email id
	 */
	public String getConsumerEmailId() {
		return consumerEmailId;
	}

	/**
	 * Sets the consumer email id.
	 *
	 * @param consumerEmailId the new consumer email id
	 */
	public void setConsumerEmailId(String consumerEmailId) {
		this.consumerEmailId = consumerEmailId;
	}

	/**
	 * Gets the product model number.
	 *
	 * @return the product model number
	 */
	public String getProductModelNumber() {
		return productModelNumber;
	}

	/**
	 * Sets the product model number.
	 *
	 * @param productModelNumber the new product model number
	 */
	public void setProductModelNumber(String productModelNumber) {
		this.productModelNumber = productModelNumber;
	}

	/**
	 * Gets the manufacturer name.
	 *
	 * @return the manufacturer name
	 */
	public String getManufacturerName() {
		return manufacturerName;
	}

	/**
	 * Sets the manufacturer name.
	 *
	 * @param manufacturerName the new manufacturer name
	 */
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	/**
	 * Gets the warranty period.
	 *
	 * @return the warranty period
	 */
	public Integer getWarrantyPeriod() {
		return warrantyPeriod;
	}

	/**
	 * Sets the warranty period.
	 *
	 * @param warrantyPeriod the new warranty period
	 */
	public void setWarrantyPeriod(Integer warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}


	/**
	 * Gets the product category guid.
	 *
	 * @return the product category guid
	 */
	public String getProductCategoryGuid() {
		return productCategoryGuid;
	}

	/**
	 * Sets the product category guid.
	 *
	 * @param productCategoryGuid the new product category guid
	 */
	public void setProductCategoryGuid(String productCategoryGuid) {
		this.productCategoryGuid = productCategoryGuid;
	}


	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}


	/**
	 * The Interface ValidateCreateDigitalKit.
	 */
	public interface ValidateCreateDigitalKit{}
	
	/**
	 * The Interface ValidateUpdateDigitalKit.
	 */
	public interface ValidateUpdateDigitalKit{}
}

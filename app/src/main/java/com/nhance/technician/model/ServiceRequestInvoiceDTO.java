package com.nhance.technician.model;

/* Copyright Â© EasOfTech 2015. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: ServiceRequestInvoiceDTO.java
*
* Date Author Changes
* 3 Jul, 2017 Saroj Created
*/

import java.util.List;


/**
 * The Class ServiceRequestInvoiceDTO.
 */
public class ServiceRequestInvoiceDTO extends BaseModel {

    /**
     * The service request number.
     */
    private String serviceRequestNumber;

    /**
     * The invoice number.
     */
    private String invoiceNumber;

    /**
     * The service request subject.
     */
    private String serviceRequestSubject;

    /**
     * The base amount.
     */
    private Double baseAmount;

    /**
     * The total amount.
     */
    private Double totalAmount;

    /**
     * The tax amount.
     */
    private Double taxAmount;

    /**
     * The net payble amount.
     */
    private Double netPaybleAmount;

    /**
     * The discount.
     */
    private Double discount;

    /**
     * The invoice link.
     */
    private String invoiceLink;

    /**
     * The user code.
     */
    private String userCode;

    private long invoiceGenerationDate;

    /**
     * The parts.
     */
    private List<ServicePartDTO> parts;
    private int status;
    private String currencyCode;

    private Double additionLabourCharge;

    private Integer modeOfPayment;

    private Long lastSyncTime;

    private Long createdDate;

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
     * Gets the service request subject.
     *
     * @return the service request subject
     */
    public String getServiceRequestSubject() {
        return serviceRequestSubject;
    }

    /**
     * Sets the service request subject.
     *
     * @param serviceRequestSubject the new service request subject
     */
    public void setServiceRequestSubject(String serviceRequestSubject) {
        this.serviceRequestSubject = serviceRequestSubject;
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
     * Gets the net payble amount.
     *
     * @return the net payble amount
     */
    public Double getNetPaybleAmount() {
        return netPaybleAmount;
    }

    /**
     * Sets the net payble amount.
     *
     * @param netPaybleAmount the new net payble amount
     */
    public void setNetPaybleAmount(Double netPaybleAmount) {
        this.netPaybleAmount = netPaybleAmount;
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
     * Gets the parts.
     *
     * @return the parts
     */
    public List<ServicePartDTO> getParts() {
        return parts;
    }

    /**
     * Sets the parts.
     *
     * @param parts the new parts
     */
    public void setParts(List<ServicePartDTO> parts) {
        this.parts = parts;
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
     * Gets the user code.
     *
     * @return the user code
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * Sets the user code.
     *
     * @param userCode the new user code
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public long getInvoiceGenerationDate() {
        return invoiceGenerationDate;
    }

    public void setInvoiceGenerationDate(long invoiceGenerationDate) {
        this.invoiceGenerationDate = invoiceGenerationDate;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Double getAdditionLabourCharge() {
        return additionLabourCharge;
    }

    public void setAdditionLabourCharge(Double additionLabourCharge) {
        this.additionLabourCharge = additionLabourCharge;
    }

    public Integer getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(Integer modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public Long getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Long lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * The Interface CheckUploadInvoiceComponent.
     */
    public interface CheckUploadInvoiceComponent {
    }

    @Override
    public String toString() {
        return "ServiceRequestInvoiceDTO{" +
                "serviceRequestNumber='" + serviceRequestNumber + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", serviceRequestSubject='" + serviceRequestSubject + '\'' +
                ", baseAmount=" + baseAmount +
                ", totalAmount=" + totalAmount +
                ", taxAmount=" + taxAmount +
                ", netPaybleAmount=" + netPaybleAmount +
                ", discount=" + discount +
                ", invoiceLink='" + invoiceLink + '\'' +
                ", userCode='" + userCode + '\'' +
                ", invoiceGenerationDate=" + invoiceGenerationDate +
                ", parts=" + parts +
                ", status=" + status +
                ", currencyCode='" + currencyCode + '\'' +
                ", additionLabourCharge=" + additionLabourCharge +
                ", modeOfPayment=" + modeOfPayment +
                ", lastSyncTime=" + lastSyncTime +
                ", createdDate=" + createdDate +
                '}';
    }
}

package com.nhance.technician.model;

/* Copyright Â© EasOfTech 2015. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: ServiceRequestDTO.java
*
* Date Author Changes
* 3 May, 2016 Saroj Created
*/

        import java.util.Date;
        import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class ServiceRequestDTO.
 */
public class ServiceRequestDTO extends BaseModel {

    /** The service request number. */
    private String serviceRequestNumber;

    /** The service request subject. */
    private String serviceRequestSubject;

    /** The service request message. */
    private String serviceRequestMessage;

    /** The digital kit code. */
    private String digitalKitCode;

    /** The attached document. */
    private String attachedDocument;

    /** The attached document file name. */
    private String attachedDocumentFileName;

    /** The attached document format. */
    private String attachedDocumentFormat;

    /** The attached file. */
//    private MultipartFile attachedFile;

    /** The created date. */
    private Long createdDate;

    /**  nullable persistent field. */
    private Integer stage;

    /** The created date str. */
    private String createdDateStr;

    /** The created by. */
    private String createdBy;

    /**  persistent field. */
    private String updatedBy;

    /**  persistent field. */
    private Date updatedDate;

    /** The from date. */
    private String fromDate;

    /** The to date. */
    private String toDate;

    /** The customer name. */
    private String customerName;

    /** The service request history. */
    private List<ServiceRequestHistory> serviceRequestHistory;

    /** The address details. */
    private Location locationDTO;

    /** The stage str. */
    private String stageStr;

    /** The created by name. */
    private String createdByName;

    /** The product name. */
    private String productName;

    /** The new stage. */
    private Integer newStage;

    /** The under progress. */
    private Integer underProgress;

    /** The resolved. */
    private Integer resolved;

    /** The closed. */
    private Integer closed;

    /** The product image. */
    private String productImage;

    /** The city. */
    private String city;

    /** The pin code. */
    private String pinCode;

    /** The state name. */
    private String stateName;

    /** The service request history id. */
    private Long serviceRequestHistoryId;

    /** The service partner. */
    private String servicePartner;

    /** The brand logo. */
    private String brandLogo;

    /** The video feature enabled. */
    private boolean videoFeatureEnabled;

    /** The currency code. */
    private String currencyCode;

    /** The user code. */
    private String userCode;

    /** The amount. */
    private Double amount;

    /** The parts. */
    private List<ServicePartDTO> parts;

    private Integer taxPercentage;

    private String taxName;

    private Integer modeOfPayment;

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
     * Gets the service request message.
     *
     * @return the service request message
     */
    public String getServiceRequestMessage() {
        return serviceRequestMessage;
    }

    /**
     * Sets the service request message.
     *
     * @param serviceRequestMessage the new service request message
     */
    public void setServiceRequestMessage(String serviceRequestMessage) {
        this.serviceRequestMessage = serviceRequestMessage;
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

    /**
     * Gets the attached document.
     *
     * @return the attached document
     */
    public String getAttachedDocument() {
        return attachedDocument;
    }

    /**
     * Sets the attached document.
     *
     * @param attachedDocument the new attached document
     */
    public void setAttachedDocument(String attachedDocument) {
        this.attachedDocument = attachedDocument;
    }

    /**
     * Gets the attached document file name.
     *
     * @return the attached document file name
     */
    public String getAttachedDocumentFileName() {
        return attachedDocumentFileName;
    }

    /**
     * Sets the attached document file name.
     *
     * @param attachedDocumentFileName the new attached document file name
     */
    public void setAttachedDocumentFileName(String attachedDocumentFileName) {
        this.attachedDocumentFileName = attachedDocumentFileName;
    }

    /**
     * Gets the attached document format.
     *
     * @return the attached document format
     */
    public String getAttachedDocumentFormat() {
        return attachedDocumentFormat;
    }

    /**
     * Sets the attached document format.
     *
     * @param attachedDocumentFormat the new attached document format
     */
    public void setAttachedDocumentFormat(String attachedDocumentFormat) {
        this.attachedDocumentFormat = attachedDocumentFormat;
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

    /**
     * Gets the service request history.
     *
     * @return the service request history
     */
    public List<ServiceRequestHistory> getServiceRequestHistory() {
        return serviceRequestHistory;
    }

    /**
     * Sets the service request history.
     *
     * @param serviceRequestHistory the new service request history
     */
    public void setServiceRequestHistory(List<ServiceRequestHistory> serviceRequestHistory) {
        this.serviceRequestHistory = serviceRequestHistory;
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Integer getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage the new stage
     */
    public void setStage(Integer stage) {
        this.stage = stage;
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
     * Gets the updated by.
     *
     * @return the updated by
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Gets the updated date.
     *
     * @return the updated date
     */
    public Date getUpdatedDate() {
        return updatedDate;
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
     * Sets the updated by.
     *
     * @param updatedBy the new updated by
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Sets the updated date.
     *
     * @param updatedDate the new updated date
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Gets the new stage.
     *
     * @return the new stage
     */
    public Integer getNewStage() {
        return newStage;
    }

    /**
     * Sets the new stage.
     *
     * @param newStage the new new stage
     */
    public void setNewStage(Integer newStage) {
        this.newStage = newStage;
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }


    /**
     * Sets the city.
     *
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the pin code.
     *
     * @return the pin code
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * Sets the pin code.
     *
     * @param pinCode the new pin code
     */
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    /**
     * Gets the state name.
     *
     * @return the state name
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * Sets the state name.
     *
     * @param stateName the new state name
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * Gets the under progress.
     *
     * @return the under progress
     */
    public Integer getUnderProgress() {
        return underProgress;
    }

    /**
     * Sets the under progress.
     *
     * @param underProgress the new under progress
     */
    public void setUnderProgress(Integer underProgress) {
        this.underProgress = underProgress;
    }

    /**
     * Gets the resolved.
     *
     * @return the resolved
     */
    public Integer getResolved() {
        return resolved;
    }

    /**
     * Sets the resolved.
     *
     * @param resolved the new resolved
     */
    public void setResolved(Integer resolved) {
        this.resolved = resolved;
    }

    /**
     * Gets the closed.
     *
     * @return the closed
     */
    public Integer getClosed() {
        return closed;
    }

    /**
     * Sets the closed.
     *
     * @param closed the new closed
     */
    public void setClosed(Integer closed) {
        this.closed = closed;
    }

    /**
     * Gets the from date.
     *
     * @return the from date
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * Sets the from date.
     *
     * @param fromDate the new from date
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Gets the to date.
     *
     * @return the to date
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * Sets the to date.
     *
     * @param toDate the new to date
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * Gets the created date str.
     *
     * @return the created date str
     */
    public String getCreatedDateStr() {
        return createdDateStr;
    }

    /**
     * Sets the created date str.
     *
     * @param createdDateStr the new created date str
     */
    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    /**
     * Gets the customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customer name.
     *
     * @param customerName the new customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the location dto.
     *
     * @return the location dto
     */
    public Location getLocationDTO() {
        return locationDTO;
    }

    /**
     * Sets the location dto.
     *
     * @param locationDTO the new location dto
     */
    public void setLocationDTO(Location locationDTO) {
        this.locationDTO = locationDTO;
    }

    /**
     * Gets the stage str.
     *
     * @return the stage str
     */
    public String getStageStr() {
        return stageStr;
    }

    /**
     * Sets the stage str.
     *
     * @param stageStr the new stage str
     */
    public void setStageStr(String stageStr) {
        this.stageStr = stageStr;
    }

    /**
     * Gets the attached file.
     *
     * @return the attached file
     *//*
    public MultipartFile getAttachedFile() {
        return attachedFile;
    }*/

    /**
     * Sets the attached file.
     *
     * @param attachedFile the new attached file
     *//*
    public void setAttachedFile(MultipartFile attachedFile) {
        this.attachedFile = attachedFile;
    }*/

    /**
     * Gets the created by name.
     *
     * @return the created by name
     */
    public String getCreatedByName() {
        return createdByName;
    }

    /**
     * Sets the created by name.
     *
     * @param createdByName the new created by name
     */
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    /**
     * Gets the product name.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the product name.
     *
     * @param productName the new product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the product image.
     *
     * @return the product image
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     * Sets the product image.
     *
     * @param productImage the new product image
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    /**
     * Gets the service request history id.
     *
     * @return the service request history id
     */
    public Long getServiceRequestHistoryId() {
        return serviceRequestHistoryId;
    }

    /**
     * Sets the service request history id.
     *
     * @param serviceRequestHistoryId the new service request history id
     */
    public void setServiceRequestHistoryId(Long serviceRequestHistoryId) {
        this.serviceRequestHistoryId = serviceRequestHistoryId;
    }



    /**
     * Gets the service partner.
     *
     * @return the service partner
     */
    public String getServicePartner() {
        return servicePartner;
    }

    /**
     * Sets the service partner.
     *
     * @param servicePartner the new service partner
     */
    public void setServicePartner(String servicePartner) {
        this.servicePartner = servicePartner;
    }


    /**
     * Gets the brand logo.
     *
     * @return the brand logo
     */
    public String getBrandLogo() {
        return brandLogo;
    }

    /**
     * Sets the brand logo.
     *
     * @param brandLogo the new brand logo
     */
    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    /**
     * Checks if is video feature enabled.
     *
     * @return true, if is video feature enabled
     */
    public boolean isVideoFeatureEnabled() {
        return videoFeatureEnabled;
    }

    /**
     * Sets the video feature enabled.
     *
     * @param videoFeatureEnabled the new video feature enabled
     */
    public void setVideoFeatureEnabled(boolean videoFeatureEnabled) {
        this.videoFeatureEnabled = videoFeatureEnabled;
    }

    /**
     * Gets the currency code.
     *
     * @return the currency code
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the currency code.
     *
     * @param currencyCode the new currency code
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public Integer getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Integer taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Integer getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(Integer modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    /**
     * The Interface CheckCreateServiceRequest.
     */
    public interface CheckCreateServiceRequest {}

    /**
     * The Interface CheckGetServiceRequestDetails.
     */
    public interface CheckGetServiceRequestDetails {}

    /**
     * The Interface CheckReplyServiceRequest.
     */
    public interface CheckReplyServiceRequest {}

    /**
     * The Interface CheckCloseServiceRequest.
     */
    public interface CheckCloseServiceRequest {}

    /**
     * The Interface ValidateCreateServiceRequest.
     */
    public interface ValidateCreateServiceRequest {}

    /**
     * The Interface ValidateLoadServiceRequest.
     */
    public interface ValidateLoadServiceRequest {}

    /**
     * The Interface ValidateUpdateServiceRequest.
     */
    public interface ValidateUpdateServiceRequest {}

    /**
     * The Interface CheckServiceRequestDetailsForTechnician.
     */
    public interface CheckServiceRequestDetailsForTechnician {}

    @Override
    public String toString() {
        return "ServiceRequestDTO{" +
                "serviceRequestNumber='" + serviceRequestNumber + '\'' +
                ", serviceRequestSubject='" + serviceRequestSubject + '\'' +
                ", serviceRequestMessage='" + serviceRequestMessage + '\'' +
                ", digitalKitCode='" + digitalKitCode + '\'' +
                ", attachedDocument='" + attachedDocument + '\'' +
                ", attachedDocumentFileName='" + attachedDocumentFileName + '\'' +
                ", attachedDocumentFormat='" + attachedDocumentFormat + '\'' +
                ", createdDate=" + createdDate +
                ", stage=" + stage +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", customerName='" + customerName + '\'' +
                ", serviceRequestHistory=" + serviceRequestHistory +
                ", locationDTO=" + locationDTO +
                ", stageStr='" + stageStr + '\'' +
                ", createdByName='" + createdByName + '\'' +
                ", productName='" + productName + '\'' +
                ", newStage=" + newStage +
                ", underProgress=" + underProgress +
                ", resolved=" + resolved +
                ", closed=" + closed +
                ", productImage='" + productImage + '\'' +
                ", city='" + city + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", stateName='" + stateName + '\'' +
                ", serviceRequestHistoryId=" + serviceRequestHistoryId +
                ", servicePartner='" + servicePartner + '\'' +
                ", brandLogo='" + brandLogo + '\'' +
                ", videoFeatureEnabled=" + videoFeatureEnabled +
                ", currencyCode='" + currencyCode + '\'' +
                ", userCode='" + userCode + '\'' +
                ", amount=" + amount +
                ", parts=" + parts +
                ", taxPercentage=" + taxPercentage +
                ", taxName='" + taxName + '\'' +
                ", modeOfPayment=" + modeOfPayment +
                '}';
    }
}

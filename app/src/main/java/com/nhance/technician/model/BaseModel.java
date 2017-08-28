package com.nhance.technician.model;

/**
 * The type Base model.
 */
/*
*
* Id: BaseModel
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/
public class BaseModel extends RequestModel {

    /**
     * The mobile number.
     */
    private String mobileNumber;

    /**
     * The email id.
     */
    private String emailId;

    /**
     * The source type.
     */
    private String sourceType;

    /**
     * The app type.
     */
    private Integer appType;

    /**
     * The default locale.
     */
    private String defaultLocale;

    /**
     * The status.
     */
    private Integer responseStatus;

    /**
     * The message description.
     */
    private String messageDescription;

    /**
     * The version number.
     */
    private String versionNumber;

    /**
     * The device manufacturer.
     */
    private String deviceManufacturer;

    /**
     * The device model.
     */
    private String deviceModel;

    private String sellerCode;

    private String registrationId;
    private String isdCode;
    private int requestType;
    private String os;

    private String sellerName;

    private boolean serverpkwrapkeyrequired = false;

    /**
     * Instantiates a new Base model.
     */
    public BaseModel() {
    }

    /**
     * Instantiates a new Base model.
     *
     * @param requesttype the requesttype
     */
    public BaseModel(int requesttype) {
        this.requestType = requesttype;
    }

    /**
     * Gets isd code.
     *
     * @return the isd code
     */
    public String getIsdCode() {
        return isdCode;
    }

    /**
     * Sets isd code.
     *
     * @param isdCode the isd code
     */
    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    /**
     * Gets the mobile number.
     *
     * @return the mobile number
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Sets the mobile number.
     *
     * @param mobileNumber the new mobile number
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Gets the email id.
     *
     * @return the email id
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * Sets the email id.
     *
     * @param emailId the new email id
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * Gets the source type.
     *
     * @return the source type
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * Sets the source type.
     *
     * @param sourceType the new source type
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * Gets the app type.
     *
     * @return the app type
     */
    public Integer getAppType() {
        return appType;
    }

    /**
     * Sets the app type.
     *
     * @param appType the new app type
     */
    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public Integer getResponseStatus() {
        return responseStatus;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setResponseStatus(Integer status) {
        this.responseStatus = status;
    }

    /**
     * Gets the message description.
     *
     * @return the message description
     */
    public String getMessageDescription() {
        return messageDescription;
    }

    /**
     * Sets the message description.
     *
     * @param messageDescription the new message description
     */
    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    /**
     * Gets the default locale.
     *
     * @return the default locale
     */
    public String getDefaultLocale() {
        return defaultLocale!=null?defaultLocale:"en_US";
    }

    /**
     * Sets the default locale.
     *
     * @param defaultLocale the new default locale
     */
    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /**
     * Gets the version number.
     *
     * @return the version number
     */
    public String getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets the version number.
     *
     * @param versionNumber the new version number
     */
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * Gets the device manufacturer.
     *
     * @return the device manufacturer
     */
    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    /**
     * Sets the device manufacturer.
     *
     * @param deviceManufacturer the new device manufacturer
     */
    public void setDeviceManufacturer(String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    /**
     * Gets the device model.
     *
     * @return the device model
     */
    public String getDeviceModel() {
        return deviceModel;
    }

    /**
     * Sets the device model.
     *
     * @param deviceModel the new device model
     */
    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    /**
     * Gets request type.
     *
     * @return the request type
     */
    public int getRequestType() {
        return requestType;
    }

    /**
     * Sets request type.
     *
     * @param requestType the request type
     */
    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    /**
     * Isserverpkwrapkeyrequired boolean.
     *
     * @return the boolean
     */
    public boolean isserverpkwrapkeyrequired() {
        return serverpkwrapkeyrequired;
    }

    /**
     * Sets .
     *
     * @param serverpkwrapkeyrequired the serverpkwrapkeyrequired
     */
    public void setserverpkwrapkeyrequired(boolean serverpkwrapkeyrequired) {
        this.serverpkwrapkeyrequired = serverpkwrapkeyrequired;
    }

    /**
     * Gets customer code.
     *
     * @return the customer code
     */
    public String getSellerCode() {
        return sellerCode;
    }

    /**
     * Sets customer code.
     *
     * @param sellerCode the customer code
     */
    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    /**
     * Gets registration id.
     *
     * @return the registration id
     */
    public String getRegistrationId() {
        return registrationId;
    }

    /**
     * Sets registration id.
     *
     * @param registrationId the registration id
     */
    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", appType='" + appType + '\'' +
                ", defaultLocale='" + defaultLocale + '\'' +
                ", status=" + responseStatus +
                ", messageDescription='" + messageDescription + '\'' +
                ", versionNumber='" + versionNumber + '\'' +
                ", requestType=" + requestType +
                ", serverpkwrapkeyrequired=" + serverpkwrapkeyrequired +
                "} " + super.toString();
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}

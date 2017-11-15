/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.nhance.technician.model;

/*
*
* Id: Application
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/
public class Application {

	private static Application applicationInstance = null;
	private int applicationType;
    private String sourceType;
	private String IMEINo;
	private String versionNumber;
	private boolean applicationLevelEncryptionRequired = true;
	private String preferedLanguage;
	private int noOfLoginTried = 0;
	private String mobileNumber;
	private String loggedInUserName;
	private String sellerCode;
	private String sellerName;
	private String password;
	private int isdCode;
	private String userCode;
	private String os;
	private String loggedInUserProfilePicPath;

	private String userProfileUserIdOrGuid;
	private String customerId;
	private String tenantId;

	private String emailId;

	private Application()
	{
		
	}
	public static Application getInstance()
	{
		if(applicationInstance == null)
		{
			applicationInstance = new Application();
		}
		return applicationInstance;
	}

	public void clearObject(){
		applicationInstance = null;
	}

	public int getApplicationType() {
        return applicationType;
    }

	public void setApplicationType(int applicationType) {
        this.applicationType = applicationType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

	public String getIMEINo() {
		return IMEINo;
	}
	public void setIMEINo(String iMEINo) {
		IMEINo = iMEINo;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public boolean isApplicationLevelEncryptionRequired() {
		return applicationLevelEncryptionRequired;
	}
	public void setApplicationLevelEncryptionRequired(boolean applicationLevelEncryptionRequired) {
		this.applicationLevelEncryptionRequired = applicationLevelEncryptionRequired;
	}
	public void setPreferedLanguage(String preferedLanguage) {
		this.preferedLanguage = preferedLanguage;
	}
	public String getPreferedLanguage() {
		return preferedLanguage;
	}
	public int getNoOfLoginTried() {
		return noOfLoginTried;
	}
	public void setNoOfLoginTried(int noOfLoginTried) {
		this.noOfLoginTried = noOfLoginTried;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getLoggedInUserName() {
		return loggedInUserName;
	}

	public void setLoggedInUserName(String loggedInUserName) {
		this.loggedInUserName = loggedInUserName;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoggedInUserProfilePicPath() {
		return loggedInUserProfilePicPath;
	}

	public void setLoggedInUserProfilePicPath(String loggedInUserProfilePicPath) {
		this.loggedInUserProfilePicPath = loggedInUserProfilePicPath;
	}

	public String getUserProfileUserIdOrGuid() {
		return userProfileUserIdOrGuid;
	}

	public void setUserProfileUserIdOrGuid(String userProfileUserIdOrGuid) {
		this.userProfileUserIdOrGuid = userProfileUserIdOrGuid;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "Application{" +
				"applicationLevelEncryptionRequired=" + applicationLevelEncryptionRequired +
				", applicationType=" + applicationType +
				", sourceType='" + sourceType + '\'' +
				", IMEINo='" + IMEINo + '\'' +
				", versionNumber='" + versionNumber + '\'' +
				", preferedLanguage='" + preferedLanguage + '\'' +
				", noOfLoginTried=" + noOfLoginTried +
				", mobileNumber='" + mobileNumber + '\'' +
				", loggedInUserName='" + loggedInUserName + '\'' +
				", sellerCode='" + sellerCode + '\'' +
				", password='" + password + '\'' +
				", isdCode=" + isdCode +
				'}';
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public int getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(int isdCode) {
		this.isdCode = isdCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
}

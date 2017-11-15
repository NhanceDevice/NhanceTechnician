/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: UserModel.java
*
* Date Author Changes
* 5 Sep, 2017 Sudhanshu Created
*/
package com.nhance.technician.model.newapis;

import java.util.Date;
import java.util.List;

/**
 * The Class UserModel.
 */
public class UserModel extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4370892955725135965L;

	/** The user detail id. */
	private Long userDetailId;

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The dob. */
	private Date dob;

	/** The gender. */
	private String gender;

	/** The email. */
	private String email;

	/** The mobile. */
	@SuppressWarnings("deprecation")
	private String mobile;

	/** The security code. */
	private String securityCode;

	/** The account enabled. */
	private String accountEnabled;

	/** The credential expired. */
	private String credentialExpired;

	/** The login attempts. */
	private Integer loginAttempts;

	/** The user code. */
	private String userCode;

	/** The role guid. */
	private String roleGuid;

	/** The user status. */
	private Integer userStatus;

	/** The verification link. */
	private String verificationLink;

	/** The device id. */
	private String deviceId;

	/** The mac id. */
	private String macId;

	/** The referral code. */
	private String referralCode;

	/** The promotional message. */
	private String promotionalMessage;

	private Integer userType;

	private DocumentModel userProfileImage;

	private Integer isdCode;

	private AddressModel defaultAddress;

	private List<AddressModel> address;

	private String os;

	/**
	 * Gets the user detail id.
	 *
	 * @return the user detail id
	 */
	public Long getUserDetailId() {
		return userDetailId;
	}

	/**
	 * Sets the user detail id.
	 *
	 * @param userDetailId the new user detail id
	 */
	public void setUserDetailId(Long userDetailId) {
		this.userDetailId = userDetailId;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the dob.
	 *
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the dob.
	 *
	 * @param dob the new dob
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the mobile.
	 *
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Sets the mobile.
	 *
	 * @param mobile the new mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * Gets the security code.
	 *
	 * @return the security code
	 */
	public String getSecurityCode() {
		return securityCode;
	}

	/**
	 * Sets the security code.
	 *
	 * @param securityCode the new security code
	 */
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	/**
	 * Gets the account enabled.
	 *
	 * @return the account enabled
	 */
	public String getAccountEnabled() {
		return accountEnabled;
	}

	/**
	 * Sets the account enabled.
	 *
	 * @param accountEnabled the new account enabled
	 */
	public void setAccountEnabled(String accountEnabled) {
		this.accountEnabled = accountEnabled;
	}

	/**
	 * Gets the credential expired.
	 *
	 * @return the credential expired
	 */
	public String getCredentialExpired() {
		return credentialExpired;
	}

	/**
	 * Sets the credential expired.
	 *
	 * @param credentialExpired the new credential expired
	 */
	public void setCredentialExpired(String credentialExpired) {
		this.credentialExpired = credentialExpired;
	}

	/**
	 * Gets the login attempts.
	 *
	 * @return the login attempts
	 */
	public Integer getLoginAttempts() {
		return loginAttempts;
	}

	/**
	 * Sets the login attempts.
	 *
	 * @param loginAttempts the new login attempts
	 */
	public void setLoginAttempts(Integer loginAttempts) {
		this.loginAttempts = loginAttempts;
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
	 * Gets the role guid.
	 *
	 * @return the role guid
	 */
	public String getRoleGuid() {
		return roleGuid;
	}

	/**
	 * Sets the role guid.
	 *
	 * @param roleGuid the new role guid
	 */
	public void setRoleGuid(String roleGuid) {
		this.roleGuid = roleGuid;
	}

	/**
	 * Gets the user status.
	 *
	 * @return the userStatus
	 */
	public Integer getUserStatus() {
		return userStatus;
	}

	/**
	 * Sets the user status.
	 *
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * Gets the verification link.
	 *
	 * @return the verificationLink
	 */
	public String getVerificationLink() {
		return verificationLink;
	}

	/**
	 * Sets the verification link.
	 *
	 * @param verificationLink the verificationLink to set
	 */
	public void setVerificationLink(String verificationLink) {
		this.verificationLink = verificationLink;
	}

	/**
	 * Gets the device id.
	 *
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * Gets the mac id.
	 *
	 * @return the macId
	 */
	public String getMacId() {
		return macId;
	}

	/**
	 * Sets the mac id.
	 *
	 * @param macId the macId to set
	 */
	public void setMacId(String macId) {
		this.macId = macId;
	}

	/**
	 * Gets the referral code.
	 *
	 * @return the referral code
	 */
	public String getReferralCode() {
		return referralCode;
	}

	/**
	 * Sets the referral code.
	 *
	 * @param referralCode the new referral code
	 */
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	/**
	 * Gets the promotional message.
	 *
	 * @return the promotional message
	 */
	public String getPromotionalMessage() {
		return promotionalMessage;
	}

	/**
	 * Sets the promotional message.
	 *
	 * @param promotionalMessage the new promotional message
	 */
	public void setPromotionalMessage(String promotionalMessage) {
		this.promotionalMessage = promotionalMessage;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public DocumentModel getUserProfileImage() {
		return userProfileImage;
	}

	public void setUserProfileImage(DocumentModel userProfileImage) {
		this.userProfileImage = userProfileImage;
	}

	public Integer getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(Integer isdCode) {
		this.isdCode = isdCode;
	}

	public List<AddressModel> getAddress() {
		return address;
	}

	public void setAddress(List<AddressModel> address) {
		this.address = address;
		if(address != null && address.size() > 0)
			setDefaultAddress(address.get(0));
	}

	public AddressModel getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(AddressModel defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * The Interface ValidateCreateUser.
	 */
	public interface ValidateCreateUser {}

	/**
	 * The Interface validateUpdateUser.
	 */
	public interface ValidateUpdateUser {}

	/**
	 * The Interface findUserList.
	 */
	public interface FindUserList {}

	/**
	 * The Interface deviceRegistration.
	 */
	public interface DeviceRegistration {}


}

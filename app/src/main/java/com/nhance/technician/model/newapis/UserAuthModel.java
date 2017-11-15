/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: UserAuthEntity.java
*
* Date Author Changes
* 23 Aug, 2017 Saroj Created
*/
package com.nhance.technician.model.newapis;

import java.util.List;

/**
 * The Class UserAuthModel.
 */
public class UserAuthModel extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -300131691707933444L;
	
	/** The login principal. */
	private String loginPrincipal;

	/** The password. */
	private String password;
	
	/** The authorities. */
	private List<String> authorities;
	
	/** The user type. */
	private String userType;

	/** The is account locked. */
	private boolean isAccountLocked;

	/** The first time login. */
	private Boolean firstTimeLogin;

	private Integer isdCode;

	private String referralCode;

	private String promotionalMessage;

	private String firstName;

	private String userProfileImage;

	/**
	 * Gets the login principal.
	 *
	 * @return the login principal
	 */
	public String getLoginPrincipal() {
		return loginPrincipal;
	}

	/**
	 * Sets the login principal.
	 *
	 * @param loginPrincipal the new login principal
	 */
	public void setLoginPrincipal(String loginPrincipal) {
		this.loginPrincipal = loginPrincipal;
	}

	/**
	 * Gets the first time login.
	 *
	 * @return the first time login
	 */
	public Boolean getFirstTimeLogin() {
		return firstTimeLogin;
	}

	/**
	 * Sets the first time login.
	 *
	 * @param firstTimeLogin the new first time login
	 */
	public void setFirstTimeLogin(Boolean firstTimeLogin) {
		this.firstTimeLogin = firstTimeLogin;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the authorities.
	 *
	 * @return the authorities
	 */
	public List<String> getAuthorities() {
		return authorities;
	}

	/**
	 * Sets the authorities.
	 *
	 * @param authorities the new authorities
	 */
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	/**
	 * Checks if is account locked.
	 *
	 * @return true, if is account locked
	 */
	public boolean isAccountLocked() {
		return isAccountLocked;
	}

	/**
	 * Sets the account locked.
	 *
	 * @param isAccountLocked the new account locked
	 */
	public void setAccountLocked(boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	/**
	 * Gets the user type.
	 *
	 * @return the user type
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Sets the user type.
	 *
	 * @param userType the new user type
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserProfileImage() {
		return userProfileImage;
	}

	public void setUserProfileImage(String userProfileImage) {
		this.userProfileImage = userProfileImage;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(Integer isdCode) {
		this.isdCode = isdCode;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public String getPromotionalMessage() {
		return promotionalMessage;
	}

	public void setPromotionalMessage(String promotionalMessage) {
		this.promotionalMessage = promotionalMessage;
	}
}

package com.nhance.technician.model.newapis;

/**
 * The Class ChangePasswordModel.
 */
public class ChangePasswordModel extends MessageModel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 771896099129845928L;
	
	/** The old password. */
	private String oldPassword;
	
	/** The new password. */
	private String newPassword;
	
	/** The login principal. */
	private String loginPrincipal;
	
	/** The user code. */
	private String userCode;
	 
	/** The otp. */
	private String otp;

	private Integer isdCode;

	private String profileImage;

	private String referralCode;

	private String promotionalMessage;

	private String firstName;
	/**
	 * Gets the old password.
	 *
	 * @return the old password
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	
	/**
	 * Sets the old password.
	 *
	 * @param oldPassword the new old password
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	/**
	 * Gets the new password.
	 *
	 * @return the new password
	 */
	public String getNewPassword() {
		return newPassword;
	}
	
	/**
	 * Sets the new password.
	 *
	 * @param newPassword the new new password
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

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
	 * Gets the otp.
	 *
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * Sets the otp.
	 *
	 * @param otp the new otp
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Integer getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(Integer isdCode) {
		this.isdCode = isdCode;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * The Interface ValidateAuth.
	 */
	public interface ValidateAuth{}
	
}

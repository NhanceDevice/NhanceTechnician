package com.nhance.technician.model;

/* Copyright Â© EasOfTech 2015. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: SellerLoginDTO.java
*
* Date Author Changes
* 27 Apr, 2016 Saroj Created
*/


/**
 * The Class SellerLoginDTO.
 */
public class SellerLoginDTO extends BaseModel {

    /** The password. */
    private String password;

    /** The old password. */
    private String oldPassword;

    /** The is first login. */
    private boolean isFirstLogin;

    /** The user code. */
    private String userCode;

    /** The otp. */
    private String otp;

    /** The user name. */
    private String userName;

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
     * Checks if is first login.
     *
     * @return true, if is first login
     */
    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    /**
     * Sets the first login.
     *
     * @param isFirstLogin the new first login
     */
    public void setFirstLogin(boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
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

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * The Interface CheckProcessLogin.
     */
    public interface CheckProcessLogin {}

    /**
     * The Interface CheckChangePassword.
     */
    public interface CheckChangePassword {}

    /**
     * The Interface CheckForgotPassword.
     */
    public interface CheckForgotPassword {}

    /**
     * The Interface CheckValidateOtp.
     */
    public interface CheckValidateOtp {}

    /**
     * The Interface CheckResetPassword.
     */
    public interface CheckResetPassword {}

    /**
     * The Interface CheckUserRegisterDeviceId.
     */
    public interface CheckUserRegisterDeviceId {}

    /**
     * The Interface CheckLogin.
     */
    public interface CheckLogin {}

    @Override
    public String toString() {
        return "SellerLoginDTO{" +
                "password='" + password + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", isFirstLogin=" + isFirstLogin +
                ", userCode='" + userCode + '\'' +
                ", otp='" + otp + '\'' +
                ", userName='" + userName + '\'' +
                '}' + super.toString();
    }
}

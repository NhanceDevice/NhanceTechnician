package com.nhance.technician.model.newapis;
/* Copyright © Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ContactModel.java
*
* Date Author Changes
* 24 Oct, 2017 Saroj Created
*/

public class ContactModel {

    private String email;

    private String mobile;

    private Integer isdCode;

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
     * Gets the isd code.
     *
     * @return the isd code
     */
    public Integer getIsdCode() {
        return isdCode;
    }

    /**
     * Sets the isd code.
     *
     * @param isdCode the new isd code
     */
    public void setIsdCode(Integer isdCode) {
        this.isdCode = isdCode;
    }

}
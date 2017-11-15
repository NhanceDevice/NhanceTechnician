/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: SyncModel.java
*
* Date Author Changes
* 17 Oct, 2017 Saroj Created
*/
package com.nhance.technician.model.newapis;

import java.util.List;

/**
 * The Class SyncModel.
 */
public class SyncModel extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The digital kit. */
	private List<DigitalKitModel> digitalKit;
	
	/** The currency code. */
	private String currencyCode;
	
	/** The tnc. */
	private String tnc;
	
	/** The privacy policy. */
	private String privacyPolicy;

	/**
	 * Gets the digital kit.
	 *
	 * @return the digital kit
	 */
	public List<DigitalKitModel> getDigitalKit() {
		return digitalKit;
	}

	/**
	 * Sets the digital kit.
	 *
	 * @param digitalKit the new digital kit
	 */
	public void setDigitalKit(List<DigitalKitModel> digitalKit) {
		this.digitalKit = digitalKit;
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
	 * Gets the tnc.
	 *
	 * @return the tnc
	 */
	public String getTnc() {
		return tnc;
	}

	/**
	 * Sets the tnc.
	 *
	 * @param tnc the new tnc
	 */
	public void setTnc(String tnc) {
		this.tnc = tnc;
	}

	/**
	 * Gets the privacy policy.
	 *
	 * @return the privacy policy
	 */
	public String getPrivacyPolicy() {
		return privacyPolicy;
	}

	/**
	 * Sets the privacy policy.
	 *
	 * @param privacyPolicy the new privacy policy
	 */
	public void setPrivacyPolicy(String privacyPolicy) {
		this.privacyPolicy = privacyPolicy;
	}

}

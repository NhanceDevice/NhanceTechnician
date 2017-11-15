/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: PurchaseSlabModel.java
*
* Date Author Changes
* 23 Aug, 2017 Swadhin Created
*/
package com.nhance.technician.model.newapis;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class PurchaseSlabModel.
 */
public class PurchaseSlabModel extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5764549518841390002L;

	/** The min value. */
	private Long minValue;
	
	/** The max value. */
	private Long maxValue;

	/** The purchase quotation models. */
	private List<PurchaseQuotationModel> purchaseQuotationModels = new ArrayList<PurchaseQuotationModel>();
	
	/**
	 * Gets the min value.
	 *
	 * @return the min value
	 */
	public Long getMinValue() {
		return minValue;
	}

	/**
	 * Sets the min value.
	 *
	 * @param minValue the new min value
	 */
	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}

	/**
	 * Gets the max value.
	 *
	 * @return the max value
	 */
	public Long getMaxValue() {
		return maxValue;
	}

	/**
	 * Sets the max value.
	 *
	 * @param maxValue the new max value
	 */
	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}

	public List<PurchaseQuotationModel> getPurchaseQuotationModels() {
		return purchaseQuotationModels;
	}

	public void setPurchaseQuotationModels(List<PurchaseQuotationModel> purchaseQuotationModels) {
		this.purchaseQuotationModels = purchaseQuotationModels;
	}
	
}

/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: SyncMasterDataModel.java
*
* Date Author Changes
* 17 Oct, 2017 Saroj Created
*/
package com.nhance.technician.model.newapis;

import java.util.List;

/**
 * The Class SyncMasterDataModel.
 */
public class SyncMasterDataModel extends MessageModel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5665540438427034028L;

	/** The Product sub category. */
	private List<MasterDataModel> ProductSubCategory;
	
	/** The manufacturer. */
	private List<MasterDataModel> manufacturer;

	/**
	 * Gets the product sub category.
	 *
	 * @return the product sub category
	 */
	public List<MasterDataModel> getProductSubCategory() {
		return ProductSubCategory;
	}

	/**
	 * Sets the product sub category.
	 *
	 * @param productSubCategory the new product sub category
	 */
	public void setProductSubCategory(List<MasterDataModel> productSubCategory) {
		ProductSubCategory = productSubCategory;
	}

	/**
	 * Gets the manufacturer.
	 *
	 * @return the manufacturer
	 */
	public List<MasterDataModel> getManufacturer() {
		return manufacturer;
	}

	/**
	 * Sets the manufacturer.
	 *
	 * @param manufacturer the new manufacturer
	 */
	public void setManufacturer(List<MasterDataModel> manufacturer) {
		this.manufacturer = manufacturer;
	}

}

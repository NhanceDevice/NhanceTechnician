/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: PurchasePlanModel.java
*
* Date Author Changes
* 23 Aug, 2017 Swadhin Created
*/
package com.nhance.technician.model.newapis;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class PurchasePlanModel.
 */
public class PurchasePlanModel extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5878276788882793150L;
	
	/** The type. */
	private Integer type;
	
	/** The name. */
	private String name;
	
	/** The model. */
	private String model;
	
	/** The purchase type. */
	private String purchaseType;
	
	private String psc;
	
	/** The purchase slab models. */
	private List<PurchaseSlabModel> purchaseSlabModels = new ArrayList<PurchaseSlabModel>();
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * Gets the purchase type.
	 *
	 * @return the purchase type
	 */
	public String getPurchaseType() {
		return purchaseType;
	}

	/**
	 * Sets the purchase type.
	 *
	 * @param purchaseType the new purchase type
	 */
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	/**
	 * Gets the purchase slab models.
	 *
	 * @return the purchase slab models
	 */
	public List<PurchaseSlabModel> getPurchaseSlabModels() {
		return purchaseSlabModels;
	}

	/**
	 * Sets the purchase slab models.
	 *
	 * @param purchaseSlabModels the new purchase slab models
	 */
	public void setPurchaseSlabModels(List<PurchaseSlabModel> purchaseSlabModels) {
		this.purchaseSlabModels = purchaseSlabModels;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	public String getPsc() {
		return psc;
	}

	public void setPsc(String psc) {
		this.psc = psc;
	}

}

/* Copyright � EasOfTech 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: PurchaseQuotationModel.java
*
* Date Author Changes
* 23 Aug, 2017 Swadhin Created
*/
package com.nhance.technician.model.newapis;

import java.util.List;

/**
 * The Class PurchaseQuotationModel.
 */
public class PurchaseQuotationModel extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7894612502426918149L;

	/** The year. */
	private Integer year;
	
	/** The month. */
	private Integer month;
	
	/** The price. */
	private Double price;
	
	/** The description. */
	private String description;
	
	/** The image url. */
	private String imageUrl;
	
	/** The document url. */
	private String documentUrl;
	
	/** The Features. */
	private List<String> Features;
	
	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * Gets the month.
	 *
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * Sets the month.
	 *
	 * @param month the new month
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the image url.
	 *
	 * @return the image url
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Sets the image url.
	 *
	 * @param imageUrl the new image url
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Gets the document url.
	 *
	 * @return the document url
	 */
	public String getDocumentUrl() {
		return documentUrl;
	}

	/**
	 * Sets the document url.
	 *
	 * @param documentUrl the new document url
	 */
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	/**
	 * Gets the features.
	 *
	 * @return the features
	 */
	public List<String> getFeatures() {
		return Features;
	}

	/**
	 * Sets the features.
	 *
	 * @param features the new features
	 */
	public void setFeatures(List<String> features) {
		Features = features;
	}

}

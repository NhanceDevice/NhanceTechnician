/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: MasterDataModel.java
*
* Date Author Changes
* 15 Sep, 2017 Saroj Created
*/
package com.nhance.technician.model.newapis;

/**
 * The Class MasterDataModel.
 */
public class MasterDataModel extends MessageModel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3117431530260514388L;

	/** The entity name. */
	private String entityName;
	
	/** The name. */
	private String name;
	
	/** The parent id. */
	private String parentId;

	/**
	 * Gets the entity name.
	 *
	 * @return the entity name
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * Sets the entity name.
	 *
	 * @param entityName the new entity name
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

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
	 * Gets the parent id.
	 *
	 * @return the parent id
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Sets the parent id.
	 *
	 * @param parentId the new parent id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}

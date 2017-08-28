/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.nhance.technician.database.migration;


import com.nhance.technician.database.DatabaseBaseModel;


/*
*
* Id: RenameTableName
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/


public class RenameTableName extends DatabaseBaseModel {

	private String oldTableName;
	private String newTableName;
	
	public RenameTableName(int operationType, String oldTableName, String newTableName) {
		super(operationType);
		
		this.oldTableName = oldTableName;
		this.newTableName = newTableName;
	}

	public String getOldTableName() {
		return oldTableName;
	}

	public void setOldTableName(String oldTableName) {
		this.oldTableName = oldTableName;
	}

	public String getNewTableName() {
		return newTableName;
	}

	public void setNewTableName(String newTableName) {
		this.newTableName = newTableName;
	}
}

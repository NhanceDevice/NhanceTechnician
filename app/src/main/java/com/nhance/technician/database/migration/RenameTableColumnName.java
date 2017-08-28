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
* Id: RenameTableColumnName
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/


public class RenameTableColumnName extends DatabaseBaseModel {
	
	private String tableName;
	private String oldTableColumnName;
	private String newTableColumnName;

	public RenameTableColumnName(int operationType, String tableName, String oldTableColumnName, String newTableColumnName) {
		super(operationType);

		this.tableName = tableName;
		this.oldTableColumnName = oldTableColumnName;
		this.newTableColumnName = newTableColumnName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getOldTableColumnName() {
		return oldTableColumnName;
	}

	public void setOldTableColumnName(String oldTableColumnName) {
		this.oldTableColumnName = oldTableColumnName;
	}

	public String getNewTableColumnName() {
		return newTableColumnName;
	}

	public void setNewTableColumnName(String newTableColumnName) {
		this.newTableColumnName = newTableColumnName;
	}
}

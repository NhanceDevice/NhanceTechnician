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
* Id: DeleteTableColumn
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/


public class DeleteTableColumn extends DatabaseBaseModel {

	private String tableName;
	private String tableColumnName;
	
	public DeleteTableColumn(int operationType, String tableName, String tableColumnName) {
		super(operationType);

		this.tableName = tableName;
		this.tableColumnName = tableColumnName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableColumnName() {
		return tableColumnName;
	}

	public void setTableColumnName(String tableColumnName) {
		this.tableColumnName = tableColumnName;
	}
}

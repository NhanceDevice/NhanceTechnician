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
* Id: DeleteTable
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/


public class DeleteTable extends DatabaseBaseModel {

	private String tableName;
	
	public DeleteTable(int operationType, String tableName) {
		super(operationType);

		this.tableName = tableName;	
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}

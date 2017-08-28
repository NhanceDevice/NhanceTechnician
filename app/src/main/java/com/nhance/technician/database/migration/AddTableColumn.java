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

import com.nhance.technician.database.AppDatabaseUpgrade;
import com.nhance.technician.database.DatabaseBaseModel;

/*
*
* Id: AddTableColumn
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/


public class AddTableColumn extends DatabaseBaseModel {
	
	private String tableName;
	private String newTableColumnName;
	private String columnType = "TEXT";
	private int isNotNull = AppDatabaseUpgrade.VALUE_NOT_SET;
	private int defaultVal = AppDatabaseUpgrade.VALUE_NOT_SET;
	private int isPrimaryKey = AppDatabaseUpgrade.VALUE_NOT_SET;
	private boolean isAutoIncrement = false;
	private Object defaultValueToSet;
	

	public AddTableColumn(int operationType, String tableName, String newTableColumnName, String columnType, int isNotNull, int defaultVal, int isPrimaryKey, boolean isAutoIncrement, Object defaultValueToSet) {
		super(operationType);

		this.tableName = tableName;
		this.newTableColumnName = newTableColumnName;
		this.columnType = columnType;
		this.isNotNull = isNotNull;
		this.defaultVal = defaultVal;
		this.isPrimaryKey = isPrimaryKey;
		this.isAutoIncrement = isAutoIncrement;
		this.defaultValueToSet = defaultValueToSet;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getNewTableColumnName() {
		return newTableColumnName;
	}

	public void setNewTableColumnName(String newTableColumnName) {
		this.newTableColumnName = newTableColumnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public int getIsNotNull() {
		return isNotNull;
	}

	public void setIsNotNull(int isNotNull) {
		this.isNotNull = isNotNull;
	}

	public int getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(int defaultVal) {
		this.defaultVal = defaultVal;
	}

	public int getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(int isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public Object getDefaultValueToSet() {
		return defaultValueToSet;
	}

	public void setDefaultValueToSet(Object defaultValueToSet) {
		this.defaultValueToSet = defaultValueToSet;
	}
}

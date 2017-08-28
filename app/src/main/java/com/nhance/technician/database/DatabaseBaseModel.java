/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.nhance.technician.database;


/*
*
* Id: DatabaseBaseModel
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/


public abstract class DatabaseBaseModel {
	
	private int operationType;
	
	public DatabaseBaseModel(int operationType) {
		this.operationType = operationType;
	}
	
	public int getOperationType() {
		return operationType;
	}
	
}
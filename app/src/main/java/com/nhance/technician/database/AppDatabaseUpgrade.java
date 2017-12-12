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

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.nhance.technician.database.migration.AddTableColumn;
import com.nhance.technician.database.migration.DeleteTable;
import com.nhance.technician.database.migration.DeleteTableColumn;
import com.nhance.technician.database.migration.RenameTableColumnName;
import com.nhance.technician.database.migration.RenameTableName;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


/*
*
* Id: AppDatabaseUpgrade
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/


public class AppDatabaseUpgrade {

	private final static String TAG = AppDatabaseUpgrade.class.getName();

	public static final int RENAME_TABLE = 1;//Rename the existing table.
	public static final int RENAME_TABLE_COLUMN = 2;//Rename the column name or change data type of column for existing table. //http://stackoverflow.com/questions/805363/how-do-i-rename-a-column-in-a-sqlite-database-table
	public static final int ADD_TABLE_COLUMN = 3;//Add the column to the existing table and update the table with default value for already existing records.
	public static final int DELETE_TABLE_COLUMN = 4;//Delete the column from the existing table. Note: Check column is primary or foreign key.
	public static final int DELETE_TABLE = 5;//Delete the existing table. Note: Check column is primary or foreign key.

	public static String PRAGMA_INFO_TABLE_COLUMN_NAME = "name";
	public static String PRAGMA_INFO_TABLE_COLUMN_PRIMARY_KEY = "pk";
	public static String PRAGMA_INFO_TABLE_COLUMN_ISAUTOINCREMENT = "isAutoIncrementSet";
	public static String PRAGMA_INFO_TABLE_COLUMN_TYPE = "type";
	public static String PRAGMA_INFO_TABLE_COLUMN_NOTNULL = "notnull";
	public static String PRAGMA_INFO_TABLE_COLUMN_DEFAULT_VALUE = "dflt_value";
	public static final int VALUE_NOT_SET = 0;
	public static final int VALUE_SET = 1;
	public static final String TEMPORARY_TABLE_CONSTANT = "_Temp";

	private SQLiteDatabase database;

	public AppDatabaseUpgrade(SQLiteDatabase database) {
		this.database = database;
	}

	public boolean onUpgrade(int versionCodeToExecute) {

		try{
			if(database != null && database.isOpen())
			{
				List<DatabaseBaseModel> statements = AppDBUpgradeStatements.getStatements(versionCodeToExecute);

				if(statements != null && statements.size() > 0)
				{
					for(DatabaseBaseModel databaseBaseModel : statements)
					{
						final Integer operationToExecute = databaseBaseModel.getOperationType();
						LOG.d(TAG, "operationToExecute: " + operationToExecute);

						switch (operationToExecute) {
							case RENAME_TABLE:
							{
								LOG.d(TAG, "RENAME_TABLE operationToExecute");
								RenameTableName renameTableName = (RenameTableName)databaseBaseModel;

								if(renameTableName != null)
								{
									String oldTableName = renameTableName.getOldTableName();
									LOG.d(TAG, "oldTableName: " + oldTableName);
									String newTableName = renameTableName.getNewTableName();
									LOG.d(TAG, "newTableName: " + newTableName);

									if(alterTableName(oldTableName, newTableName))
										LOG.d(TAG, "RENAME_TABLE success");
									else
										LOG.d(TAG, "RENAME_TABLE failed");
								}
								else
									LOG.d(TAG, "RENAME_TABLE renameTableName failed");

								break;
							}
							case RENAME_TABLE_COLUMN:
							{
								LOG.d(TAG, "RENAME_TABLE_COLUMN operationToExecute");

								RenameTableColumnName renameTableColumnName = (RenameTableColumnName)databaseBaseModel;

								if(renameTableColumnName != null)
								{
									String tableName = renameTableColumnName.getTableName();
									LOG.d(TAG, "tableName: " + tableName);

									String oldTableColumnName = renameTableColumnName.getOldTableColumnName();
									LOG.d(TAG, "oldTableColumnName: " + oldTableColumnName);
									String newTableColumnName = renameTableColumnName.getNewTableColumnName();
									LOG.d(TAG, "newTableColumnName: " + newTableColumnName);

									if(renameTableColumnNames(tableName, oldTableColumnName, newTableColumnName))
										LOG.d(TAG, "RENAME_TABLE_COLUMN success");
									else
										LOG.d(TAG, "RENAME_TABLE_COLUMN failed");
								}
								else
									LOG.d(TAG, "RENAME_TABLE_COLUMN renameTableColumnName failed");

								break;
							}
							case ADD_TABLE_COLUMN:
							{
								LOG.d(TAG, "ADD_TABLE_COLUMN operationToExecute");

								AddTableColumn addTableColumn = (AddTableColumn)databaseBaseModel;

								if(addTableColumn != null)
								{
									if(addTableColumn(addTableColumn))
										LOG.d(TAG, "ADD_TABLE_COLUMN success");
									else
										LOG.d(TAG, "ADD_TABLE_COLUMN failed");
								}

								break;
							}
							case DELETE_TABLE_COLUMN:
							{
								LOG.d(TAG, "DELETE_TABLE_COLUMN operationToExecute");

								DeleteTableColumn deleteTableColumn = (DeleteTableColumn)databaseBaseModel;

								if(deleteTableColumn != null)
								{
									String tableName = deleteTableColumn.getTableName();
									LOG.d(TAG, "tableName: " + tableName);
									String tableColumnNameToDelete = deleteTableColumn.getTableColumnName();
									LOG.d(TAG, "tableColumnNameToDelete: " + tableColumnNameToDelete);

									if(deleteTableColumns(tableName, tableColumnNameToDelete))
										LOG.d(TAG, "DELETE_TABLE_COLUMN success");
									else
										LOG.d(TAG, "DELETE_TABLE_COLUMN failed");
								}

								break;
							}
							case DELETE_TABLE:
							{
								LOG.d(TAG, "DELETE_TABLE operationToExecute");

								DeleteTable deleteTable = (DeleteTable)databaseBaseModel;

								if(deleteTable != null)
								{
									String tableName = deleteTable.getTableName();

									if(dropTable(tableName))
										LOG.d(TAG, "DELETE_TABLE success");
									else
										LOG.d(TAG, "DELETE_TABLE failed");
								}
								break;
							}

							default:
								break;
						}
					}

					executeAfterUpgrade(versionCodeToExecute);
				}
			}
			else
				return false;

			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	private void executeAfterUpgrade(int versionCodeExecuted) {

		switch (versionCodeExecuted)
		{

		}
	}



	// For to Delete the directory inside list of files and inner Directory
	public boolean deleteDir(File dir) {
		if (dir.isDirectory()) {

			String[] children = dir.list();
			if(children != null && children.length > 0){
				for (int i=0; i<children.length; i++) {
					boolean success = deleteDir(new File(dir, children[i]));
					if (!success) {
						return false;
					}
				}
			}
		}

		return dir.delete();// The directory is now empty so delete it
	}





	protected boolean tableHasAutoIncrement(String tableName) {
		//http://stackoverflow.com/questions/18694393/how-could-you-get-if-a-table-is-autoincrement-or-not-from-the-metadata-of-an-sql
		boolean status = false;
		Cursor cursor = null;
		try{

			String sqlStatement = "SELECT 1 FROM sqlite_master WHERE type = 'table' AND name = '"+tableName+"' AND sql LIKE '%AUTOINCREMENT%'";

			cursor = database.rawQuery(sqlStatement, null);

			if(cursor != null)
				status = cursor.moveToNext();

		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			cursor.close();
		}

		return status;
	}

	protected LinkedHashMap<String, LinkedHashMap<String, Object>> getTableDetails(String tableName) {
		//http://stackoverflow.com/questions/11753871/getting-the-type-of-a-column-in-sqlite
		LinkedHashMap<String, LinkedHashMap<String, Object>> tableInfo = new LinkedHashMap<String, LinkedHashMap<String,Object>>();

		Cursor cursor = null;
		try{

			String sqlStatement = "PRAGMA table_info("+tableName+")";

			cursor = database.rawQuery(sqlStatement, null);

			if(cursor != null)
			{
				int nameIdx = cursor.getColumnIndexOrThrow(PRAGMA_INFO_TABLE_COLUMN_NAME);
				int pkValueIdx = cursor.getColumnIndexOrThrow(PRAGMA_INFO_TABLE_COLUMN_PRIMARY_KEY);
				int typeIdx = cursor.getColumnIndexOrThrow(PRAGMA_INFO_TABLE_COLUMN_TYPE);
				int notNullIdx = cursor.getColumnIndexOrThrow(PRAGMA_INFO_TABLE_COLUMN_NOTNULL);
				int dfltValueIdx = cursor.getColumnIndexOrThrow(PRAGMA_INFO_TABLE_COLUMN_DEFAULT_VALUE);
				Boolean isAutoIncrement = tableHasAutoIncrement(tableName);
				LOG.d(TAG, "isAutoIncrement: " + isAutoIncrement);

				while(cursor.moveToNext())
				{
					String columnName = cursor.getString(nameIdx);
					LOG.d(TAG, "columnName: " + columnName);
					String columnType = cursor.getString(typeIdx);
					LOG.d(TAG, "columnType: " + columnType);
					Integer notNull = cursor.getInt(notNullIdx);
					LOG.d(TAG, "notNull: " + notNull);
					Integer defaultVal = cursor.getInt(dfltValueIdx);
					LOG.d(TAG, "defaultVal: " + defaultVal);
					Integer pk = cursor.getInt(pkValueIdx);
					LOG.d(TAG, "pk: " + pk);

					LinkedHashMap<String, Object> columnInfo = new LinkedHashMap<String, Object>();
					columnInfo.put(PRAGMA_INFO_TABLE_COLUMN_PRIMARY_KEY, pk);

					if(pk == VALUE_SET && isAutoIncrement)
						columnInfo.put(PRAGMA_INFO_TABLE_COLUMN_ISAUTOINCREMENT, true);
					else if((pk == VALUE_SET && !isAutoIncrement) || (pk == VALUE_NOT_SET && !isAutoIncrement) || (pk == VALUE_NOT_SET && isAutoIncrement))
						columnInfo.put(PRAGMA_INFO_TABLE_COLUMN_ISAUTOINCREMENT, false);

					columnInfo.put(PRAGMA_INFO_TABLE_COLUMN_TYPE, columnType);
					columnInfo.put(PRAGMA_INFO_TABLE_COLUMN_NOTNULL, notNull);
					columnInfo.put(PRAGMA_INFO_TABLE_COLUMN_DEFAULT_VALUE, defaultVal);

					tableInfo.put(columnName, columnInfo);
				}
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			cursor.close();
		}

		return tableInfo;
	}


	protected boolean checkTableExists(String tableName) {

		boolean status = false;
		Cursor cursor = null;
		try{

			String sqlStatement = "SELECT name FROM sqlite_master WHERE type = 'table' AND name = '"+tableName+"' COLLATE NOCASE";

			cursor = database.rawQuery(sqlStatement, null);

			if(cursor != null)
				status = cursor.moveToNext();

		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			cursor.close();
		}

		return status;
	}

	protected boolean isFieldExist(String tableName, String fieldName)
	{
		Cursor res = database.rawQuery("PRAGMA table_info("+tableName+")",null);

		if(res != null)
		{
			while(res.moveToNext())
			{
				int nameIdx = res.getColumnIndexOrThrow(PRAGMA_INFO_TABLE_COLUMN_NAME);
				String columnName = res.getString(nameIdx);
				LOG.d(TAG, "columnName: " + columnName);
				if(columnName.equals(fieldName))
					return true;
			}
		}
		return false;
	}

	protected boolean alterTableName(String old_table_name, String new_table_name) {

		try{
			if(checkTableExists(old_table_name) && !checkTableExists(new_table_name))
			{
				String statement = "ALTER TABLE "+old_table_name+" RENAME TO "+new_table_name;
				execSQL(statement);

				return true;
			}
			else
				return false;

		}catch(Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}

	protected boolean
	addTableColumn(AddTableColumn addTableColumn) {

		try{
			String tableName = addTableColumn.getTableName();

			String columnName = addTableColumn.getNewTableColumnName();
			LOG.d(TAG, "columnName: " + columnName);

			if(checkTableExists(tableName) && !isFieldExist(tableName, columnName))
			{
				Integer pk = addTableColumn.getIsPrimaryKey();
				LOG.d(TAG, "pk: " + pk);
				Boolean isAutoIncrement = addTableColumn.isAutoIncrement();
				LOG.d(TAG, "isAutoIncrement: " + isAutoIncrement);
				String columnType = addTableColumn.getColumnType();
				LOG.d(TAG, "columnType: " + columnType);
				Integer notNull = addTableColumn.getIsNotNull();
				LOG.d(TAG, "notNull: " + notNull);
				Integer defaultVal = addTableColumn.getDefaultVal();
				LOG.d(TAG, "defaultVal: " + defaultVal);

				StringBuilder builder = new StringBuilder();

				builder.append("ALTER TABLE "+tableName+" ADD COLUMN "+columnName+" "+columnType);

				if(pk == VALUE_SET)
				{
					builder.append(" PRIMARY KEY ");
					if(isAutoIncrement)
						builder.append(" AUTOINCREMENT ");
				}

				if(notNull == VALUE_SET)
					builder.append(" NOT NULL ");
				if(defaultVal == VALUE_SET)
				{
					Object defaultValue = addTableColumn.getDefaultValueToSet();
					if(columnType.equalsIgnoreCase(("TEXT")))
						builder.append(" DEFAULT "+ defaultValue +" ");
					else if(columnType.equalsIgnoreCase(("INTEGER")))
						builder.append(" DEFAULT "+ String.valueOf(defaultValue + " "));
					else if(columnType.equalsIgnoreCase(("REAL"))) {
						if(defaultValue instanceof Double)
							builder.append(" DEFAULT " + String.valueOf(defaultValue + " "));
						else if(defaultValue instanceof Long)
							builder.append(" DEFAULT " + String.valueOf(defaultValue + " "));
					}
					else if(columnType.equalsIgnoreCase(("BLOB")))
						builder.append(" DEFAULT "+ String.valueOf(defaultValue + " "));
				}

				String statement = builder.toString();
				LOG.d(TAG, "statement: " + statement);

				execSQL(statement);

				return true;
			}
			else
				return false;

		}catch(Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}

	protected boolean dropTable(String tableName) {

		try{
			String statement = "DROP TABLE IF EXISTS "+tableName;
			execSQL(statement);

			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	//old_col_name,new_col_name      
	protected boolean renameTableColumnNames(String tableName, String oldColumnName, String newColumnName) {

		try{
			if(checkTableExists(tableName))
			{
				if(oldColumnName != null && newColumnName != null)
				{
					LinkedHashMap<String, String> updatedTableColumnNamesToChange = new LinkedHashMap<String, String>();

					if(oldColumnName != null && isFieldExist(tableName, oldColumnName) && !isFieldExist(tableName, newColumnName))
					{
						updatedTableColumnNamesToChange.put(oldColumnName, newColumnName);
					}

					if(updatedTableColumnNamesToChange != null && updatedTableColumnNamesToChange.size() > 0)
					{
						//TODO:Create temporary table with new column name.
						if(createTableForRenameColumns(tableName, updatedTableColumnNamesToChange))
						{
							//TODO:Copy all the data from old table to temporary table.
							if(copyTableDataForRenameColumn(tableName))
							{
								//TODO:Drop old table.
								if(dropTable(tableName))
								{
									//TODO:Rename temporary table to actual table name.
                                    return alterTableName(tableName + TEMPORARY_TABLE_CONSTANT, tableName);
								}
								else
								{
									dropTable(tableName+TEMPORARY_TABLE_CONSTANT);
									return false;
								}
							}
							else
							{
								dropTable(tableName+TEMPORARY_TABLE_CONSTANT);
								return false;
							}
						}
						else
							return false;
					}
					else
						return false;
				}
				else
					return false;
			}
			else
				return false;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	protected boolean deleteTableColumns(String tableName, String columnNameToDelete) {

		try{
			if(checkTableExists(tableName))
			{
				LinkedHashMap<String, String> updatedTableColumnNamesToChange = new LinkedHashMap<String, String>();

				updatedTableColumnNamesToChange.put(columnNameToDelete, null);

				if(updatedTableColumnNamesToChange != null && updatedTableColumnNamesToChange.size() > 0)
				{
					//TODO:Create temporary table with new column name.
					if(createTableForDeleteColumns(tableName, updatedTableColumnNamesToChange))
					{
						//TODO:Copy all the data from old table to temporary table.
						if(copyTableDataForDeleteColumn(tableName))
						{
							//TODO:Drop old table.
							if(dropTable(tableName))
							{
								//TODO:Rename temporary table to actual table name.
                                return alterTableName(tableName + TEMPORARY_TABLE_CONSTANT, tableName);
							}
							else
							{
								dropTable(tableName+TEMPORARY_TABLE_CONSTANT);
								return false;
							}
						}
						else
						{
							dropTable(tableName+TEMPORARY_TABLE_CONSTANT);
							return false;
						}
					}
					return false;
				}
				else
					return false;
			}
			else
				return false;

		}catch(Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}

	protected boolean createTableForRenameColumns(String tableName, LinkedHashMap<String, String> columnNames) {

		return createTable(tableName, columnNames, RENAME_TABLE_COLUMN);
	}

	protected boolean createTableForDeleteColumns(String tableName, LinkedHashMap<String, String> columnNames) {

		return createTable(tableName, columnNames, DELETE_TABLE_COLUMN);
	}

	protected boolean copyTableDataForRenameColumn(String tableName) {

		String insertIntoStatment = null;
		String selectFromStatement = null;

		String tempTableName = tableName+TEMPORARY_TABLE_CONSTANT;
		LOG.d(TAG, "tempTableName: " + tempTableName);

		LinkedHashMap<String, LinkedHashMap<String, Object>> tempTableDetails = getTableDetails(tempTableName);

		if(tempTableDetails != null && tempTableDetails.size() > 0)
		{
			StringBuilder builder = null;
			Set<String> set = null;
			Iterator<String> iterator = null;

			builder = new StringBuilder();
			builder.append("INSERT INTO "+tempTableName+" (");

			set = tempTableDetails.keySet();
			iterator = set.iterator();
			boolean hasNext = iterator.hasNext();

			while(hasNext)
			{
				String columnName = iterator.next();
				LOG.d(TAG, "temp table columnName: " + columnName);
				builder.append(columnName);

				hasNext = iterator.hasNext();
				if(hasNext)
				{
					builder.append(", ");
				}
				else
					builder.append(") ");
			}

			insertIntoStatment = builder.toString();
			builder = null;
		}

		LinkedHashMap<String, LinkedHashMap<String, Object>> actualTableDetails = getTableDetails(tableName);

		if(actualTableDetails != null && actualTableDetails.size() > 0)
		{
			StringBuilder builder = null;
			Set<String> set = null;
			Iterator<String> iterator = null;

			builder = new StringBuilder();
			builder.append("SELECT ");

			set = actualTableDetails.keySet();
			iterator = set.iterator();
			boolean hasNext = iterator.hasNext();

			while(hasNext)
			{
				String columnName = iterator.next();
				LOG.d(TAG, "temp table columnName: " + columnName);
				builder.append(columnName);

				hasNext = iterator.hasNext();
				if(hasNext)
				{
					builder.append(", ");
				}
				else
					builder.append(" FROM "+tableName);
			}

			selectFromStatement = builder.toString();
			builder = null;
		}

		return copyData(insertIntoStatment, selectFromStatement);
	}

	protected boolean copyTableDataForDeleteColumn(String tableName) {

		String insertIntoStatment = null;
		String selectFromStatement = null;

		String tempTableName = tableName+TEMPORARY_TABLE_CONSTANT;
		LOG.d(TAG, "tempTableName: " + tempTableName);

		LinkedHashMap<String, LinkedHashMap<String, Object>> tempTableDetails = getTableDetails(tempTableName);

		if(tempTableDetails != null && tempTableDetails.size() > 0)
		{
			StringBuilder builder = null;
			Set<String> set = null;
			Iterator<String> iterator = null;

			builder = new StringBuilder();
			builder.append("INSERT INTO "+tempTableName+" (");

			set = tempTableDetails.keySet();
			iterator = set.iterator();
			boolean hasNext = iterator.hasNext();

			while(hasNext)
			{
				String columnName = iterator.next();
				LOG.d(TAG, "temp table columnName: " + columnName);
				builder.append(columnName);

				hasNext = iterator.hasNext();
				if(hasNext)
				{
					builder.append(", ");
				}
				else
					builder.append(") ");
			}

			insertIntoStatment = builder.toString();
			builder = null;
		}

		LinkedHashMap<String, LinkedHashMap<String, Object>> actualTableDetails = getTableDetails(tableName);

		if(actualTableDetails != null && actualTableDetails.size() > 0)
		{
			StringBuilder builder = null;
			Set<String> set = null;
			Iterator<String> iterator = null;

			builder = new StringBuilder();
			builder.append("SELECT ");

			set = actualTableDetails.keySet();
			iterator = set.iterator();
			boolean hasNext = iterator.hasNext();

			while(hasNext)
			{
				String columnName = iterator.next();
				LOG.d(TAG, "temp table columnName: " + columnName);

				if(tempTableDetails.containsKey(columnName))
				{
					builder.append(columnName);

					hasNext = iterator.hasNext();
					if(hasNext)
					{
						builder.append(", ");
					}
					else
						builder.append(" FROM "+tableName);
				}
				else
				{
					hasNext = iterator.hasNext();
					if(!hasNext)
					{
						String lastColumnToDelete = builder.toString();
						lastColumnToDelete = lastColumnToDelete.substring(0, lastColumnToDelete.length()-2);
						builder = null;
						builder = new StringBuilder();

						builder.append(lastColumnToDelete);
						builder.append(" FROM "+tableName);
					}
				}
			}

			selectFromStatement = builder.toString();
			builder = null;
		}

		return copyData(insertIntoStatment, selectFromStatement);
	}

	protected boolean createTable(String tableName, LinkedHashMap<String, String> columnNames, int crudOperation) {

		boolean isTableCreated = false;

		try{

			if(tableName != null)
			{
				LinkedHashMap<String, LinkedHashMap<String, Object>> tableDetails = getTableDetails(tableName);

				if(tableDetails != null && tableDetails.size() > 0)
				{
					if(columnNames != null && columnNames.size() > 0)
					{
						StringBuilder builder = null;
						String createStatment = null;
						String tempTableName = tableName+TEMPORARY_TABLE_CONSTANT;
						Set<String> set = null;
						Iterator<String> iterator = null;

						switch (crudOperation) {
							case RENAME_TABLE_COLUMN:
							{
								builder = new StringBuilder();
								builder.append("CREATE TABLE IF NOT EXISTS "+tempTableName+" (");

								set = tableDetails.keySet();
								iterator = set.iterator();

								boolean hasNext = iterator.hasNext();

								while(hasNext)
								{
									String existingColumnName = iterator.next();
									LOG.d(TAG, "existingColumnName: " + existingColumnName);

									LinkedHashMap<String, Object> columnDefination = tableDetails.get(existingColumnName);

									Integer pk = (Integer)columnDefination.get(PRAGMA_INFO_TABLE_COLUMN_PRIMARY_KEY);
									LOG.d(TAG, "pk: " + pk);
									Boolean isAutoIncrement = (Boolean)columnDefination.get(PRAGMA_INFO_TABLE_COLUMN_ISAUTOINCREMENT);
									LOG.d(TAG, "isAutoIncrement: " + isAutoIncrement);
									String columnType = (String)columnDefination.get(PRAGMA_INFO_TABLE_COLUMN_TYPE);
									LOG.d(TAG, "columnType: " + columnType);
									Integer notNull = (Integer)columnDefination.get(PRAGMA_INFO_TABLE_COLUMN_NOTNULL);
									LOG.d(TAG, "notNull: " + notNull);
									Integer defaultVal = (Integer)columnDefination.get(PRAGMA_INFO_TABLE_COLUMN_DEFAULT_VALUE);
									LOG.d(TAG, "defaultVal: " + defaultVal);

									if(columnNames.containsKey(existingColumnName))
									{
										String newColumnName = columnNames.get(existingColumnName);

										builder.append(newColumnName+" "+columnType+" ");

										if(pk == VALUE_SET)
										{
											builder.append(" PRIMARY KEY ");
											if(isAutoIncrement)
												builder.append(" AUTOINCREMENT ");
										}
										if(notNull == VALUE_SET)
											builder.append(" NOT NULL ");
										if(defaultVal == VALUE_SET)
											builder.append(" DEFAULT "+ String.valueOf(defaultVal)+" ");

										hasNext = iterator.hasNext();

										if(hasNext)
										{
											builder.append(",");
										}
										else
											builder.append(" );");
									}
									else
									{
										builder.append(existingColumnName+" "+columnType+" ");

										if(pk == VALUE_SET)
										{
											builder.append(" PRIMARY KEY ");
											if(isAutoIncrement)
												builder.append(" AUTOINCREMENT ");
										}
										if(notNull == VALUE_SET)
											builder.append(" NOT NULL ");
										if(defaultVal == VALUE_SET)
											builder.append(" DEFAULT "+ String.valueOf(defaultVal)+" ");

										hasNext = iterator.hasNext();

										if(hasNext)
										{
											builder.append(",");
										}
										else
											builder.append(" );");
									}
								}

								createStatment = builder.toString();

								LOG.d(TAG, "createStatment: " + createStatment);

								break;
							}
							case DELETE_TABLE_COLUMN:
							{
								builder = new StringBuilder();
								builder.append("CREATE TABLE IF NOT EXISTS "+tempTableName+" (");

								set = tableDetails.keySet();
								iterator = set.iterator();

								boolean hasNext = iterator.hasNext();

								while(hasNext)
								{
									String existingColumnName = iterator.next();
									LOG.d(TAG, "existingColumnName: " + existingColumnName);

									LinkedHashMap<String, Object> columnDefination = tableDetails.get(existingColumnName);

									Integer pk = (Integer)columnDefination.get(PRAGMA_INFO_TABLE_COLUMN_PRIMARY_KEY);
									LOG.d(TAG, "pk: " + pk);
									Boolean isAutoIncrement = (Boolean)columnDefination.get(PRAGMA_INFO_TABLE_COLUMN_ISAUTOINCREMENT);
									LOG.d(TAG, "isAutoIncrement: " + isAutoIncrement);
									String columnType = (String)columnDefination.get(PRAGMA_INFO_TABLE_COLUMN_TYPE);
									LOG.d(TAG, "columnType: " + columnType);
									Integer notNull = (Integer)columnDefination.get(PRAGMA_INFO_TABLE_COLUMN_NOTNULL);
									LOG.d(TAG, "notNull: " + notNull);
									Integer defaultVal = (Integer)columnDefination.get(PRAGMA_INFO_TABLE_COLUMN_DEFAULT_VALUE);
									LOG.d(TAG, "defaultVal: " + defaultVal);

									if(!columnNames.containsKey(existingColumnName))
									{
//									String newColumnName = columnNames.get(existingColumnName);

										builder.append(existingColumnName+" "+columnType+" ");

										if(pk == VALUE_SET)
										{
											builder.append(" PRIMARY KEY ");
											if(isAutoIncrement)
												builder.append(" AUTOINCREMENT ");
										}
										if(notNull == VALUE_SET)
											builder.append(" NOT NULL ");
										if(defaultVal == VALUE_SET)
											builder.append(" DEFAULT "+ String.valueOf(defaultVal)+" ");

										hasNext = iterator.hasNext();

										if(hasNext)
										{
											builder.append(",");
										}
										else
											builder.append(" );");
									}
									else
									{
										hasNext = iterator.hasNext();

										if(!hasNext)
										{
											String lastColumnToDelete = builder.toString();
											lastColumnToDelete = lastColumnToDelete.substring(0, lastColumnToDelete.length()-1);
											builder = null;
											builder = new StringBuilder();
											builder.append(lastColumnToDelete);
											builder.append(" );");
										}
									}
								}
								createStatment = builder.toString();

								LOG.d(TAG, "createStatment: " + createStatment);

								break;
							}

							default:
								break;
						}

						if(createStatment != null)
						{
							execSQL(createStatment);
							isTableCreated = true;
						}
					}
				}
			}

		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return isTableCreated;
	}

	protected boolean copyData(String insertIntoStatment, String selectFromStatement) {

		boolean isTableDataCopied = false;

		try{
			if(insertIntoStatment != null && selectFromStatement != null)
			{
				String copyStatmenet = insertIntoStatment+" "+selectFromStatement;
				execSQL(copyStatmenet);
				isTableDataCopied = true;
			}

		}catch(Exception e)
		{
			e.printStackTrace();

			return false;
		}
		return isTableDataCopied;
	}

	protected void execSQL(String statement) throws NhanceException {

		try{
			database.execSQL(statement);
		}catch(SQLException e)
		{
			e.printStackTrace();
			throw new NhanceException(NhanceException.DATABASE_UPGRADE_STATEMENT_ERROR);
		}
	}
}

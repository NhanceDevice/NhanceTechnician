/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.nhance.technician.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.nhance.technician.database.AppDatabaseUpgrade;
import com.nhance.technician.datasets.AssignedServiceRequestTable;
import com.nhance.technician.datasets.DataSyncTrackTable;
import com.nhance.technician.datasets.GeneratedInvoiceTable;
import com.nhance.technician.datasets.UserProfileTable;
import com.nhance.technician.exception.NhanceException;

import java.util.ArrayList;
import java.util.List;



/*
*
* Id: ApplicationDataBase
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/

public class ApplicationDataBase {

    public static String TAG = ApplicationDataBase.class.getName();
    private SQLiteDatabase mConnectort;
    private String dbPath;

    public ApplicationDataBase(String dbPath) throws NhanceException {

        this.dbPath = dbPath;
        createDataBase();
    }

    private void createDataBase() throws NhanceException {

        try {

            mConnectort = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

            if (!mConnectort.isOpen()) {
                openDB();
            }

//            mConnectort.setVersion(4);//Added for testing..

            int dbVersion = mConnectort.getVersion();
            Log.v("VersionDB", "" + dbVersion);
            Log.v("VersionApp", "" + NhanceApplication.getVersionCode());
            if (dbVersion == 0) {
                mConnectort.setVersion(NhanceApplication.getVersionCode());
                dbVersion = NhanceApplication.getVersionCode();
            }

            if (NhanceApplication.getVersionCode() == dbVersion) {
                //deleteAllTables();
                createAllTables();
            /*} else if (NhanceApplication.getVersionCode() == 20) {
                deleteAllTables();
                createAllTables();*/
            } else if (NhanceApplication.getVersionCode() > dbVersion) {
                AppDatabaseUpgrade databaseUpgrade = new AppDatabaseUpgrade(mConnectort);
                while (dbVersion < NhanceApplication.getVersionCode()) {
                    dbVersion++;

                    //TODO:Please remove(mConnectort.setVersion(dbVersion); , dbVersion = mConnectort.getVersion();) the above and uncomment below code when it is released for production.
                    if (databaseUpgrade.onUpgrade(dbVersion)) {
                        mConnectort.setVersion(dbVersion);
                        dbVersion = mConnectort.getVersion();
                    } else
                        throw new NhanceException(NhanceException.DATABASE_UPGRADE_ERROR);

                   /* mConnectort.setVersion(dbVersion);
                    dbVersion = mConnectort.getVersion();*/

                }
                createAllTables();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_CREATION_ERROR, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException("Error While Creating DataBase : " + e.getMessage());
        }
    }

    private void createAllTables() throws NhanceException {
        try {
            UserProfileTable.createTables(getDatabase());
            GeneratedInvoiceTable.createTables(getDatabase());
            DataSyncTrackTable.createTables(getDatabase());
            AssignedServiceRequestTable.createTables(getDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_UPGRADE_STATEMENT_ERROR);
        }
    }

    public void deleteAllTables() throws NhanceException {
        try {
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_UPGRADE_STATEMENT_ERROR);
        }
    }


    public void resetAllTables() throws NhanceException {
        try {
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_UPGRADE_STATEMENT_ERROR);
        }
    }
    public void deleteAllTabless() throws NhanceException {
        try {
            SQLiteDatabase db = getDatabase();
            // query to obtain the names of all tables in your database
            Cursor c = getDatabase().rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            List<String> tables = new ArrayList<>();
            // iterate over the result set, adding every table name to a list
            while (c.moveToNext()) {
                tables.add(c.getString(0));
            }
            // call DROP TABLE on every table name
            for (String table : tables) {
                String dropQuery = "DROP TABLE IF EXISTS " + table;
                db.execSQL(dropQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_UPGRADE_STATEMENT_ERROR);
        }
    }

    public SQLiteDatabase getDatabase() {
        return mConnectort;
    }

    public void openDB() throws NhanceException {
        try {
            mConnectort = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_UPDATE_ERR);
        }
    }

    public boolean isTableCreatedAndRowsExists(String tableName) {

        Cursor Cursor = null;
        int rowsCount = 0;
        try {
            beginTransaction();
            String sqlQuery = "SELECT COUNT(*) as count FROM " + tableName;

            Cursor = getQueryResult(sqlQuery, null);
            if (null != Cursor && Cursor.moveToFirst()) {
                rowsCount = Cursor.getInt(Cursor.getColumnIndex("count"));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (null != Cursor)
                Cursor.close();
            Cursor = null;
            endTransaction();
        }
        return rowsCount > 0;
    }

    public long insert(String table, String nullColumnHack, ContentValues values) throws NhanceException {
        try {
            beginTransaction();
            long newlyInsertedRowId = mConnectort.insertOrThrow(table, nullColumnHack, values);
            commitToDB();
            return newlyInsertedRowId;
        } catch (Exception e) {
            Log.d(TAG, ">>>>>> ERROR : while Inserting in to data base");
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_UPDATE_ERR);
        } finally {
            values.clear();
            values = null;
            endTransaction();
        }
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) throws NhanceException {
        int numOfUpdatedRows = 0;
        try {
            beginTransaction();
            numOfUpdatedRows = mConnectort.update(table, values, whereClause, whereArgs);
            commitToDB();
            return numOfUpdatedRows;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_UPDATE_ERR);
        } finally {
            values.clear();
            values = null;
            endTransaction();
        }
    }

    public int delete(String table, String whereClause, String[] whereClauseArray) throws NhanceException {
        try {

            beginTransaction();

            int noOfRowsAffected = mConnectort.delete(table, whereClause, whereClauseArray);

            commitToDB();

            return noOfRowsAffected;
        } catch (SQLiteException e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_DELETE_ERR, "Delete record failed");
        } finally {
            endTransaction();
        }
    }

    public Cursor getQueryResult(String sqlQuery, String[] selectionArgs) throws NhanceException {

        Cursor cursor = null;
        try {
            cursor = mConnectort.rawQuery(sqlQuery, selectionArgs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        }
        return cursor;
    }

    public void execSQL(String sqlQuery, String[] selectionArgs) throws NhanceException {

        try {
            mConnectort.execSQL(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        }
    }

    public Cursor getSqlQueryResult(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) throws NhanceException {
        try {
            return mConnectort.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
            throw new NhanceException(null, ex.getMessage());
        }
    }

    public Cursor getSingleElement(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) throws NhanceException {

        Cursor cursor = null;
        try {
            cursor = mConnectort.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        }

        return cursor;
    }

    public void beginTransaction() {

        if (null != mConnectort) mConnectort.beginTransactionNonExclusive();
    }

    public void endTransaction() {

        if (null != mConnectort && mConnectort.inTransaction()) mConnectort.endTransaction();
    }

    public void commitToDB() {
        if (null != mConnectort) {
            //System.out.println("Commiting DB");
            mConnectort.setTransactionSuccessful();
        }
    }

    public void closeDB() {
        if (mConnectort != null && mConnectort.isOpen()) {
            mConnectort.close();
            mConnectort = null;
        }
    }
}

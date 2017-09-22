/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */

package com.nhance.technician.datasets;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.SellerLoginDTO;


/*
*
* Id: UserProfileTable
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/
public class UserProfileTable {


    private static final String TAG = UserProfileTable.class.getName();

    public static String COLUMN_ID = "id";
    public static String USER_PROFILE_TABLE = "UserProfile";
    public static String COLUMN_USER_CODE = "user_code";
    public static String COLUMN_SELLER_CODE = "seller_code";
    public static String COLUMN_SELLER_NAME = "seller_name";
    public static String COLUMN_SELLER_EMAIL_ID = "email_id";
    public static String COLUMN_ISD_CODE = "isd_code";
    public static String COLUMN_MOBILE_NO = "mobile_no";
    public static String COLUMN_USER_NAME = "user_name";

    private static final String UserProfileTable_Create_Table = "CREATE TABLE IF NOT EXISTS " + USER_PROFILE_TABLE
            + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_CODE + " TEXT ,"
            + COLUMN_SELLER_CODE + " TEXT ,"
            + COLUMN_SELLER_NAME + " TEXT ,"
            + COLUMN_SELLER_EMAIL_ID + " TEXT ,"
            + COLUMN_ISD_CODE + " INTEGER ,"
            + COLUMN_MOBILE_NO + " TEXT ,"
            + COLUMN_USER_NAME + " TEXT "
            + " );";

    /**
     * <b>public static void createTables(SQLiteDatabase db)</b><p>
     * <p>
     * Called when UserProfile table is required to creat
     *
     * @param db instance of application's SQLiteDatabase
     */
    public static void createTables(SQLiteDatabase db) {
        db.execSQL(UserProfileTable_Create_Table);
        Log.d(TAG, " created");
    }

    /**
     * <b>public static void dropTables(SQLiteDatabase db)</b><p>
     * <p>
     * Called when UserProfile table is required to drop
     *
     * @param db instance of application's SQLiteDatabase
     */
    public static void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_PROFILE_TABLE);
    }

    /**
     * <b>public static void reset(SQLiteDatabase db)</b><p>
     * <p>
     * Called when UserProfile table is required to reset
     *
     * @param db instance of application's SQLiteDatabase
     */
    public static void reset(SQLiteDatabase db) {
        dropTables(db);
        createTables(db);
    }


    public static boolean isDataExists(String columnName, String columnValue) throws NhanceException {

        Cursor cursor = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + USER_PROFILE_TABLE + " where " + columnName + " = '" + columnValue + "'";
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            return null != cursor && cursor.moveToFirst();
        } finally {
            if (null != cursor) cursor.close();
            cursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }

    public static String getStringColumnValue(String columnName) throws NhanceException {
        Cursor cursor = null;
        String columnValue = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + USER_PROFILE_TABLE;
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            if (null != cursor && cursor.moveToFirst()) {
                columnValue = cursor.getString((cursor.getColumnIndex(columnName)));
            }
            return columnValue;
        } finally {
            if (null != cursor) cursor.close();
            cursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }

    public static String getStringColumnValue(String columnNameValueToFetch, String conditionalColumnName, String conditionalColumnNameValue) throws NhanceException {
        Cursor cursor = null;
        String columnValue = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + USER_PROFILE_TABLE + " where " + conditionalColumnName + " = '" + conditionalColumnNameValue + "'";
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            if (null != cursor && cursor.moveToFirst()) {
                columnValue = cursor.getString((cursor.getColumnIndex(columnNameValueToFetch)));
            }
            return columnValue;
        } finally {
            if (null != cursor) cursor.close();
            cursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }

    public void storeUserDetails(SellerLoginDTO sellerLoginDTO) throws NhanceException {

        try {
            ContentValues userValues = new ContentValues();

            Application application = Application.getInstance();
            if (sellerLoginDTO.getUserCode() != null && sellerLoginDTO.getUserCode().length() > 0) {
                userValues.put(UserProfileTable.COLUMN_USER_CODE, sellerLoginDTO.getUserCode());
                application.setUserCode(sellerLoginDTO.getUserCode());
            }

            if (sellerLoginDTO.getSellerCode() != null && sellerLoginDTO.getSellerCode().length() > 0) {
                userValues.put(UserProfileTable.COLUMN_SELLER_CODE, sellerLoginDTO.getSellerCode());
                application.setSellerCode(sellerLoginDTO.getSellerCode());
            }

            if (sellerLoginDTO.getSellerName() != null && sellerLoginDTO.getSellerName().length() > 0)
                userValues.put(UserProfileTable.COLUMN_SELLER_NAME, sellerLoginDTO.getSellerName());

            if (sellerLoginDTO.getEmailId() != null && sellerLoginDTO.getEmailId().length() > 0)
                userValues.put(UserProfileTable.COLUMN_SELLER_EMAIL_ID, sellerLoginDTO.getEmailId());

            if (sellerLoginDTO.getIsdCode() != null && sellerLoginDTO.getIsdCode().length() > 0) {
                userValues.put(UserProfileTable.COLUMN_ISD_CODE, sellerLoginDTO.getIsdCode());
                application.setIsdCode(Integer.parseInt(sellerLoginDTO.getIsdCode()));
            }

            if (sellerLoginDTO.getMobileNumber() != null && sellerLoginDTO.getMobileNumber().length() > 0) {
                userValues.put(UserProfileTable.COLUMN_MOBILE_NO, sellerLoginDTO.getMobileNumber());
                application.setMobileNumber(sellerLoginDTO.getMobileNumber());
            }
            if (sellerLoginDTO.getUserName() != null && sellerLoginDTO.getUserName().length() > 0) {
                userValues.put(UserProfileTable.COLUMN_USER_NAME, sellerLoginDTO.getUserName());
            }

            if (sellerLoginDTO.getMobileNumber() != null && isDataExists(UserProfileTable.COLUMN_MOBILE_NO, sellerLoginDTO.getMobileNumber())) {
                NhanceApplication.applicationDataBase.update(UserProfileTable.USER_PROFILE_TABLE, userValues, UserProfileTable.COLUMN_MOBILE_NO + " = ?",
                        new String[]{sellerLoginDTO.getMobileNumber()});
            } else {
                NhanceApplication.applicationDataBase.insert(UserProfileTable.USER_PROFILE_TABLE, null, userValues);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_SAVE_ERR);
        }
    }

    public boolean isUserProfileDetailsExists(String mobileNumber) throws NhanceException {

        return valueDetailsExistsInTable(UserProfileTable.USER_PROFILE_TABLE, UserProfileTable.COLUMN_MOBILE_NO, mobileNumber);
    }

    /**
     * Method to check whether given column value already exists in table
     *
     * @param tableName
     * @param columnName
     * @param columnValue
     * @return boolean
     * @throws NhanceException
     */
    public boolean valueDetailsExistsInTable(String tableName, String columnName, String columnValue) throws NhanceException {

        Cursor checkerCursor = null;
        try{

            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from "+tableName + " where " +
                    columnName + " = '" + columnValue+"'" ;

            checkerCursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery,null);
            if(null != checkerCursor && checkerCursor.moveToFirst())
                return true;
            else
                return false;

        }catch(Exception e)
        {
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        }
        finally{
            if(null != checkerCursor)
                checkerCursor.close();
            checkerCursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }
}

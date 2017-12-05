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


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.exception.NhanceException;

/*
*
* Id: DataSyncTrackTable
*
* Date Author Changes
* 17-Oct-17 Javeed Khan H Created
*/
public class DataSyncTrackTable {
    /**
     * TAG to be used in Logger
     */
    private static final String TAG = DataSyncTrackTable.class.getName();

    /**
     * FeedbackType - Name of the table
     */
    public static String DATA_SYNC_TRACK_TABLE = "DataSyncTrack";

    /**
     * id - Primary key of the Table
     */
    public static String COLUMN_ID = "id";
    /**
     *
     */
    public static String COLUMN_SYNC_TYPE = "sync_type";
    /**
     *
     */
    public static String COLUMN_SYNC_STATUS = "sync_status";

    public static String COLUMN_LAST_SYNC_DATE = "last_sync_date";

    /**
     * fk_user_mobile_no - foreign key reference of mobile no in user profile table
     */
    public static String COLUMN_FK_USER_ID_OR_GUID = "fk_user_id";


    /**
     * Create table query
     */
    private static final String Data_Sync_Track_Create_Table = "CREATE TABLE IF NOT EXISTS " + DATA_SYNC_TRACK_TABLE
            + " ("
            + COLUMN_ID                 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_FK_USER_ID_OR_GUID + " TEXT, "
            + COLUMN_SYNC_TYPE + " INTEGER ,"
            + COLUMN_LAST_SYNC_DATE + " REAL ,"
            + COLUMN_SYNC_STATUS + " INTEGER ,"
            + "FOREIGN KEY (" + COLUMN_FK_USER_ID_OR_GUID + ") REFERENCES " + UserProfileTable.USER_PROFILE_TABLE + "(" + UserProfileTable.COLUMN_USER_ID_OR_GUID + ") "
            + " );";
    /**
     * <b>public static void createTables(SQLiteDatabase db)</b><p>
     *
     * Called when FeedbackType table is required to creat
     *
     * @param db instance of application's SQLiteDatabase
     */
    public static void createTables(SQLiteDatabase db) {
        db.execSQL(Data_Sync_Track_Create_Table);
        Log.d(TAG, " created");
    }

    /**
     * <b>public static void dropTables(SQLiteDatabase db)</b><p>
     *
     * Called when FeedbackType table is required to drop
     *
     * @param db instance of application's SQLiteDatabase
     */
    public static void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + DATA_SYNC_TRACK_TABLE);
    }
    /**
     * <b>public static void reset(SQLiteDatabase db)</b><p>
     *
     * Called when FeedbackType table is required to reset
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
            String sqlQuery = "select * from " + DATA_SYNC_TRACK_TABLE + " where " + columnName + " = '" + columnValue + "'";
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            return null != cursor && cursor.moveToFirst();
        } finally {
            if (null != cursor) cursor.close();
            cursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }
}

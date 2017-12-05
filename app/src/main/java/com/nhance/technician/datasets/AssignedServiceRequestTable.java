package com.nhance.technician.datasets;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.exception.NhanceException;

/**
 * Created by Javeed on 05/12/17.
 */

public class AssignedServiceRequestTable {

    private static final String TAG = AssignedServiceRequestTable.class.getName();
    public static String COLUMN_ID = "id";
    public static String ASSIGNED_SR_TABLE = "AssignedServiceRequest";

    public static String COLUMN_GUID = "guid";
    public static String COLUMN_SER_REQ_NO = "service_req_no";
    public static String COLUMN_SER_REQ_SUBJECT = "service_req_subject";
    public static String COLUMN_SER_REQ_CREATED_DATE = "service_req_created_date";
    public static String COLUMN_USER_NAME = "user_name";
    public static String COLUMN_ISD_CODE = "isd_code";
    public static String COLUMN_MOBILE_NUMBER = "mobile_number";
    public static String COLUMN_ADDRESS = "address";
    public static String COLUMN_SR_JSON = "sr_json";
    public static String COLUMN_PAYMENT_STATUS = "payment_status";
    public static String COLUMN_USER_GUID = "user_code";

    private static final String Assigned_ServiceRequest_Create_Table = "CREATE TABLE IF NOT EXISTS " + ASSIGNED_SR_TABLE
            + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_GUID + " TEXT ,"
            + COLUMN_GUID + " TEXT ,"
            + COLUMN_SER_REQ_NO + " TEXT ,"
            + COLUMN_SER_REQ_SUBJECT + " TEXT ,"
            + COLUMN_SER_REQ_CREATED_DATE + " REAL ,"
            + COLUMN_ISD_CODE + " TEXT ,"
            + COLUMN_USER_NAME + " TEXT ,"
            + COLUMN_MOBILE_NUMBER + " TEXT ,"
            + COLUMN_ADDRESS + " TEXT ,"
            + COLUMN_SR_JSON + " TEXT, "
            + COLUMN_PAYMENT_STATUS + " INTEGER"
            + " );";


    public static void createTables(SQLiteDatabase db) {
        db.execSQL(Assigned_ServiceRequest_Create_Table);
        Log.d(TAG, " created");
    }


    public static void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + ASSIGNED_SR_TABLE);
    }


    public static void reset(SQLiteDatabase db) {
        dropTables(db);
        createTables(db);
    }


    public static boolean isDataExists(String columnName, String columnValue) throws NhanceException {

        Cursor cursor = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + ASSIGNED_SR_TABLE + " where " + columnName + " = '" + columnValue + "'";
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            return null != cursor && cursor.moveToFirst();
        } finally {
            if (null != cursor) cursor.close();
            cursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }
}

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

/*
*
* Id: ApplicationConstants
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/
public interface ApplicationConstants {


    /**
     * The FIL pre_ path.
     */
    String FILE_PRE_PATH = "/mnt/sdcard";

    /**
     * The FIL e_ path.
     */
    String FOLDER_NAME = "Nhance";

    /**
     * The FIL e_ path.
     */
    String TEMP_FOLDER_NAME = "TempNhance";

    /**
     * The Log_ file.
     */
    String LOG_FILE = "Nhance.txt";

    /**
     * The Log_ file.
     */
    String DB_FILE = "NhanceDB.db";

    String APPLICATION_FOLDER_PATH = FILE_PRE_PATH + "/" + FOLDER_NAME;

    String TEMP_APPLICATION_FOLDER_PATH = FILE_PRE_PATH + "/" + TEMP_FOLDER_NAME;

    // SMS provider identification
    // It should match with your SMS gateway origin
    String SMS_ORIGIN = "NHANCE";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    String OTP_DELIMITER = ":";

    int OTP_LENGTH = 6;

    String APPLICATION_TYPE = "EOT";

    String SOURCE_TYPE = "Android";

    String SMS_ORIGIN_KEY = "SMS_ORIGIN";

    String SMS_MESSAGE_KEY = "MESSAGE";

    String OTP_KEY = "OTP";


    String CRASH_LOG_FILES_FOLDER = "CrashLogs";
    String CRASH_LOG_FILES_NAME = "CrashLog_";
    String CRASH_LOG_FILES_EXTENSION = ".stacktrace";
    int BYTE_DATA = 1;
    int LESS_THAN_KB_DATA = 2;
    int KB_DATA = 3;
    int LESS_THAN_MB_DATA = 4;
    int MB_DATA = 5;
    String CRASH_LOG_FILE_NAME = "crash_log_file_name";

    String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
    int USER_NEW = 1;
    int USER_LOGGED_IN = 2;
    int USER_LOGGED_OUT = 3;
    String LOGGED_IN_USER = "LOGGED_IN_USER";


    String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    String REGISTRATION_COMPLETE = "registrationComplete";


    public static final String ACTION_CODE_SERVICE_REQUEST_INVOICE_PAID = "18000012";

    String STARTED_FROM_NOTIFICATION_TAG = "StartedFromNotificationClick";
    String INCREMENTAL_NUMBER = "incremental_number";
    String ACTION_UPDATE_VIEW = "com.nhance.technician.updateview";


    enum AppMode {
        PLAYSTORE_RELEASE {
            @Override
            public String toString() {
                return "playstore_release";
            }
        },
        DEV_RELEASE {
            @Override
            public String toString() {
                return "dev_release";
            }
        },
        DEMO_RELEASE {
            @Override
            public String toString() {
                return "demo_release";
            }
        }
    }

    AppMode AppExt = AppMode.DEV_RELEASE;


    long FILE_SIZE_IN_KB = 1024;
    long FILE_SIZE_IN_MB = FILE_SIZE_IN_KB * 1024;


    String OPEN_URL = "OPEN_URL";
    String OPEN_URL_SCREEN_TITLE = "OPEN_URL_SCREEN_TITLE";

    String TEXT_TO_SHARE = "TEXT_TO_SHARE";
    String SCREEN_TITLE = "SCREEN_TITLE";

    int DK_TYPE_technician = 1;
    int DK_TYPE_B2B = 2;

    int STATUS_FAIL = 8;
    int STATUS_SUCCESS = 13;

    int ATTRIBUTE_NOT_CAPTURED = 0;
    int ATTRIBUTE_CAPTURED = 1;

    int PAYMENT_STATUS_PENDING_BILL_GENERATED = 14;
    int PAYMENT_STATUS_PAID = 24;

    int MODE_OF_PAYMENT_CASH = 1;
    int MODE_OF_PAYMENT_ONLINE = 2;
    int MODE_OF_PAYMENT_BOTH_CASH_ONLINE = 3;

    String CASH_PAYMENT = "Cash";
    String ONLINE_PAYMENT = "Online";

    String REQUESTING_RESET_PASSWORD = "REQUESTING_RESET_PASSWORD";

    String CHANGE_PASSWORD_AFTER_OTP = "CHANGE PASSWORD AFTER OTP";
}

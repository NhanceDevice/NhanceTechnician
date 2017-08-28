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

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Environment;

import com.nhance.technician.R;
import com.nhance.technician.crashlog.ExceptionHandler;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.BaseModel;
import com.nhance.technician.model.Location;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.receiver.NetworkConnectivityReceiver;
import com.nhance.technician.util.TypefaceUtils.TypefaceHelper;
import com.nhance.technician.util.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/*
*
* Id: ApplicationConstants
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/
public class NhanceApplication extends Application implements ApplicationConstants {

    public static final String TASK_INTENT = "com.nhance.technician.TASK_INTENT";
    public static String TAG = NhanceApplication.class.getName();

    public static InputStream is;
    private static String locale = null;
    public static long TIME_OUT = 0;
    public static NhanceApplication mContext;
    public static ApplicationDataBase applicationDataBase;
    private static Hashtable<String, BaseModel> modelToTakeAction = null;
    public static Resources resources;
    private static int versionCode;
    private static String versionName;
    public static String purchaseFileName = "";
    private int applicationType;
    private String sourceType;
    private boolean encryptionRequired = true;
    private String backendUrl = "";
    private String appID;
    public static final String MENU_ITEM = "menuItemPosition";

    public static Location tempAddedAddress = null;


    private ExceptionHandler exceptionHandler;
    private boolean isInternalStorageRequired = false;
    //public static final String packageNhanceDemo = "demo.com.nhance.technician";


    public static final String NHANCE_PREFERENCES = "nhance_preferences";
    public static final String PREF_FLOATING_WINDOW = "isFloatingWindowVisible";

    public static String lastNotificationCodeToDismiss = "";

    public enum HelpLine {
        MOBILE, EMAIL
    }

    public void setConnectivityListener(NetworkConnectivityReceiver.ConnectivityReceiverListener listener) {
        NetworkConnectivityReceiver.connectivityReceiverListener = listener;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);
        LOG.d(TAG, "Oncreate Called.");

        try {
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mContext = this;

        applicationFolderPath = mContext.getExternalFilesDir(null).getAbsolutePath();
        appDataBackUpFolderPath = mContext.getExternalFilesDir(null).getAbsolutePath();

        exceptionHandler = new ExceptionHandler();

        initNhanceDb();
        createFileSystemToUse();

        // Font Helper initialization
        TypefaceHelper.initialize(this);
        try {
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            LOG.d(TAG, "Starting App");
            resources = getResources();
            modelToTakeAction = new Hashtable<String, BaseModel>();

            backendUrl = resources.getString(R.string.backend_url);
            int logLevel = LOG.VERBOSE;
            LOG.setLogLevel(logLevel);

            applicationType = resources.getInteger(R.integer.application_type);
            sourceType = resources.getString(R.string.source_type);

            createAppDataBackUpFolder();

            com.nhance.technician.model.Application applicationObj = com.nhance.technician.model.Application.getInstance();
            applicationObj.setVersionNumber(versionName);
            applicationObj.setApplicationType(applicationType);
            applicationObj.setSourceType(sourceType);
            applicationObj.setOs(resources.getString(R.string.os));

            JSONAdaptor.register();
            MobileDeviceInfo.MobileDeviceInfoInit(this);
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
        } catch (Exception exception) {
            LOG.d(TAG, "================== Exception ======================= :" + exception.getLocalizedMessage());
            exception.printStackTrace();
        }
    }


    private String applicationFolderPath;
    private String applicationUserFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String applicationUserServiceRequestFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath();


    public String getAppID() {
        return appID;
    }

    public String getStoragePath(String dbFile) throws IOException {
        /*if (dbFile.equals(DB_FILE))
            return getInternalStoragePath(dbFile);
        else*/
        return isInternalStorageRequired ? getInternalStoragePath(dbFile) : getExternalStoragePath(dbFile);
    }

    private String getInternalStoragePath(String filename) throws IOException {

        if (filename.equals(DB_FILE)) {
            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            File directory = contextWrapper.getDir(FOLDER_NAME, Context.MODE_PRIVATE);
            LOG.d(TAG, "Internal Folder Path : " + directory.getAbsolutePath());
            File contentFile = new File(directory, filename);
            LOG.d(TAG, "Internal File Path : " + contentFile.getAbsolutePath());
            return contentFile.getAbsolutePath();
        } else {
            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            File directory = contextWrapper.getDir(FOLDER_NAME, Context.MODE_PRIVATE);
            applicationFolderPath = directory.getAbsolutePath();
            LOG.d(TAG, "Internal Folder Path : " + applicationFolderPath);
            File contentFile = new File(directory, filename);
            LOG.d(TAG, "Internal File Path : " + contentFile.getAbsolutePath());
            return contentFile.getAbsolutePath();
        }
    }

    private String getExternalStoragePath(String filename) throws IOException {
        String fileAbsolutePath = null;
        try {
            File file = null;
            file = new File(mContext.getExternalFilesDir(null).getAbsolutePath(), FOLDER_NAME);
            if (file == null || (file != null && !file.exists())) {

                Util.createPathIfNecessary(applicationFolderPath, FOLDER_NAME);
                applicationFolderPath = applicationFolderPath + "/" + FOLDER_NAME;
                LOG.d(TAG, "External Folder Path : " + applicationFolderPath);

                fileAbsolutePath = createFile(filename);
                LOG.d(TAG, "Files got created");
            } else {
                applicationFolderPath = file.getAbsolutePath();
                fileAbsolutePath = createFile(filename);
            }

            file = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.d(TAG, "External File Path : " + fileAbsolutePath);
        return fileAbsolutePath;
    }

    public String getAppCrashLogFolderPath(String folderName) throws IOException {
        String fileAbsolutePath = null;
        try {
            File contentFile = new File(applicationFolderPath, folderName);
            if (contentFile != null && !contentFile.exists())
                contentFile.mkdirs();

            fileAbsolutePath = contentFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.d(TAG, "Application Crash Log Folder Path : " + fileAbsolutePath);
        return fileAbsolutePath;
    }

    public String getUserDKStoragePath(String folderName) {
        String fileAbsolutePath = null;
        try {
            Util.createPathIfNecessary(applicationUserFolderPath, folderName);
            File contentFile = new File(applicationUserFolderPath, folderName);
            fileAbsolutePath = contentFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.d(TAG, "Application User DK Folder Path : " + fileAbsolutePath);
        return fileAbsolutePath;
    }

    private String createFile(String filename) throws IOException {

        File contentFile = new File(applicationFolderPath + "/" + filename);
        if (!contentFile.exists()) {
            contentFile.createNewFile();
        }
        return contentFile.getAbsolutePath();
    }

    public void initNhanceDb() {
        String dbPath = null;
        try {
            dbPath = getStoragePath(DB_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (applicationDataBase == null) {
                applicationDataBase = new ApplicationDataBase(dbPath);
            }
        } catch (NhanceException e) {
            e.printStackTrace();
        }
        LOG.d(TAG, "DB creation finished");

        exceptionHandler.register(this);
    }

    private String appDataBackUpFolderPath;
    private String storageForSerReqFiles;



    public String createAppDataBackUpFolder() {
        File file = null;
        try {
            file = new File(mContext.getExternalFilesDir(null).getAbsolutePath(), "." + TEMP_FOLDER_NAME);
            if (file == null || (file != null && !file.exists())) {
                file = null;
                Util.createPathIfNecessary(appDataBackUpFolderPath, TEMP_FOLDER_NAME);
                appDataBackUpFolderPath = appDataBackUpFolderPath + "/" + TEMP_FOLDER_NAME;
                LOG.d(TAG, "appDataBackUpFolderPath : " + appDataBackUpFolderPath);
                file = new File(appDataBackUpFolderPath);
                if (file.exists())
                    appDataBackUpFolderPath = hideFile(file);
            } else
                appDataBackUpFolderPath = file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.d(TAG, "appDataBackUpFolderPath : " + appDataBackUpFolderPath);
        return appDataBackUpFolderPath;
    }

    public String hideFile(File file) {
        File dstFile = new File(file.getParent(), "." + file.getName());
        String folderPath = dstFile.getAbsolutePath();
        LOG.d(TAG, "hideFile()  folderPath: " + folderPath);
        file.renameTo(dstFile);

        return folderPath;
    }


    public static NhanceApplication getApplication() {
        return mContext;
    }

    public String getApplicationFolderPath() {
        return applicationFolderPath;
    }

    public static Context getContext() {
        return mContext;
    }

    public boolean isEncryptionRequired() {
        return encryptionRequired;
    }

    public void setEncryptionRequired(boolean encryptionRequired) {
        this.encryptionRequired = encryptionRequired;
    }

    public String getBackendUrl() {
        return backendUrl;
    }

    public void setBackendUrl(String backendUrl) {
        this.backendUrl = backendUrl;
    }


    public boolean createFileSystemToUse() {
        try {
//            getStoragePath(DB_FILE);
            getStoragePath(LOG_FILE);
//            createDBFile();
//            createLogFile();
            LOG.d(TAG, "Files got created");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private void createLogFile() throws IOException {

        //Todo create Log file from path
        /*File fileTxt = new File(APPLICATION_FOLDER_PATH + "/" + LOG_FILE);
        if (!fileTxt.exists()) {
            fileTxt.createNewFile();
        }*/

    }

    private void createDBFile() throws IOException {

        //Todo create DB file from path
/*
        File dbFile = new File(APPLICATION_FOLDER_PATH + "/" + DB_FILE_NAME);
        if (!dbFile.exists()) {
            dbFile.createNewFile();
        }*/

    }

    public static void resetModelToTakeAction() {
        if (modelToTakeAction != null && modelToTakeAction.containsKey("ModelToTakeAction"))
            modelToTakeAction.remove("ModelToTakeAction");
    }

    public static void setModelToTakeAction(BaseModel base) {
        if (modelToTakeAction == null) {
            modelToTakeAction = new Hashtable<String, BaseModel>();
        }
        modelToTakeAction.put("ModelToTakeAction", base);
    }

    public static BaseModel getModelToTakeAction() throws NhanceException {
        Object obj = modelToTakeAction.get("ModelToTakeAction");
        if (null != obj) {
            return (BaseModel) obj;
        } else {
            throw new NhanceException(NhanceException.BUSINESS_CONTROLLER_NOT_INITIALIZED, "Model container not initialized");
        }
    }


    public static int getVersionCode() {
        return versionCode;
    }

    public static String getVersionName() {
        return versionName;
    }

    public ExceptionHandler getExceptionHandler() {

        return exceptionHandler;
    }


    public String getApplicationUserStoragePath(String fileName) {
        String fileAbsolutePath = null;
        try {
            File contentFile = new File(applicationUserFolderPath, fileName);
            fileAbsolutePath = contentFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.d(TAG, "Application User File Path : " + fileAbsolutePath);
        return fileAbsolutePath;
    }

    public String getApplicationUserServReqStoragePath(String fileName) {
        String fileAbsolutePath = null;
        try {
            File contentFile = new File(applicationUserServiceRequestFolderPath, fileName);
            fileAbsolutePath = contentFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.d(TAG, "Application User File Path : " + fileAbsolutePath);
        return fileAbsolutePath;
    }

    public void setAppUserServiceRequestFolderPath(String serviceRequestNo) {

        try {
            if (isInternalStorageRequired) {
                ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
                File directory = contextWrapper.getDir(applicationUserFolderPath + "/ServiceRequest/" + serviceRequestNo, Context.MODE_PRIVATE);
                applicationUserServiceRequestFolderPath = directory.getAbsolutePath();
            } else {

                Util.createPathIfNecessary(applicationUserFolderPath, "ServiceRequest/" + serviceRequestNo);
                String applicationFolderPath = applicationUserFolderPath + "/ServiceRequest/" + serviceRequestNo;
                File file = new File(applicationFolderPath);
                applicationUserServiceRequestFolderPath = file.getAbsolutePath();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (applicationUserServiceRequestFolderPath != null)
            LOG.d(TAG, "App User Service Request Folder Path : " + applicationUserServiceRequestFolderPath);
        else
            LOG.d(TAG, "App User Service Request Folder Path not created.");
    }

    public void setAppUserFolderPath(String phoneNumber) {

        try {
            if (isInternalStorageRequired) {
                ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
                File directory = contextWrapper.getDir(applicationFolderPath + "/" + phoneNumber, Context.MODE_PRIVATE);
                applicationUserFolderPath = directory.getAbsolutePath();
            } else {

                Util.createPathIfNecessary(applicationFolderPath, phoneNumber);
                String applicationFolderPath = this.applicationFolderPath + "/" + phoneNumber;
                File file = new File(applicationFolderPath);
                applicationUserFolderPath = file.getAbsolutePath();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (applicationUserFolderPath != null)
            LOG.d(TAG, "App User Folder Path : " + applicationUserFolderPath);
        else
            LOG.d(TAG, "App User Folder Path not created.");
    }

}

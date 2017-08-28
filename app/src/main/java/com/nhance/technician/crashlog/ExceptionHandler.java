/*
Copyright (c) 2009 nullwire aps

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

Contributors: 
Mads Kristiansen, mads.kristiansen@nullwire.com
Glen Humphrey
Evan Charlton
Peter Hewitt
*/

package com.nhance.technician.crashlog;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.model.Application;
import com.nhance.technician.networking.util.RestConstants;
import com.nhance.technician.util.Util;

import java.io.IOException;
import java.util.Date;

/*
* Date Author Changes
* 04-Jan-16 Javeed Created
*/
public class ExceptionHandler {

    //	https://code.google.com/p/android-remote-stacktrace/

    public static String TAG = ExceptionHandler.class.getName();

    private Context context;

    public static String APP_VERSION = "unknown";
    public static String APP_PACKAGE = "unknown";
    public static String PHONE_MODEL = "unknown";
    public static String PHONE_BRAND = "unknown";
    public static String ANDROID_VERSION = "unknown";


    private String folderPath = null;

    public ExceptionHandler() {
    }

    /**
     * Register handler for unhandled exceptions.
     *
     * @param context
     */
    public void register(final Context context) {
        this.context = context;
        Log.i(TAG, "Registering default exceptions handler");

        try {

            folderPath = NhanceApplication.getApplication().getAppCrashLogFolderPath(ApplicationConstants.CRASH_LOG_FILES_FOLDER);

            // Get information about the Package
            PackageManager pm = context.getPackageManager();

            PackageInfo pi;
            // Version
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            APP_VERSION = pi.versionName;
            // Package name
            APP_PACKAGE = pi.packageName;
            // Files dir for storing the stack traces
//			G.FILES_PATH = context.getFilesDir().getAbsolutePath();
            // Device model
            PHONE_MODEL = Build.MODEL;
            //Phone model
            PHONE_BRAND = Build.BRAND;
            // Android version
            ANDROID_VERSION = Build.VERSION.RELEASE;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String dateAndTime = Util.getDayTimeStringFromDate(new Date(), true);

        final String crashLogIdentifierData =   "Brand : "+PHONE_BRAND+"\n"+
                                                "Model : "+PHONE_MODEL+"\n"+
                                                "Android OS : "+ANDROID_VERSION+"\n"+
                                                "App Package : "+APP_PACKAGE+"\n"+
                                                "App Version : "+APP_VERSION+"\n"+
                                                "Date : "+dateAndTime+"\n";
        Log.d(TAG, "Crash_Log_Identifier_Data : " + crashLogIdentifierData);
        Log.d(TAG, "FILES_PATH: " + folderPath);

        new Thread() {
            @Override
            public void run() {
                // First of all transmit any stack traces that may be lying around
//				submitStackTraces();
                UncaughtExceptionHandler currentHandler = Thread.getDefaultUncaughtExceptionHandler();
                if (currentHandler != null) {
                    Log.d(TAG, "current handler class=" + currentHandler.getClass().getName());
                }
                // don't register again if already registered
                if (!(currentHandler instanceof DefaultExceptionHandler)) {
                    // Register default exceptions handler
                    Thread.setDefaultUncaughtExceptionHandler(
                            new DefaultExceptionHandler(crashLogIdentifierData,currentHandler, context, folderPath));
                }
            }
        }.start();
    }

    /*public void startServiceToUpload() throws Exception {

        Application application = Application.getInstance();

        Intent intent = new Intent(context, CrashLogUploadService.class);

        intent.putExtra(ApplicationConstants.MOBILE_NO_EXTRA, application.getMobileNumber());
        intent.putExtra(ApplicationConstants.EMAIL_ID_EXTRA, application.getEmailId());
        intent.putExtra(ApplicationConstants.APPLICATION_TYPE_EXTRA, application.getApplicationType());
        intent.putExtra(ApplicationConstants.SOURCE_TYPE_EXTRA, application.getSourceType());
        intent.putExtra(ApplicationConstants.SERVICE_URL_EXTRA, RestConstants.UPLOAD_CRASH_LOG_URL);
        intent.putExtra(ApplicationConstants.CRASH_LOG_FOLDER_PATH_EXTRA, folderPath);
        context.startService(intent);
    }*/

    public String getFolderPath() {

        return folderPath;
    }
}

/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.nhance.technician.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//import com.applozic.mobicomkit.api.account.user.UserClientService;


/**
 * Created by afsar on 07-Oct-15.
 */
public class BaseFragmentActivity extends AppCompatActivity 
         {


    public static final String TAG = BaseFragmentActivity.class.getName();
    public static Activity instance;

    private ProgressDialog progressDialog = null;
    private Handler handler;
    private String progressMessage = null;

    private static final String ARG_SECTION_NUMBER = "section_number";


    /**
     * dateEditText is used to set and retrieve date.
     */
    private EditText dateEditText;

    /**
     * dateTextInputLayout is used to disable error.
     */
    private TextInputLayout dateTextInputLayout;

    /**
     * mCurrentyear, mSelectedYear is used to set the current and selected year.
     */
    private int mCurrentyear, mSelectedYear;
    /**
     * mCurrentmonth, mSelectedMonth is used to set the current and selected month.
     */
    private int mCurrentmonth, mSelectedMonth;
    /**
     * mCurrentday, mSelectedDay is used to set the current and selected day.
     */
    private int mCurrentday, mSelectedDay;

    /**
     * DATE_PICKER_ID is used to define constant for popup dialog.
     */
    public static final int DATE_PICKER_ID = 1111;


    protected ConnectivityManager mSystemService;

    /**
     * This is used to allow date picker future date or not.
     */
    private boolean allowFutureDate;
    private TextView toolbarTitle;
    private TextView toolbarSubtitle;
    private AppCompatImageView mToolbarLogo;

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        instance = this;

        mSystemService = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        handler = new Handler();
    }




    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }




    

    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";

    public void openWebPage(String url) {
        if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = HTTP + url;
        }
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    
    public static Activity getForegroundActivity() {
        Activity activity = null;
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);

            if (activities == null)
                return null;

            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return activity;
    }

    

}

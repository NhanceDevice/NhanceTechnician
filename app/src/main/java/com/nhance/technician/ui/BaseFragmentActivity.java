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
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.service.fcm.RegistrationIntentService;
import com.nhance.technician.ui.fragments.NavigationDrawerFragment;
import com.nhance.technician.util.AccessPreferences;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//import com.applozic.mobicomkit.api.account.user.UserClientService;


/**
 * Created by afsar on 07-Oct-15.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, DialogInterface.OnClickListener{


    public static final String TAG = BaseFragmentActivity.class.getName();
    public static Activity instance;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in .
     */
    private CharSequence mTitle;

    private Toolbar toolbar;

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
    private String mScreenTitle;

    public void setContentView(int layoutResID, String screenTitle){
        setContentView(layoutResID);
        mScreenTitle = screenTitle;
    }

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        instance = this;

        mSystemService = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        handler = new Handler();
    }

    public ConnectivityManager getmSystemService() {
        return mSystemService;
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

    public void logoutUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getResources().getString(R.string.logout));
        builder.setMessage(getResources().getString(R.string.sure_to_logout));
        builder.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.SENT_TOKEN_TO_SERVER, false);
                new RegistrationIntentService().unregisterGCMToken();
                AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_LOGGED_OUT);
                Application.getInstance().setLoggedInUserName("");
                Application.getInstance().setMobileNumber(null);
                AccessPreferences.clear(BaseFragmentActivity.this);
                Intent loginIntent = new Intent(BaseFragmentActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();

            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        if(position == 0)
        {
            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);
        }
        else if(position == 1)
        {
            logoutUser();
        }
    }

    @Override
    public void onNavigationDrawerProfileClicked() {


        LOG.d(TAG,"Profile Clicked");
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

    public void hideSoftKeyPad() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            if (!(e.getMessage() == null)) {

            }
            e.printStackTrace();
        }
    }

    public void setUpNavigationDrawer(String screenTitle, View continer) {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = screenTitle;

        mNavigationDrawerFragment.setContainer(continer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar, screenTitle);
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    public ActionBar getCustomActionBar(){

        return mNavigationDrawerFragment.getActionBar();
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle(){

        return mNavigationDrawerFragment.getmDrawerToggle();
    }

    public void setUpNavigationDrawer(String screenTitle, String screenSubTitle, View continer, boolean isSideBarRequired) {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = screenTitle;

        mNavigationDrawerFragment.setContainer(continer);

        if(isSideBarRequired){
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout), toolbar, screenTitle, screenSubTitle);
        }else{
            ActionBar actionBar = getCustomActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

            TextView screen_title = (TextView) toolbar.findViewById(R.id.screen_title);
            screen_title.setVisibility(View.VISIBLE);
            screen_title.setText(Html.fromHtml("<font color='#ffffff'>" + mTitle + " </font>"));
//            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>" + mTitle + " </font>"));

            if(screenSubTitle != null && screenSubTitle.trim().length() > 0)
                actionBar.setSubtitle(Html.fromHtml("<font color='#ffffff'> <small>" + screenSubTitle + " <small> </font>"));
        }
    }

    public void setUpNavigationDrawer(String screenTitle, String screenSubTitle, View continer) {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = screenTitle;

        mNavigationDrawerFragment.setContainer(continer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar, screenTitle, screenSubTitle);
    }

    private String mPositiveButtonText,mNegativeButtonText;
    public byte mAlertCode = -1;
    public final byte defaultCode = 0;

    public void showAlert(final String messageToDisplay, final String positiveButtonText, final String negativeButtonText, final byte alertCode){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPositiveButtonText = positiveButtonText;
                mNegativeButtonText = negativeButtonText;
                mAlertCode = alertCode;

                if(mScreenTitle == null || (mScreenTitle != null && mScreenTitle.length() == 0))
                    mScreenTitle = getResources().getString(R.string.alert);

                AlertDialog.Builder dialog = new AlertDialog.Builder(BaseFragmentActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle(mScreenTitle);
                dialog.setMessage(messageToDisplay);

                if(mPositiveButtonText != null && mPositiveButtonText.length() > 0){
                    dialog.setPositiveButton(mPositiveButtonText, BaseFragmentActivity.this);
                }
                if(mPositiveButtonText != null && mPositiveButtonText.length() > 0){
                    dialog.setNegativeButton(mNegativeButtonText, BaseFragmentActivity.this);
                }

                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });
    }

    public void showAlert(final String messageToDisplay){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPositiveButtonText = null;
                mNegativeButtonText = null;
                mAlertCode = 0;

                if(mScreenTitle == null || (mScreenTitle != null && mScreenTitle.length() == 0))
                    mScreenTitle = getResources().getString(R.string.alert);

                AlertDialog.Builder dialog = new AlertDialog.Builder(BaseFragmentActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle(mScreenTitle);
                dialog.setMessage(messageToDisplay);
                dialog.setPositiveButton(getResources().getString(R.string.ok), BaseFragmentActivity.this);

                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });
    }

    private ProgressDialog progressDialog = null ;
    private Handler handler;
    private String progressMessage = null ;

    public void showProgressDialog(final Context context, final String message)
    {
        handler.post( new Runnable() {

            public void run() {

                progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setTitle(message);
                progressDialog.setMessage("Please wait ...");
                progressDialog.setCancelable(false);
                if(!isFinishing())
                    progressDialog.show();
            }});

    }
    public void dismissProgressDialog()
    {
        handler.post(new Runnable() {
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    public static String getCountryDialCode() {
        String countryID;
        String countryDialCode = "";

        TelephonyManager telephonyMngr = (TelephonyManager) NhanceApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);

        countryID = telephonyMngr.getSimCountryIso().toUpperCase();
        String[] arrCountryCode = NhanceApplication.getContext().getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < arrCountryCode.length; i++) {
            String[] arrDial = arrCountryCode[i].split(",");
            if (arrDial[1].trim().equals(countryID.trim())) {
                countryDialCode = arrDial[0];
                break;
            }
        }

        if (countryDialCode == null || countryDialCode.length() < 1) {
            countryDialCode = "91";
        }

        return countryDialCode;
    }

    public static String getCountryID() {
        String countryID;

        TelephonyManager telephonyMngr = (TelephonyManager) NhanceApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);

        countryID = telephonyMngr.getSimCountryIso().toUpperCase();
        String[] arrCountryCode = NhanceApplication.getContext().getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < arrCountryCode.length; i++) {
            String[] arrDial = arrCountryCode[i].split(",");
            if (arrDial[1].trim().equals(countryID.trim())) {
                countryID = arrDial[1].trim().toLowerCase();
                break;
            }
        }
        if (countryID == null || countryID.length() < 1) {
            countryID = "in";
        }
        return countryID;
    }
}

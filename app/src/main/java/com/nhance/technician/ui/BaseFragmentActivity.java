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
import android.app.ActivityManager;
import android.app.KeyguardManager;
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
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.newapis.ErrorMessage;
import com.nhance.technician.model.newapis.ResponseStatus;
import com.nhance.technician.model.newapis.UserAuthModel;
import com.nhance.technician.model.newapis.UserAuthResponse;
import com.nhance.technician.networking.RestCall;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.networking.util.RestConstants;
import com.nhance.technician.ui.action.CommonAction;
import com.nhance.technician.ui.fragments.NavigationDrawerFragment;
import com.nhance.technician.util.AccessPreferences;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

                logoutWithDeRegisteringDevice(BaseFragmentActivity.this);
                
                /*AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.SENT_TOKEN_TO_SERVER, false);
                new RegistrationIntentService().unregisterGCMToken();
                AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_LOGGED_OUT);
                Application.getInstance().setLoggedInUserName("");
                Application.getInstance().setMobileNumber(null);
                AccessPreferences.clear(BaseFragmentActivity.this);
                Intent loginIntent = new Intent(BaseFragmentActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();*/

            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void logoutWithDeRegisteringDevice(final Context context) {
        try {

            Callback call = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    dismissProgressDialog();
                    showAlert("Unable to process your request. Please try again.");
                }

                @Override
                public void onResponse(Call call, Response response) {
                    dismissProgressDialog();
                    if (response.isSuccessful()) {
                        int responseCode = response.code();
                        if (responseCode == 200) {
                            try {
                                int status = 0;

                                String resStr = response.body().string();
                                LOG.d("UserLoginTask", resStr);

                                UserAuthResponse userAuthResponse = JSONAdaptor.fromJSON(resStr, UserAuthResponse.class);
                                ResponseStatus responseStatus = userAuthResponse.getStatus();
                                if (responseStatus != null && responseStatus.getStatusCode() != null) {
                                    status = responseStatus.getStatusCode();
                                }
                                if (status > 0) {
                                    List<ErrorMessage> errorMessages = responseStatus.getErrorMessages();
                                    if (errorMessages != null && errorMessages.size() > 0) {
                                        showAlert(errorMessages.get(0).getMessageDescription());
                                    }
                                }else{
                                    UserAuthModel userAuthModel = userAuthResponse.getMessage();
                                    if(userAuthModel == null)
                                        userAuthModel = (UserAuthModel)NhanceApplication.getModelToTakeActions();

                                    NhanceApplication.setModelToTakeAction(userAuthModel);

                                    proceedToLogout(BaseFragmentActivity.this, false);
                                }
                            } catch (IOException ioe) {
                                showAlert("Server Unreachable. Please try after some time.");
                            } catch (NhanceException e) {
                                showAlert("Server Unreachable. Please try after some time.");
                            }
                        } else if (responseCode == 404 || responseCode == 503) {
                            LOG.d(TAG, "Server Unreachable. Please try after some time");
                            showAlert("Server Unreachable. Please try after some time.");
                        } else if (responseCode == 500) {
                            LOG.d(TAG, "Internal server error.");
                            showAlert("Error while communicating with server, please contact administrator.");
                        } else {
                            showAlert("Error while communicating with server, please contact administrator.");
                        }

                    } else {
                        showAlert("Error while communicating with server, please contact administrator.");
                    }
                }

            };

            UserAuthModel login = new UserAuthModel();
            login.setUserId(Application.getInstance().getUserProfileUserIdOrGuid());

            new CommonAction().addCommonRequestParameters(login);

            login = (UserAuthModel)NhanceApplication.getModelToTakeActions();

            NhanceApplication.setModelToTakeAction(login);

            LOG.d("Request===> ", login.toString());

            String logoutUrl = NhanceApplication.getApplication().getBackendUrl() + RestConstants.LOGOUT_URL+"/"+login.getUserId();

            RestCall.get(logoutUrl, call);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Unable to process your request. Please try again.");
        } catch (NhanceException e) {
            e.printStackTrace();
            showAlert("Unable to process your request. Please try again.");
        }
    }

    public void proceedToLogout(final Context context, boolean dueToLoginInDifferentDevice) {
        AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.SENT_TOKEN_TO_SERVER, false);
        AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.LOGGED_IN_USER, "");
        AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_LOGGED_OUT);
        Application.getInstance().setLoggedInUserName("");
        Application.getInstance().setLoggedInUserProfilePicPath("");
        Application.getInstance().setUserProfileUserIdOrGuid(null);
        Application.getInstance().setEmailId(null);
        Application.getInstance().setMobileNumber(null);
        AccessPreferences.clear(context);
        if (isAppOpened()) {
            if (!dueToLoginInDifferentDevice) {
                showLoginScreen(context);
            } else {
                final Activity currentActivity = getForegroundActivity();
                showLoginScreen(context);
                DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                            {
                                dialog.dismiss();
                                break;
                            }
                            case DialogInterface.BUTTON_NEGATIVE:
                            {
                                dialog.dismiss();
                                break;
                            }
                        }
                    }
                };
                if (currentActivity != null) {
                    showAlert(context.getResources().getString(R.string.user_logout_message), context.getResources().getString(R.string.ok), null, clickListener);
                }
            }
        }
    }

    private void showLoginScreen(Context context){
        AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_LOGGED_OUT);

        Application application = Application.getInstance();
        application.clearObject();

        Intent intent = new Intent(BaseFragmentActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }

    public boolean isAppOpened() {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND || appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
            return true;
        }

        try {
            final Activity currenctActivity = getForegroundActivity();
            if (currenctActivity != null) {
                KeyguardManager km = (KeyguardManager) currenctActivity.getSystemService(Context.KEYGUARD_SERVICE);
                return km.inKeyguardRestrictedInputMode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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


        Log.d(TAG,"Profile Clicked");
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

    public void showAlert(final String messageToDisplay, final String positiveButtonText, final String negativeButtonText, final DialogInterface.OnClickListener listener){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPositiveButtonText = positiveButtonText;
                mNegativeButtonText = negativeButtonText;

                if(mScreenTitle == null || (mScreenTitle != null && mScreenTitle.length() == 0))
                    mScreenTitle = getResources().getString(R.string.alert);

                AlertDialog.Builder dialog = new AlertDialog.Builder(BaseFragmentActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle(mScreenTitle);
                dialog.setMessage(messageToDisplay);

                if(mPositiveButtonText != null && mPositiveButtonText.length() > 0){
                    dialog.setPositiveButton(mPositiveButtonText, listener);
                }
                if(mPositiveButtonText != null && mPositiveButtonText.length() > 0){
                    dialog.setNegativeButton(mNegativeButtonText, listener);
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
}

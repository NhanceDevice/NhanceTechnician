package com.nhance.technician.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.datasets.GeneratedInvoiceTable;
import com.nhance.technician.datasets.UserProfileTable;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.newapis.ErrorMessage;
import com.nhance.technician.model.newapis.ResponseStatus;
import com.nhance.technician.model.newapis.ServiceRequestInvoiceModel;
import com.nhance.technician.model.newapis.ServiceRequestInvoiceModelFindResponse;
import com.nhance.technician.model.newapis.UserAuthModel;
import com.nhance.technician.model.newapis.UserAuthResponse;
import com.nhance.technician.networking.RestCall;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.networking.util.RestConstants;
import com.nhance.technician.service.fcm.RegistrationIntentService;
import com.nhance.technician.ui.action.CommonAction;
import com.nhance.technician.ui.custom.adapter.CountriesAdap;
import com.nhance.technician.ui.custom.dialogplus.DialogPlus;
import com.nhance.technician.ui.custom.dialogplus.OnBackPressListener;
import com.nhance.technician.ui.custom.dialogplus.ViewHolder;
import com.nhance.technician.ui.util.EditTextUtils;
import com.nhance.technician.ui.util.KeyboardWatcher;
import com.nhance.technician.util.AccessPreferences;
import com.nhance.technician.util.TypefaceUtils.TypefaceHelper;
import com.nhance.technician.util.Util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseFragmentActivity implements KeyboardWatcher.OnKeyboardToggleListener{

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserAuthModel login;
    // UI references.
    private AutoCompleteTextView mMobileNoView;
    private AppCompatEditText mPasswordView;
    private View mLoginFormView;
    private CoordinatorLayout coordinatorLayout;
    private TextInputLayout pswdInputLay;
    private AppCompatImageButton pswdInfoBttn;

    private TextView frgtPswdTv;
    private AppCompatTextView versionTv, copyrightTv;

    private String[] permissions = null;
    private final int RequestId = 132;

    private List<String> countryCodesList;
    private LinearLayout countryCodeLay, mobileLay;
    private AppCompatTextView countryFlagTv, countryISDCodeTv;
    private AppCompatImageView countryFlagIv;
    private CountriesAdap adapter;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestId : {
                if (grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    NhanceApplication.getApplication().createFileSystemToUse();
                    NhanceApplication.getApplication().initNhanceDb();

                    doDeviceRegistration();
                } else {
                    Toast.makeText(this, "No Permissions Granted !", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    public boolean hasPermissions(Context context, String... permissions) {

        boolean permissionsSet = true;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsSet = false;
                    break;
                }
            }
        }
        return permissionsSet;
    }

    private void permissionsCheckInstallation() {
        if (permissions == null) {

            permissions = new String[]{
                    "android.permission.RECEIVE_SMS",
                    "android.permission.READ_SMS",
                    "android.permission.ACCESS_NETWORK_STATE",
                    "android.permission.INTERNET",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_EXTERNAL_STORAGE"
            };
        }

        boolean hasPermissions = hasPermissions(this, permissions);

        if (!hasPermissions) {
            ActivityCompat.requestPermissions(this, permissions, RequestId);
        }else
            doDeviceRegistration();
    }

    private void doDeviceRegistration() {
        boolean isTokenSentToServer = AccessPreferences.get(NhanceApplication.getContext(), ApplicationConstants.SENT_TOKEN_TO_SERVER, false);

        if (!isTokenSentToServer) {
            if (isPlayServicesInstalled()) {
                Intent intent = new Intent(LoginActivity.this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private boolean isPlayServicesInstalled() {
        GoogleApiAvailability getGoogleapiAvailability = GoogleApiAvailability.getInstance();
        int Code = getGoogleapiAvailability.isGooglePlayServicesAvailable(LoginActivity.this);
        if (Code != ConnectionResult.SUCCESS) {
            if (getGoogleapiAvailability.isUserResolvableError(Code)) {
                getGoogleapiAvailability.getErrorDialog(LoginActivity.this, Code, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("MainActivity", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, getResources().getString(R.string.login));

        countryCodesList = Arrays.asList(getResources().getStringArray(R.array.CountryCodes));
        countryCodeLay = (LinearLayout) findViewById(R.id.country_lay);
        mobileLay = (LinearLayout) findViewById(R.id.mobile_lay);
        countryFlagTv = (AppCompatTextView) findViewById(R.id.flag_tv);
        countryFlagIv = (AppCompatImageView) findViewById(R.id.flag_iv);
        countryISDCodeTv = (AppCompatTextView) findViewById(R.id.country_mobile_code_tv);
        //countryFlagTv.setText(localeToEmoji("IN"));
        String countryID = getCountryID();
        countryFlagIv.setImageResource(getResources().getIdentifier("drawable/"
                + countryID, null, getPackageName()));
        countryISDCodeTv.setText("+" + getCountryDialCode());

        // Set up the login form.
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mMobileNoView = (AutoCompleteTextView) findViewById(R.id.mobileno);

        copyrightTv = (AppCompatTextView)findViewById(R.id.copyright_tv);
        versionTv = (AppCompatTextView)findViewById(R.id.version_no_tv);

        copyrightTv.setText(getResources().getString(R.string.copyright));
        versionTv.setText(Util.getAppVersion(LoginActivity.this));

        frgtPswdTv = (TextView)findViewById(R.id.frgt_pswd_tv);
        frgtPswdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                    startActivity(intent);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    showAlert(e.getLocalizedMessage());
                }
            }
        });

        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, -10);
        String beforeDate = "date before 10 days : " + (now.get(Calendar.MONTH) + 1)
                + "-"
                + now.get(Calendar.DATE)
                + "-"
                + now.get(Calendar.YEAR);
        System.out.println(beforeDate);

        pswdInputLay = (TextInputLayout) findViewById(R.id.pswd_input_lay);
        mPasswordView = (AppCompatEditText) findViewById(R.id.password);
        pswdInfoBttn = (AppCompatImageButton) findViewById(R.id.pswd_info_bttn);
        /*pswdInputLay.setPressed(false);
        pswdInputLay.setClickable(false);
        mPasswordView.setPressed(false);*/
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        pswdInfoBttn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(getResources().getString(R.string.pswd_info_msg));
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

        Integer userStatus = AccessPreferences.get(NhanceApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_NEW);
        LOG.d(TAG, "User Status : userStatus : " + userStatus);

        if (userStatus != ApplicationConstants.USER_NEW && userStatus != ApplicationConstants.USER_LOGGED_OUT && userStatus == ApplicationConstants.USER_LOGGED_IN) {

            LOG.d(TAG, "User Status : ApplicationConstants.USER_LOGGED_IN");

            String loggedInUsersEmailOrMobileNo = AccessPreferences.get(NhanceApplication.getContext(), ApplicationConstants.LOGGED_IN_USER, "");
            LOG.d(TAG, "loggedInUsersEmailOrMobileNo : " + loggedInUsersEmailOrMobileNo);
            try {

                if (loggedInUsersEmailOrMobileNo != null && loggedInUsersEmailOrMobileNo.length() > 0) {
                    new CommonAction().loadBasicUserDeatilsToApplicationModel(loggedInUsersEmailOrMobileNo);
                    Application.getInstance().setMobileNumber(loggedInUsersEmailOrMobileNo);
                    NhanceApplication.getApplication().setAppUserFolderPath(Application.getInstance().getMobileNumber());
                }

                Intent mainIntent = new Intent(LoginActivity.this, TechOperationsActivity.class);
                mainIntent.putExtra("USERCODE", Application.getInstance().getUserCode());
                startActivity(mainIntent);
                finish();
            } catch (NhanceException e) {
                e.printStackTrace();
            }
        }

        keyboardWatcher = new KeyboardWatcher(LoginActivity.this);
        keyboardWatcher.setListener(this);

        permissionsCheckInstallation();

        countryCodeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyPad();
                final DialogPlus dialog = DialogPlus.newDialog(LoginActivity.this)
                        .setExpanded(false)
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new ViewHolder(R.layout.country_list_lay))
                        .setCancelable(true)
                        .setBackgroundColorResourceId(R.color.transparent)
                        .setOnBackPressListener(new OnBackPressListener() {
                            @Override
                            public void onBackPressed(DialogPlus dialog) {
                                dialog.dismiss();
                            }
                        })
                        .create(false);
                dialog.show();

                View dialogView = dialog.getHolderView();
                AppCompatEditText searchableET = (AppCompatEditText) dialogView.findViewById(R.id.searchable_et);
                AppCompatTextView noResultTv = (AppCompatTextView) dialogView.findViewById(R.id.no_result_tv);
                TypefaceHelper.getInstance().setTypeface(searchableET, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
                TypefaceHelper.getInstance().setTypeface(noResultTv, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
                RecyclerView countryLv = (RecyclerView) dialogView.findViewById(R.id.country_lv);
                adapter = new CountriesAdap(LoginActivity.this, countryCodesList, searchableET, noResultTv, dialog);
                countryLv.setAdapter(adapter);
                countryLv.setLayoutManager(new LinearLayoutManager(LoginActivity.this));

                adapter.setOnItemClickListener(new CountriesAdap.CountryClickListener() {
                    @Override
                    public void onCountryClick(View view, int position, String[] countryStrArray) {
                        AppCompatTextView flagTv = (AppCompatTextView) view.findViewById(R.id.flag_tv);
                        AppCompatTextView countryTv = (AppCompatTextView) view.findViewById(R.id.country_name_tv);
                        countryFlagTv.setText(flagTv.getText());
                        String pngName = countryStrArray[1].trim().toLowerCase();
                        countryFlagIv.setImageResource(getResources().getIdentifier("drawable/" + pngName, null, getPackageName()));

                        Log.v("CountryISOCode", "" + countryStrArray[0]);
                        String ISOCode = countryStrArray[0];
                        countryISDCodeTv.setText("");
                        countryISDCodeTv.setText("+" + ISOCode);
                        countryISDCodeTv.invalidate();
                        hideSoftKeyPad();
                        dialog.dismiss();
                    }
                });
            }
        });

        /*Application application = Application.getInstance();
        application.setUserCode("11000003");
        Intent mainIntent = new Intent(LoginActivity.this, TechOperationsActivity.class);
        mainIntent.putExtra("USERCODE","11000003");
        startActivity(mainIntent);
        finish();*/
    }

    private KeyboardWatcher keyboardWatcher;

    @Override
    public void onKeyboardShown(int keyboardSize) {
        if (copyrightTv != null && versionTv != null) {
            copyrightTv.setVisibility(View.GONE);
            versionTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onKeyboardClosed() {
        if (copyrightTv != null && versionTv != null) {
            copyrightTv.setVisibility(View.VISIBLE);
            versionTv.setVisibility(View.VISIBLE);

            Animation animationFadeIn = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade_in_center);
            copyrightTv.startAnimation(animationFadeIn);
            versionTv.startAnimation(animationFadeIn);
        }
    }

    @Override
    public void onDestroy() {
        keyboardWatcher.destroy();
        super.onDestroy();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        hideSoftKeyPad();

        if (getmSystemService().getActiveNetworkInfo() == null) {
            showAlert(getString(R.string.network_error));
            return;
        }

        // Reset errors.
        mMobileNoView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String mobileNo = mMobileNoView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(mobileNo)) {
//            mMobileNoView.setError(getString(R.string.error_field_required));
            showAlert(getString(R.string.mobile_number_blank_error));
            focusView = mMobileNoView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
            showAlert(getString(R.string.please_enter_password));
            focusView = mPasswordView;
            cancel = true;
        }else if(password.length() < 6){
            showAlert(getString(R.string.password_should_be_of_length));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            /*mAuthTask = new UserLoginTask(mobileNo, password);
            mAuthTask.execute((Void) null);*/
            try {

                Callback call = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        showProgress(false);
                        showAlert("Unable to process your request. Please try again.");
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        showProgress(false);
                        if (response.isSuccessful()) {
                            int responseCode = response.code();
                            if (responseCode == 200) {
                                try {
                                    String resStr = response.body().string();
                                    int status = 0;
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
                                        try {
                                            UserAuthModel userAuthModel = userAuthResponse.getMessage();
                                            NhanceApplication.setModelToTakeAction(userAuthModel);

                                            new UserProfileTable().storeUserDetailsAfterSignIn();
                                            new CommonAction().loadBasicUserDeatilsToApplicationModel(userAuthModel.getUserId());

                                            if(!userAuthModel.getFirstTimeLogin()) {
                                                AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.LOGGED_IN_USER, userAuthModel.getUserId());
                                                AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_LOGGED_IN);
                                            }

                                            if(userAuthModel.getFirstTimeLogin()){

                                                Intent intent = new Intent(LoginActivity.this, SetPasswordActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else {
                                                //TODO:The below service is not ready to proceed.
                                                /*Intent mainIntent = new Intent(LoginActivity.this, TechOperationsActivity.class);
                                                mainIntent.putExtra("USERCODE", userAuthModel.getUserId());
                                                startActivity(mainIntent);
                                                finish();*/
                                                LOG.d("Application.getInstance() >>>>>>> ", Application.getInstance().toString());

                                                int availableServiceReqInvoices = GeneratedInvoiceTable.getCountOfServiceRequestInvoices(userAuthModel.getUserId());
                                                if (availableServiceReqInvoices > 0) {
                                                    Intent mainIntent = new Intent(LoginActivity.this, TechOperationsActivity.class);
                                                    mainIntent.putExtra("USERCODE", userAuthModel.getUserId());
                                                    startActivity(mainIntent);
                                                    finish();
                                                } else {
                                                    syncAndFetchStoredInvoices(userAuthModel.getUserId());
                                                }
                                            }

                                        } catch (NhanceException e) {
                                            e.printStackTrace();
                                            showAlert(e.getLocalizedMessage());
                                        }
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
                Application application = Application.getInstance();

                if (!(mobileNo.contains("@") || mobileNo.contains("."))) {
                    application.setMobileNumber(mobileNo);
                    login.setLoginPrincipal(mobileNo);
                } else {
                    application.setEmailId(mobileNo);
                    login.setLoginPrincipal(mobileNo);
                }

                login.setPassword(password);
                application.setPassword(login.getPassword());

                String isdCode = EditTextUtils.getText(countryISDCodeTv);
                if (isdCode != null && !isdCode.isEmpty() && isdCode.length() > 0) {
                    String isdCodeEditTextValue = isdCode.replace("+", "");

                    if (isdCodeEditTextValue != null && !isdCodeEditTextValue.isEmpty() && isdCodeEditTextValue.length() > 0) {
                        login.setIsdCode(Integer.parseInt(isdCodeEditTextValue));
                        application.setIsdCode(Integer.parseInt(isdCodeEditTextValue));
                    }
                }

                new CommonAction().addCommonRequestParameters(login);

                login = (UserAuthModel)NhanceApplication.getModelToTakeActions();

                LOG.d("Request===> ", login.toString());
                RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.LOGIN_REQ, JSONAdaptor.toJSON(login), call);
            } catch (IOException e) {
                e.printStackTrace();
                dismissProgressDialog();
                showAlert("Unable to process your request. Please try again.");
            } catch (NhanceException e) {
                e.printStackTrace();
                dismissProgressDialog();
                showAlert("Unable to process your request. Please try again.");
            }
        }
    }

    private void syncAndFetchStoredInvoices(String user_guid) {
        showProgress(true);
        try {

            Callback call = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    showProgress(false);
                    showAlert("Unable to process your request. Please try again.");
                }

                @Override
                public void onResponse(Call call, Response response) {
                    showProgress(false);
                    if (response.isSuccessful()) {
                        int responseCode = response.code();
                        if (responseCode == 200) {
                            try {
                                String resStr = response.body().string();
                                LOG.d("UserLoginTask", resStr);

                                int status = 0;

                                ServiceRequestInvoiceModelFindResponse requestInvoiceModelFindResponse = JSONAdaptor.fromJSON(resStr, ServiceRequestInvoiceModelFindResponse.class);
                                ResponseStatus responseStatus = requestInvoiceModelFindResponse.getStatus();
                                if (responseStatus != null && responseStatus.getStatusCode() != null) {
                                    status = responseStatus.getStatusCode();
                                }
                                if (status == 0) {
                                    List<ServiceRequestInvoiceModel> serviceRequestInvoiceModels = requestInvoiceModelFindResponse.getResults();
                                    if (serviceRequestInvoiceModels != null && serviceRequestInvoiceModels.size() > 0) {
                                        try {
                                            new GeneratedInvoiceTable().storeServiceReqInvoiceDetails(serviceRequestInvoiceModels);
                                        } catch (Exception e) {
                                        }
                                    }
                                    Intent mainIntent = new Intent(LoginActivity.this, TechOperationsActivity.class);
                                    mainIntent.putExtra("USERCODE", Application.getInstance().getUserProfileUserIdOrGuid());
                                    startActivity(mainIntent);
                                    finish();
                                }
                            } catch (IOException ioe) {
                                showAlert("Unable to process your request. Please try again.");
                            } catch (NhanceException e) {
                                showAlert("Unable to process your request. Please try again.");
                            }
                        } else if (responseCode == 404 || responseCode == 503) {
                            LOG.d(TAG, "Server Unreachable. Please try after some time");
                            showAlert("Server Unreachable. Please try after some time");
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
            ServiceRequestInvoiceModel serviceRequestInvoiceModel = new ServiceRequestInvoiceModel();
            serviceRequestInvoiceModel.setUserId(user_guid);

            long lastSyncTime = new CommonAction().updateSyncInProgress(ApplicationConstants.SYNC_GENERATED_INVOICE).getLastSyncTime();
            serviceRequestInvoiceModel.setLastSyncTime(lastSyncTime);
            serviceRequestInvoiceModel.setDefaultLocale("en_US");
            LOG.d("Request===> ", serviceRequestInvoiceModel.toString());
            RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.SYNC_INVOICE_HISTORY_REQ, JSONAdaptor.toJSON(serviceRequestInvoiceModel), call);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NhanceException e) {
            e.printStackTrace();
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if(show){
            showProgressDialog(LoginActivity.this, "");
        }else{
            dismissProgressDialog();
        }
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                mProgressView.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            }
        });*/
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (mAlertCode){
            case defaultCode:
            {
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
                break;
            }
        }
    }

   /* private void showAlert(String message){
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }*/
}


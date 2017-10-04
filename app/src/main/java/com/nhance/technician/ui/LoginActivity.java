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
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.datasets.GeneratedInvoiceTable;
import com.nhance.technician.datasets.UserProfileTable;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.MasterDataDTO;
import com.nhance.technician.model.SellerLoginDTO;
import com.nhance.technician.model.ServiceRequestInvoiceDTO;
import com.nhance.technician.networking.RestCall;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.networking.util.RestConstants;
import com.nhance.technician.ui.action.CommonAction;
import com.nhance.technician.ui.util.KeyboardWatcher;
import com.nhance.technician.util.AccessPreferences;
import com.nhance.technician.util.Util;

import java.io.IOException;
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
    private SellerLoginDTO sellerLoginDTO = null;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestId : {
                if (grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    NhanceApplication.getApplication().createFileSystemToUse();
                    NhanceApplication.getApplication().initNhanceDb();
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
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, getResources().getString(R.string.login));
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
                        sellerLoginDTO = null;
                        sellerLoginDTO = new SellerLoginDTO();
                        sellerLoginDTO.setResponseStatus(1);
                        sellerLoginDTO.setMessageDescription("Unable to process your request. Please try again.");
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        showProgress(false);
                        if (response.isSuccessful()) {
                            int responseCode = response.code();
                            /*Intent mainIntent = new Intent(LoginActivity.this, TechOperationsActivity.class);
                            startActivity(mainIntent);
                            finish();*/
                            if (responseCode == 200) {
                                try {
                                    String resStr = response.body().string();
                                    LOG.d("UserLoginTask", resStr);
                                    sellerLoginDTO = JSONAdaptor.fromJSON(resStr, SellerLoginDTO.class);

                                    if (sellerLoginDTO != null) {
                                        int status = 0;
                                        if (sellerLoginDTO.getResponseStatus() != null) {
                                            status = sellerLoginDTO.getResponseStatus();
                                        }
                                        if (status > 0) {
                                            String errorMsg = sellerLoginDTO.getMessageDescription();
                                            showAlert(errorMsg);
                                        } else {
                                            //Response ( SellerLoginDTO): success/failure + userCode +  sellerCode + sellerName + emailId + isdCode + mobileNumber + userName
                                            LOG.d("", sellerLoginDTO.toString());
                                            try {

                                                new UserProfileTable().storeUserDetails(sellerLoginDTO);
                                                new CommonAction().loadBasicUserDeatilsToApplicationModel(sellerLoginDTO.getMobileNumber());

                                                if(!sellerLoginDTO.isFirstLogin()) {
                                                    AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.LOGGED_IN_USER, sellerLoginDTO.getMobileNumber());
                                                    AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_LOGGED_IN);
                                                }

                                                if(sellerLoginDTO.isFirstLogin()){

                                                    Intent intent = new Intent(LoginActivity.this, SetPasswordActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else {
                                                    LOG.d("Application.getInstance() >>>>>>> ", Application.getInstance().toString());

                                                    int availableServiceReqInvoices = GeneratedInvoiceTable.getCountOfServiceRequestInvoices(sellerLoginDTO.getUserCode());
                                                    if (availableServiceReqInvoices > 0) {
                                                        Intent mainIntent = new Intent(LoginActivity.this, TechOperationsActivity.class);
                                                        mainIntent.putExtra("USERCODE", sellerLoginDTO.getUserCode());
                                                        startActivity(mainIntent);
                                                        finish();
                                                    } else {
                                                        syncAndFetchStoredInvoices(sellerLoginDTO.getUserCode());
                                                    }
                                                }

                                            } catch (NhanceException e) {
                                                e.printStackTrace();
                                                showAlert(e.getLocalizedMessage());
                                            }
                                        }
                                    } else {
                                        showAlert(getResources().getString(R.string.unable_to_process));
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
                sellerLoginDTO = new SellerLoginDTO();
                sellerLoginDTO.setMobileNumber(mobileNo);
                Application.getInstance().setMobileNumber(mobileNo);
                sellerLoginDTO.setIsdCode("91");
                Application.getInstance().setIsdCode(Integer.parseInt("91"));
                sellerLoginDTO.setPassword(password);
                sellerLoginDTO.setDefaultLocale("en_US");
                sellerLoginDTO.setAppType(Application.getInstance().getApplicationType());
                LOG.d("Request===> ", sellerLoginDTO.toString());
                RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.LOGIN_REQ, JSONAdaptor.toJSON(sellerLoginDTO), call);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Unable to process your request. Please try again.");
            } catch (NhanceException e) {
                e.printStackTrace();
                showAlert("Unable to process your request. Please try again.");
            }
        }
    }

    private void syncAndFetchStoredInvoices(String userCode) {
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
                                MasterDataDTO masterDataDTO = JSONAdaptor.fromJSON(resStr, MasterDataDTO.class);

                                if (masterDataDTO != null) {
                                    int status = 0;
                                    if (masterDataDTO.getResponseStatus() != null) {
                                        status = masterDataDTO.getResponseStatus();
                                    }
                                    if (status > 0) {
                                        String errorMsg = masterDataDTO.getMessageDescription();
                                        showAlert(errorMsg);
                                    } else {

                                        List<ServiceRequestInvoiceDTO> serviceRequestInvoiceDTOList = masterDataDTO.getInvoiceHistory();
                                        if (serviceRequestInvoiceDTOList != null && serviceRequestInvoiceDTOList.size() > 0) {
                                            LOG.d(TAG, serviceRequestInvoiceDTOList.toString());
                                            try {
                                                new GeneratedInvoiceTable().storeServiceReqInvoiceDetails(serviceRequestInvoiceDTOList);
                                            } catch (Exception e) {

                                            }
                                        }
                                        Intent mainIntent = new Intent(LoginActivity.this, TechOperationsActivity.class);
                                        mainIntent.putExtra("USERCODE", sellerLoginDTO.getUserCode());
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                } else {
                                    showAlert( getResources().getString(R.string.unable_to_process));
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
            ServiceRequestInvoiceDTO serviceRequestInvoiceDTO = new ServiceRequestInvoiceDTO();
            serviceRequestInvoiceDTO.setUserCode(userCode);
            serviceRequestInvoiceDTO.setLastSyncTime(0L);
            serviceRequestInvoiceDTO.setDefaultLocale("en_US");
            LOG.d("Request===> ", serviceRequestInvoiceDTO.toString());
            RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.SYNC_REQ, JSONAdaptor.toJSON(serviceRequestInvoiceDTO), call);
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


package com.nhance.technician.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.nhance.technician.util.AccessPreferences;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseFragmentActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private SellerLoginDTO sellerLoginDTO = null;
    // UI references.
    private AutoCompleteTextView mMobileNoView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mMobileNoView = (AutoCompleteTextView) findViewById(R.id.mobileno);

        mPasswordView = (EditText) findViewById(R.id.password);
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

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

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
        /*Application application = Application.getInstance();
        application.setUserCode("11000003");
        Intent mainIntent = new Intent(LoginActivity.this, TechOperationsActivity.class);
        mainIntent.putExtra("USERCODE","11000003");
        startActivity(mainIntent);
        finish();*/
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mMobileNoView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String mobileNo = mMobileNoView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mobileNo)) {
            mMobileNoView.setError(getString(R.string.error_field_required));
            focusView = mMobileNoView;
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
                                            Snackbar snackbar = Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        } else {
                                            //Response ( SellerLoginDTO): success/failure + userCode +  sellerCode + sellerName + emailId + isdCode + mobileNumber + userName
                                            LOG.d("", sellerLoginDTO.toString());
                                            try {
                                                new UserProfileTable().storeUserDetails(sellerLoginDTO);
                                                new CommonAction().loadBasicUserDeatilsToApplicationModel(sellerLoginDTO.getMobileNumber());

                                                AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.LOGGED_IN_USER, sellerLoginDTO.getMobileNumber());
                                                AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_LOGGED_IN);

                                            } catch (NhanceException e) {
                                                e.printStackTrace();
                                            }
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
                                    } else {
                                        Snackbar snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.unable_to_process), Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                } catch (IOException ioe) {
                                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Unable to process your request. Please try again.", Snackbar.LENGTH_LONG);
                                    snackbar.show();

                                } catch (NhanceException e) {
                                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Unable to process your request. Please try again.", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            } else if (responseCode == 404 || responseCode == 503) {
                                LOG.d(TAG, "Server Unreachable. Please try after some time");
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Server Unreachable. Please try after some time", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else if (responseCode == 500) {
                                LOG.d(TAG, "Internal server error.");
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Error while communicating with server, please contact administrator.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Error while communicating with server, please contact administrator.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }

                        } else {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Error while communicating with server, please contact administrator.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                };
                sellerLoginDTO = new SellerLoginDTO();
                sellerLoginDTO.setMobileNumber(mobileNo);
                sellerLoginDTO.setIsdCode("91");
                sellerLoginDTO.setPassword(password);
                sellerLoginDTO.setDefaultLocale("en_US");
                LOG.d("Request===> ", sellerLoginDTO.toString());
                RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.LOGIN_REQ, JSONAdaptor.toJSON(sellerLoginDTO), call);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NhanceException e) {
                e.printStackTrace();
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
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Unable to process your request. Please try again.", Snackbar.LENGTH_LONG);
                    snackbar.show();
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
                                        Snackbar snackbar = Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_LONG);
                                        snackbar.show();
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
                                    Snackbar snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.unable_to_process), Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            } catch (IOException ioe) {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Unable to process your request. Please try again.", Snackbar.LENGTH_LONG);
                                snackbar.show();

                            } catch (NhanceException e) {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Unable to process your request. Please try again.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        } else if (responseCode == 404 || responseCode == 503) {
                            LOG.d(TAG, "Server Unreachable. Please try after some time");
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Server Unreachable. Please try after some time", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else if (responseCode == 500) {
                            LOG.d(TAG, "Internal server error.");
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Error while communicating with server, please contact administrator.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Error while communicating with server, please contact administrator.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    } else {
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Error while communicating with server, please contact administrator.", Snackbar.LENGTH_LONG);
                        snackbar.show();
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
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        runOnUiThread(new Runnable() {
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
        });

    }
}


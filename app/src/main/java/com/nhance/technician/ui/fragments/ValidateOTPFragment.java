package com.nhance.technician.ui.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.newapis.ChangePasswordModel;
import com.nhance.technician.model.newapis.ChangePasswordModelResponse;
import com.nhance.technician.model.newapis.ErrorMessage;
import com.nhance.technician.model.newapis.ResponseStatus;
import com.nhance.technician.networking.RestCall;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.networking.util.RestConstants;
import com.nhance.technician.ui.BaseFragmentActivity;
import com.nhance.technician.ui.action.CommonAction;
import com.nhance.technician.ui.action.OTPAction;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ValidateOTPFragment extends Fragment implements OTPAction.SMSReceiveCallBack {

    public static final String TAG = ValidateOTPFragment.class.getName();
    private AutoCompleteTextView otpET;
    private Button validateBT;
    private boolean isRequestedForResetPassword;
    private AppCompatTextView screen_title_header_tv;
    private CoordinatorLayout coordinatorLayout;


    public static ValidateOTPFragment newInstance() {
        return new ValidateOTPFragment();
    }



    public ValidateOTPFragment() {
        new OTPAction().registerOTPSMSReceiver(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if(bundle != null)
        {
            isRequestedForResetPassword = bundle.getBoolean(ApplicationConstants.REQUESTING_RESET_PASSWORD);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        /*getActivity().runOnUiThread(new Runnable() {
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
        if(show){
            ((BaseFragmentActivity)getActivity()).showProgressDialog(getActivity(), "");
        }else{
            ((BaseFragmentActivity)getActivity()).dismissProgressDialog();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentview = inflater.inflate(R.layout.fragment_validate_otp, container, false);

        screen_title_header_tv = (AppCompatTextView)fragmentview.findViewById(R.id.screen_title_header_tv);
        coordinatorLayout = (CoordinatorLayout)fragmentview.findViewById(R.id.coordinatorLayout);

        otpET = (AutoCompleteTextView)fragmentview.findViewById(R.id.input_otp);
        otpET.setEnabled(true);
        otpET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0 && s.length() == getResources().getInteger(R.integer.OTP_LENGTH)){
                    otpET.setError((CharSequence) null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otpET.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            validateAndProceed();

                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        validateBT = (Button)fragmentview.findViewById(R.id.validate_btn);
        validateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateAndProceed();
            }
        });
        return fragmentview;
    }

    private boolean isValidateOtpDataValid() {

        boolean retValue = true;

        if(otpET.getText().toString().trim().length() == 0 ) {
            showAlert(getResources().getString(R.string.please_enter_otp));
            retValue = false;
        }
        else if(otpET.getText().toString().trim().length() > 0 && otpET.getText().toString().trim().length() < getResources().getInteger(R.integer.OTP_LENGTH) ) {
            showAlert(getResources().getString(R.string.incomplete_otp));
            retValue = false;
        }
        else {

            retValue = true;
        }

        return retValue;
    }

    private void showAlert(String message){
        ((BaseFragmentActivity)getActivity()).showAlert(message);
    }

    private void validateAndProceed(){

        BaseFragmentActivity baseFragmentActivity = (BaseFragmentActivity)getActivity();

        if (baseFragmentActivity.getmSystemService().getActiveNetworkInfo() == null) {
            showAlert(getString(R.string.network_error));
            return;
        }

        if(!isValidateOtpDataValid())
        {
            Log.d(TAG, "User data not valid so going to return from ValidateAndDoDirectTransfer");
            return;
        }

        attemptToRequestOtp();
    }

    private void attemptToRequestOtp() {

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
                            /*Intent mainIntent = new Intent(LoginActivity.this, TechOperationsActivity.class);
                            startActivity(mainIntent);
                            finish();*/
                        if (responseCode == 200) {
                            try {
                                String resStr = response.body().string();
                                LOG.d("UserLoginTask", resStr);
                                int status = 0;

                                ChangePasswordModelResponse changePasswordModelResponse = JSONAdaptor.fromJSON(resStr, ChangePasswordModelResponse.class);
                                ResponseStatus responseStatus = changePasswordModelResponse.getStatus();
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

                                        ChangePasswordModel changePasswordModel = changePasswordModelResponse.getMessage();
                                        if(changePasswordModel == null)
                                            changePasswordModel = (ChangePasswordModel)NhanceApplication.getModelToTakeActions();
                                        NhanceApplication.setModelToTakeAction(changePasswordModel);

                                        SetPasswordFrag setPasswordFrag = new SetPasswordFrag();

                                        Bundle fragmentBundle = new Bundle();
                                        fragmentBundle.putBoolean(ApplicationConstants.CHANGE_PASSWORD_AFTER_OTP, true);
                                        setPasswordFrag.setArguments(fragmentBundle);

                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.login_container, setPasswordFrag, SetPasswordFrag.TAG)
                                                .commit();

                                    } catch (Exception e) {
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

            String otp = otpET.getText().toString().trim();

            ChangePasswordModel login = new ChangePasswordModel();
            login.setOtp(otp);

            if(Application.getInstance().getMobileNumber() != null)
                login.setLoginPrincipal(Application.getInstance().getMobileNumber());
            else if(Application.getInstance().getEmailId() != null)
                login.setLoginPrincipal(Application.getInstance().getEmailId());

            new CommonAction().addCommonRequestParameters(login);

            login = (ChangePasswordModel)NhanceApplication.getModelToTakeActions();

            NhanceApplication.setModelToTakeAction(login);

            LOG.d("Request===> ", login.toString());
            RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.VERIFY_OTP_URL, JSONAdaptor.toJSON(login), call);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Unable to process your request. Please try again.");
        } catch (NhanceException e) {
            e.printStackTrace();
            showAlert("Unable to process your request. Please try again.");
        }
    }

    @Override
    public void smsReadAndSetOTP(Bundle bundle) {
        String otp = bundle.getString(ApplicationConstants.OTP_KEY);
        otpET.setText(otp);
    }
}

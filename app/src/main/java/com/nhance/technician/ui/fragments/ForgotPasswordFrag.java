package com.nhance.technician.ui.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.nhance.technician.R;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.newapis.ChangePasswordModel;
import com.nhance.technician.model.newapis.ErrorMessage;
import com.nhance.technician.model.newapis.ResponseStatus;
import com.nhance.technician.model.newapis.UserAuthResponse;
import com.nhance.technician.networking.RestCall;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.networking.util.RestConstants;
import com.nhance.technician.ui.BaseFragmentActivity;
import com.nhance.technician.ui.action.CommonAction;
import com.nhance.technician.ui.util.EditTextUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ForgotPasswordFrag extends Fragment {

    public static final String TAG = ForgotPasswordFrag.class.getName();
    private Button sendBtn;
    private View v;

    private TextInputLayout inputLayEmailIdOrMobileNumber;
    private AutoCompleteTextView inputEmailIdOrMobileNumber;
    private AppCompatTextView screen_title_header_tv;
    private CoordinatorLayout coordinatorLayout;

    public ForgotPasswordFrag() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViews() {

        screen_title_header_tv = (AppCompatTextView)v.findViewById(R.id.screen_title_header_tv);
        coordinatorLayout = (CoordinatorLayout)v.findViewById(R.id.coordinatorLayout);

        inputLayEmailIdOrMobileNumber = (TextInputLayout) v.findViewById(R.id.input_lay_email_id_or_mobile_no);
        inputEmailIdOrMobileNumber = (AutoCompleteTextView) v.findViewById(R.id.input_mobile_no);

        inputEmailIdOrMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputLayEmailIdOrMobileNumber.setError(null);
                inputLayEmailIdOrMobileNumber.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendBtn = (Button) v.findViewById(R.id.forgot_password_button);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        initViews();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onLoginClicked();
            }
        });

        return v;
    }

    /*private void showAlert(String message){
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }*/

    private void onLoginClicked() {
        BaseFragmentActivity baseFragmentActivity = (BaseFragmentActivity)getActivity();

        baseFragmentActivity.hideSoftKeyPad();

        if (baseFragmentActivity.getmSystemService().getActiveNetworkInfo() == null) {
            ((BaseFragmentActivity)getActivity()).showAlert(getResources().getString(R.string.network_error));
        } else {
            validateAndProceed();
        }
    }

    private void validateAndProceed(){

        if(!isDataValid())
        {
            return;
        }

        attemptToRequestOtp();
    }

    private boolean isDataValid() {

        boolean retValue = true;

        if (EditTextUtils.getText(inputEmailIdOrMobileNumber).length() == 0) {
            ((BaseFragmentActivity)getActivity()).showAlert(getResources().getString(R.string.please_enter_emailid_mobile_number));

            retValue = false;
        } else if (EditTextUtils.getText(inputEmailIdOrMobileNumber).length() > 0 && EditTextUtils.isNumeric(EditTextUtils.getText(inputEmailIdOrMobileNumber)) &&
                !(EditTextUtils.getText(inputEmailIdOrMobileNumber).contains("@") || EditTextUtils.getText(inputEmailIdOrMobileNumber).contains(".")) &&
                EditTextUtils.getText(inputEmailIdOrMobileNumber).length() > getResources().getInteger(R.integer.MOBILE_NO_LENGTH)) {
            ((BaseFragmentActivity)getActivity()).showAlert(getResources().getString(R.string.mobile_number_should_not_more_than_10));
            retValue = false;
        } else if (EditTextUtils.getText(inputEmailIdOrMobileNumber).length() > 0 && EditTextUtils.isNumeric(EditTextUtils.getText(inputEmailIdOrMobileNumber)) &&
                !(EditTextUtils.getText(inputEmailIdOrMobileNumber).contains("@") || EditTextUtils.getText(inputEmailIdOrMobileNumber).contains("."))
                && EditTextUtils.getText(inputEmailIdOrMobileNumber).startsWith("0")) {
            ((BaseFragmentActivity)getActivity()).showAlert(getResources().getString(R.string.invalid_mobile_number_starts_with_zero));
            retValue = false;
        }
        else {

            retValue = true;
        }

        return retValue;
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
                    ((BaseFragmentActivity)getActivity()).showAlert("Unable to process your request. Please try again.");
                }

                @Override
                public void onResponse(Call call, Response response) {
                    showProgress(false);
                    if (response.isSuccessful()) {
                        int responseCode = response.code();
                        if (responseCode == 200 || responseCode == 201) {
                            try {
                                String resStr = response.body().string();
                                LOG.d("UserLoginTask", resStr);
                                int status = 0;

                                UserAuthResponse userAuthResponse = JSONAdaptor.fromJSON(resStr, UserAuthResponse.class);
                                ResponseStatus responseStatus = userAuthResponse.getStatus();
                                if (responseStatus != null && responseStatus.getStatusCode() != null) {
                                    status = responseStatus.getStatusCode();
                                }
                                if (status > 0) {
                                    List<ErrorMessage> errorMessages = responseStatus.getErrorMessages();
                                    if (errorMessages != null && errorMessages.size() > 0) {
                                        ((BaseFragmentActivity)getActivity()).showAlert(errorMessages.get(0).getMessageDescription());
                                    }
                                }else{
                                    try {
//                                        Application.getInstance().setMobileNumber(Application.getInstance().getUserProfileUserIdOrGuid());
                                        getActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.login_container, new ValidateOTPFragment())
                                                .commit();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        ((BaseFragmentActivity)getActivity()).showAlert(e.getLocalizedMessage());
                                    }
                                }
                            } catch (IOException ioe) {
                                ((BaseFragmentActivity)getActivity()).showAlert("Server Unreachable. Please try after some time.");
                            } catch (NhanceException e) {
                                ((BaseFragmentActivity)getActivity()).showAlert("Server Unreachable. Please try after some time.");
                            }
                        } else if (responseCode == 404 || responseCode == 503) {
                            LOG.d(TAG, "Server Unreachable. Please try after some time");
                            ((BaseFragmentActivity)getActivity()).showAlert("Server Unreachable. Please try after some time.");
                        } else if (responseCode == 500) {
                            LOG.d(TAG, "Internal server error.");
                            ((BaseFragmentActivity)getActivity()).showAlert("Error while communicating with server, please contact administrator.");
                        } else {
                            ((BaseFragmentActivity)getActivity()).showAlert("Error while communicating with server, please contact administrator.");
                        }

                    } else {
                        ((BaseFragmentActivity)getActivity()).showAlert("Error while communicating with server, please contact administrator.");
                    }
                }

            };

            ChangePasswordModel login = new ChangePasswordModel();

            Application application = Application.getInstance();
            application.setMobileNumber("");
            application.setEmailId("");

            if (!(EditTextUtils.getText(inputEmailIdOrMobileNumber).contains("@") || EditTextUtils.getText(inputEmailIdOrMobileNumber).contains("."))) {
                String phoneNumber = EditTextUtils.getText(inputEmailIdOrMobileNumber);
                application.setMobileNumber(phoneNumber);
                login.setLoginPrincipal(phoneNumber);
            } else {
                String emailId = EditTextUtils.getText(inputEmailIdOrMobileNumber);
                application.setEmailId(emailId);
                login.setLoginPrincipal(emailId);
            }

            String isdCodeEditTextValue = "91";
            login.setIsdCode(Integer.parseInt(isdCodeEditTextValue));
            application.setIsdCode(Integer.parseInt(isdCodeEditTextValue));

            new CommonAction().addCommonRequestParameters(login);
            login = (ChangePasswordModel)NhanceApplication.getModelToTakeActions();

            NhanceApplication.setModelToTakeAction(login);

            LOG.d("Request===> ", login.toString());

            RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.OTP_REQUEST_URL, JSONAdaptor.toJSON(login), call);
        } catch (IOException e) {
            e.printStackTrace();
            ((BaseFragmentActivity)getActivity()).showAlert("Unable to process your request. Please try again.");
        } catch (NhanceException e) {
            e.printStackTrace();
            ((BaseFragmentActivity)getActivity()).showAlert("Unable to process your request. Please try again.");
        }
    }
}

package com.nhance.technician.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

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
import com.nhance.technician.ui.BaseFragmentActivity;
import com.nhance.technician.ui.TechOperationsActivity;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SetPasswordFrag extends Fragment implements ApplicationConstants {

    public static final String TAG = SetPasswordFrag.class.getName();
    private View v;

    private TextInputLayout newPswdInputLay,confirmNewPswdInputLay;
    private AppCompatEditText newPswdeET,confirmNewPswdeET;

    Button changePasswordBtn,skipPasswordBtn,forceChangePasswordBtn;

    boolean isComingFromOTPScreen = false;

    private CoordinatorLayout coordinatorLayout;

    public SetPasswordFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Bundle bundle = getArguments();
        if(bundle!=null)
            isComingFromOTPScreen = bundle.getBoolean(ApplicationConstants.CHANGE_PASSWORD_AFTER_OTP,false);
    }

    private View mProgressView;

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        getActivity().runOnUiThread(new Runnable() {
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

    private void initViews() {
        coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout);

        mProgressView = v.findViewById(R.id.login_progress);

        newPswdInputLay = (TextInputLayout) v.findViewById(R.id.new_pswd_input_lay);
        newPswdeET = (AppCompatEditText) v.findViewById(R.id.input_new_password);

        btn_layout = (LinearLayout)v.findViewById(R.id.btn_layout);

        confirmNewPswdInputLay = (TextInputLayout) v.findViewById(R.id.confirm_new_pswd_input_lay);
        confirmNewPswdeET = (AppCompatEditText) v.findViewById(R.id.input_confirm_new_password);

        changePasswordBtn = (Button) v.findViewById(R.id.change_pswd_bttn);
        skipPasswordBtn = (Button) v.findViewById(R.id.skip_button);

        forceChangePasswordBtn = (Button) v.findViewById(R.id.force_set_pswd_bttn);

        if(isComingFromOTPScreen){
            btn_layout.setVisibility(View.GONE);
        }else{
            forceChangePasswordBtn.setVisibility(View.GONE);
        }

        newPswdeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                newPswdInputLay.setError(null);
                newPswdInputLay.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmNewPswdeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                confirmNewPswdInputLay.setError(null);
                confirmNewPswdInputLay.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private SellerLoginDTO sellerLoginDTO = null;

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
                                        moveToDashboard();
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
            showAlert(e.getLocalizedMessage());
        } catch (NhanceException e) {
            e.printStackTrace();
            showAlert(e.getLocalizedMessage());
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSetPassword() {

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

                                            if(!new UserProfileTable().isUserProfileDetailsExists(Application.getInstance().getMobileNumber())) {
                                                new UserProfileTable().storeUserDetails(sellerLoginDTO);
                                            }

                                            int availableServiceReqInvoices = GeneratedInvoiceTable.getCountOfServiceRequestInvoices(sellerLoginDTO.getUserCode());
                                            if (availableServiceReqInvoices > 0) {
                                                moveToDashboard();
                                            } else {
                                                syncAndFetchStoredInvoices(sellerLoginDTO.getUserCode());
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

            String newPassword = newPswdeET.getText().toString().trim();

            sellerLoginDTO = new SellerLoginDTO();
            sellerLoginDTO.setPassword(newPassword);
            sellerLoginDTO.setMobileNumber(Application.getInstance().getMobileNumber());
            sellerLoginDTO.setIsdCode("91");
            sellerLoginDTO.setDefaultLocale("en_US");
            sellerLoginDTO.setAppType(Application.getInstance().getApplicationType());
            LOG.d("Request===> ", sellerLoginDTO.toString());
            RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.RESET_PASSWORD_REQ_URL, JSONAdaptor.toJSON(sellerLoginDTO), call);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Unable to process your request. Please try again.");
        } catch (NhanceException e) {
            e.printStackTrace();
            showAlert("Unable to process your request. Please try again.");
        }
    }

    private LinearLayout btn_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_set_password, container, false);

        initViews();

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((BaseFragmentActivity) getActivity()).hideSoftKeyPad();

                validateAndProceed();
            }
        });

        skipPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((BaseFragmentActivity) getActivity()).hideSoftKeyPad();
                syncAndFetchStoredInvoices(sellerLoginDTO.getUserCode());
            }
        });

        forceChangePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((BaseFragmentActivity) getActivity()).hideSoftKeyPad();

                validateAndProceed();
            }
        });

        return v;
    }

    private void showAlert(String message){
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void validateAndProceed(){

        if (((BaseFragmentActivity)getActivity()).getmSystemService().getActiveNetworkInfo() == null) {
            showAlert(getString(R.string.network_error));
            return;
        }

        if(!isDataValid())
        {
            return;
        }

        attemptSetPassword();
    }

    private boolean isDataValid() {

        boolean retValue = true;

        View focusView = null;

        if(newPswdeET.getText().toString().trim().length() == 0 ) {
            showAlert(getResources().getString(R.string.please_enter_new_password));
            retValue = false;
            focusView = newPswdeET;
        }
        else if(newPswdeET.getText().toString().trim().length() >0 && newPswdeET.getText().toString().trim().length()<6 ) {
            showAlert(getResources().getString(R.string.password_should_be_of_length));
            retValue = false;
            focusView = newPswdeET;
        }
        else if(confirmNewPswdeET.getText().toString().trim().length() == 0 ) {
            showAlert(getResources().getString(R.string.please_enter_confirm_password));
            retValue = false;
            focusView = confirmNewPswdeET;
        }
        else if(confirmNewPswdeET.getText().toString().trim().length() >0 && confirmNewPswdeET.getText().toString().trim().length()<6 ) {
            showAlert(getResources().getString(R.string.password_should_be_of_length));
            retValue = false;
            focusView = confirmNewPswdeET;
        }else if(!newPswdeET.getText().toString().trim().equals(confirmNewPswdeET.getText().toString().trim())) {
            showAlert(getResources().getString(R.string.new_password_and_confirm_password_should_be_same));
            retValue = false;
            focusView = newPswdeET;
        }

        if(focusView != null){
            focusView.requestFocus();
        }

        return retValue;
    }

    private void moveToDashboard(){

        Intent mainIntent = new Intent(getActivity(), TechOperationsActivity.class);
        mainIntent.putExtra("USERCODE", sellerLoginDTO.getUserCode());
        getActivity().startActivity(mainIntent);
        getActivity().finish();
    }
}

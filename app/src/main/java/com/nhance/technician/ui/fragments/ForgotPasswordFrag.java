package com.nhance.technician.ui.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.SellerLoginDTO;
import com.nhance.technician.networking.RestCall;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.networking.util.RestConstants;
import com.nhance.technician.ui.BaseFragmentActivity;
import com.nhance.technician.ui.custom.adapter.CountriesAdap;
import com.nhance.technician.ui.custom.dialogplus.DialogPlus;
import com.nhance.technician.ui.custom.dialogplus.OnBackPressListener;
import com.nhance.technician.ui.custom.dialogplus.ViewHolder;
import com.nhance.technician.ui.util.EditTextUtils;
import com.nhance.technician.util.AccessPreferences;
import com.nhance.technician.util.TypefaceUtils.TypefaceHelper;

import java.io.IOException;
import java.util.Arrays;
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

        countryCodesList = Arrays.asList(getResources().getStringArray(R.array.CountryCodes));
        countryCodeLay = (LinearLayout) v.findViewById(R.id.country_lay);
        mobileLay = (LinearLayout) v.findViewById(R.id.mobile_lay);
        countryFlagTv = (AppCompatTextView) v.findViewById(R.id.flag_tv);
        countryFlagIv = (AppCompatImageView) v.findViewById(R.id.flag_iv);
        countryISDCodeTv = (AppCompatTextView) v.findViewById(R.id.country_mobile_code_tv);
        TypefaceHelper.getInstance().setTypeface(countryISDCodeTv, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));

        //countryFlagTv.setText(localeToEmoji("IN"));
        String countryID = ((BaseFragmentActivity)getActivity()).getCountryID();
        countryFlagIv.setImageResource(getResources().getIdentifier("drawable/"
                + countryID, null, ((BaseFragmentActivity)getActivity()).getPackageName()));
        countryISDCodeTv.setText("+" + ((BaseFragmentActivity)getActivity()).getCountryDialCode());

        screen_title_header_tv = (AppCompatTextView)v.findViewById(R.id.screen_title_header_tv);
        TypefaceHelper.getInstance().setTypeface(screen_title_header_tv, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
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

        countryCodeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseFragmentActivity)getActivity()).hideSoftKeyPad();
                final DialogPlus dialog = DialogPlus.newDialog(getActivity())
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
                adapter = new CountriesAdap(getActivity(), countryCodesList, searchableET, noResultTv, dialog);
                countryLv.setAdapter(adapter);
                countryLv.setLayoutManager(new LinearLayoutManager(getActivity()));

                adapter.setOnItemClickListener(new CountriesAdap.CountryClickListener() {
                    @Override
                    public void onCountryClick(View view, int position, String[] countryStrArray) {
                        AppCompatTextView flagTv = (AppCompatTextView) view.findViewById(R.id.flag_tv);
                        AppCompatTextView countryTv = (AppCompatTextView) view.findViewById(R.id.country_name_tv);
                        countryFlagTv.setText(flagTv.getText());
                        String pngName = countryStrArray[1].trim().toLowerCase();
                        countryFlagIv.setImageResource(getResources().getIdentifier("drawable/" + pngName, null, getActivity().getPackageName()));

                        Log.v("CountryISOCode", "" + countryStrArray[0]);
                        String ISOCode = countryStrArray[0];
                        countryISDCodeTv.setText("");
                        countryISDCodeTv.setText("+" + ISOCode);
                        countryISDCodeTv.invalidate();
                        ((BaseFragmentActivity)getActivity()).hideSoftKeyPad();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private List<String> countryCodesList;
    private LinearLayout countryCodeLay, mobileLay;
    private AppCompatTextView countryFlagTv, countryISDCodeTv;
    private AppCompatImageView countryFlagIv;
    private CountriesAdap adapter;

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

    private SellerLoginDTO sellerLoginDTO = null;

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
                                        ((BaseFragmentActivity)getActivity()).showAlert(errorMsg);
                                    } else {
                                        LOG.d("", sellerLoginDTO.toString());
                                        try {
                                            Application.getInstance().setMobileNumber(sellerLoginDTO.getMobileNumber());
                                            getActivity().getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.login_container, new ValidateOTPFragment())
                                                    .commit();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            ((BaseFragmentActivity)getActivity()).showAlert(e.getLocalizedMessage());
                                        }
                                    }
                                } else {
                                    ((BaseFragmentActivity)getActivity()).showAlert(getResources().getString(R.string.unable_to_process));
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

            sellerLoginDTO = new SellerLoginDTO();

            String isdCode = EditTextUtils.getText(countryISDCodeTv);
            if (isdCode != null && !isdCode.isEmpty() && isdCode.length() > 0) {
                String isdCodeEditTextValue = isdCode.replace("+", "");

                if (isdCodeEditTextValue != null && !isdCodeEditTextValue.isEmpty() && isdCodeEditTextValue.length() > 0) {
                    Application.getInstance().setIsdCode(Integer.parseInt(isdCodeEditTextValue));
                    sellerLoginDTO.setIsdCode(isdCodeEditTextValue);
                }
                if(isdCodeEditTextValue.trim().equalsIgnoreCase("971") && NhanceApplication.PACKAGE_NAME.equalsIgnoreCase(NhanceApplication.prodPackageName)){
                    NhanceApplication.getApplication().setBackendUrl(getResources().getString(R.string.ae_production_backend_url));;
                    AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.NHANCE_SERVER_URL, NhanceApplication.getApplication().getBackendUrl());
                }else{
                    String backendUrl = null;
                    if(NhanceApplication.PACKAGE_NAME.equalsIgnoreCase(NhanceApplication.devPackageName)){
                        backendUrl = getResources().getString(R.string.qa_backend_url);
                    }else if(NhanceApplication.PACKAGE_NAME.equalsIgnoreCase(NhanceApplication.demoPackageName)){
                        backendUrl = getResources().getString(R.string.demo_backend_url);
                    }else if(NhanceApplication.PACKAGE_NAME.equalsIgnoreCase(NhanceApplication.prodPackageName)){
                        backendUrl = getResources().getString(R.string.in_production_backend_url);
                    }

                    if(isdCodeEditTextValue.trim().equalsIgnoreCase("971")){
                        backendUrl = getResources().getString(R.string.ae_production_backend_url);
                    }

                    NhanceApplication.getApplication().setBackendUrl(backendUrl);
                    AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.NHANCE_SERVER_URL, NhanceApplication.getApplication().getBackendUrl());
                }
            }

            sellerLoginDTO.setMobileNumber(EditTextUtils.getText(inputEmailIdOrMobileNumber));
            sellerLoginDTO.setDefaultLocale("en_US");
            sellerLoginDTO.setAppType(Application.getInstance().getApplicationType());
            LOG.d("Request===> ", sellerLoginDTO.toString());
            RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.OTP_REQUEST_URL, JSONAdaptor.toJSON(sellerLoginDTO), call);
        } catch (IOException e) {
            e.printStackTrace();
            ((BaseFragmentActivity)getActivity()).showAlert("Unable to process your request. Please try again.");
        } catch (NhanceException e) {
            e.printStackTrace();
            ((BaseFragmentActivity)getActivity()).showAlert("Unable to process your request. Please try again.");
        }
    }
}

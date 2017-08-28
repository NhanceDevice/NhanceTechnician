package com.nhance.technician.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nhance.technician.R;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.ServiceRequestDTO;
import com.nhance.technician.networking.RestCall;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.networking.util.RestConstants;
import com.nhance.technician.ui.TechOperationsActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by afsarhussain on 06/07/17.
 */

public class SearchSRNFragment extends Fragment {

    public static final String TAG = SearchSRNFragment.class.getName();

    private View mProgressView;
    LinearLayout searchFieldContainerView;
    TextInputEditText serviceRequestnoTV;

    private ServiceRequestDTO serviceRequestDTO;

    public SearchSRNFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_section_search_srn, container, false);

        mProgressView = rootView.findViewById(R.id.fetch_progress);
        searchFieldContainerView = (LinearLayout) rootView.findViewById(R.id.searchfield_container);
        serviceRequestnoTV = (TextInputEditText) rootView.findViewById(R.id.servicerequestnotv);

        rootView.findViewById(R.id.get_details_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchServiceRequestDetails();

            }
        });
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_srn_search, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {

            ((TechOperationsActivity)getActivity()).logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        resetServiceReqNoField();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void showGenerateInvoiceFragment(){
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        GenerateInvoiceFragment generateInvoiceFragment = new GenerateInvoiceFragment();
        generateInvoiceFragment.setServiceRequestDTO(serviceRequestDTO);
        trans.replace(R.id.root_frame,generateInvoiceFragment,RootFragment.FRAGMENT_TAG);
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(TAG);
        trans.commit();
    }


    private void fetchServiceRequestDetails() {
        boolean cancel = false;
        String serviceReqNo = serviceRequestnoTV.getText().toString();
        if (TextUtils.isEmpty(serviceReqNo)) {
            serviceRequestnoTV.setError(getString(R.string.error_field_required));
            cancel = true;
        }

        if (cancel) {
            serviceRequestnoTV.requestFocus();
        } else {
            showProgress(true);
            try {

                Callback call = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        showProgress(false);
                        serviceRequestDTO = null;
                        serviceRequestDTO = new ServiceRequestDTO();
                        serviceRequestDTO.setResponseStatus(1);
                        serviceRequestDTO.setMessageDescription("Unable to process your request. Please try again.");
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        showProgress(false);
                        if (response.isSuccessful()) {
                            int responseCode = response.code();

                            if (responseCode == 200) {
                                try {
                                    String resStr = response.body().string();
                                    LOG.d("fetchServiceRequestDetails", resStr);
                                    serviceRequestDTO = JSONAdaptor.fromJSON(resStr, ServiceRequestDTO.class);

                                    if (serviceRequestDTO != null) {
                                        int status = 0;
                                        if (serviceRequestDTO.getResponseStatus() != null) {
                                            status = serviceRequestDTO.getResponseStatus();
                                        }
                                        if (status > 0) {
                                            String errorMsg = serviceRequestDTO.getMessageDescription();
                                            Snackbar snackbar = Snackbar.make(((TechOperationsActivity) getActivity()).getCoordinatorLayout(), errorMsg, Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        } else {
                                            LOG.d("Search Response : ", serviceRequestDTO.toString());
                                            //showPartDetailsLayout();
                                            showGenerateInvoiceFragment();

                                        }
                                    } else {
                                        Snackbar snackbar = Snackbar.make(((TechOperationsActivity) getActivity()).getCoordinatorLayout(), getResources().getString(R.string.unable_to_process), Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                } catch (IOException ioe) {
                                    Snackbar snackbar = Snackbar.make(((TechOperationsActivity) getActivity()).getCoordinatorLayout(), "Unable to process your request. Please try again.", Snackbar.LENGTH_LONG);
                                    snackbar.show();

                                } catch (NhanceException e) {
                                    Snackbar snackbar = Snackbar.make(((TechOperationsActivity) getActivity()).getCoordinatorLayout(), "Unable to process your request. Please try again.", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            } else if (responseCode == 404 || responseCode == 503) {
                                LOG.d(TAG, "Server Unreachable. Please try after some time");
                                Snackbar snackbar = Snackbar.make(((TechOperationsActivity) getActivity()).getCoordinatorLayout(), "Server Unreachable. Please try after some time", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else if (responseCode == 500) {
                                LOG.d(TAG, "Internal server error.");
                                Snackbar snackbar = Snackbar.make(((TechOperationsActivity) getActivity()).getCoordinatorLayout(), "Error while communicating with server, please contact administrator.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else {
                                Snackbar snackbar = Snackbar.make(((TechOperationsActivity) getActivity()).getCoordinatorLayout(), "Error while communicating with server, please contact administrator.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }

                        } else {
                            Snackbar snackbar = Snackbar.make(((TechOperationsActivity) getActivity()).getCoordinatorLayout(), "Error while communicating with server, please contact administrator.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                };
                serviceRequestDTO = new ServiceRequestDTO();
                serviceRequestDTO.setUserCode(((TechOperationsActivity) getActivity()).getUserCode());
                serviceRequestDTO.setServiceRequestNumber(serviceReqNo);
                serviceRequestDTO.setDefaultLocale("en_US");
                LOG.d("Request===> ", serviceRequestDTO.toString());
                RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.GET_SER_REQ_DETAILS, JSONAdaptor.toJSON(serviceRequestDTO), call);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NhanceException e) {
                e.printStackTrace();
            }
        }
    }

    private void resetServiceReqNoField() {
        if (serviceRequestnoTV != null)
            serviceRequestnoTV.setText("");
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int shortAnimTime = getActivity().getResources().getInteger(android.R.integer.config_shortAnimTime);
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
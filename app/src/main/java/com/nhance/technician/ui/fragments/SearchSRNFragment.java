package com.nhance.technician.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.newapis.ResponseStatus;
import com.nhance.technician.model.newapis.ServiceRequestModel;
import com.nhance.technician.model.newapis.ServiceRequestModelFindResponse;
import com.nhance.technician.networking.RestCall;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.networking.util.RestConstants;
import com.nhance.technician.ui.BaseFragmentActivity;
import com.nhance.technician.ui.action.CommonAction;
import com.nhance.technician.ui.custom.adapter.AssignedServiceRequestAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by afsarhussain on 06/07/17.
 */

public class SearchSRNFragment extends Fragment implements AssignedServiceRequestAdapter.AssignedServiceRequestAdapterInterface {

    public static final String TAG = SearchSRNFragment.class.getName();

    private View mProgressView;

    private RelativeLayout service_request_summary_rl;
    private RecyclerView service_request_rv;
    private List<ServiceRequestModel> assignedServiceRequest;
    private AssignedServiceRequestAdapter assignedServiceRequestAdapter;
    private TextView service_not_available_tv;

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
        service_not_available_tv = (TextView) rootView.findViewById(R.id.service_not_available_tv);
        service_request_summary_rl = (RelativeLayout) rootView.findViewById(R.id.service_request_summary_rl);
        mProgressView = rootView.findViewById(R.id.fetch_progress);
        service_request_rv = (RecyclerView)rootView.findViewById(R.id.service_request_rv);
        service_request_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        service_request_rv.setLayoutManager(layoutManager);

        /*service_request_rv.addOnItemTouchListener(new RecyclerviewClickListeners.RecyclerTouchListener(getActivity(),
                service_request_rv, new RecyclerviewClickListeners.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ServiceRequestModel serviceRequestModel = assignedServiceRequest.get(position);
                try {
                    ServiceRequestModel selectedServiceRequestModel = new CommonAction().getAssignedServiceRequestDetails(serviceRequestModel.getGuid());
                    showGenerateInvoiceFragment(selectedServiceRequestModel);
                } catch (NhanceException e) {
                    e.printStackTrace();
                    ((BaseFragmentActivity)getActivity()).showAlert(e.getLocalizedMessage());
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/

        fetchServiceRequestDetails();

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void showGenerateInvoiceFragment(ServiceRequestModel selectedServiceRequest) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        GenerateInvoiceFragment generateInvoiceFragment = new GenerateInvoiceFragment();
        generateInvoiceFragment.setServiceRequest(selectedServiceRequest);
        trans.replace(R.id.root_frame, generateInvoiceFragment, RootFragment.FRAGMENT_TAG);
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(TAG);
        trans.commit();
    }


    private void fetchServiceRequestDetails() {

        showProgress(true);
        try {

            Callback call = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    showProgress(false);
                    /*((BaseFragmentActivity)getActivity()).showAlert("Unable to process your request. Please try again.");*/
                    new PopulateSR().execute();
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

                                int status = 0;

                                ServiceRequestModelFindResponse serviceRequestModelFindResponse = JSONAdaptor.fromJSON(resStr, ServiceRequestModelFindResponse.class);
                                ResponseStatus responseStatus = serviceRequestModelFindResponse.getStatus();
                                if (responseStatus != null && responseStatus.getStatusCode() != null) {
                                    status = responseStatus.getStatusCode();
                                }
                                if (status > 0) {
                                   /* List<ErrorMessage> errorMessages = responseStatus.getErrorMessages();
                                    if (errorMessages != null && errorMessages.size() > 0) {
                                        ((BaseFragmentActivity)getActivity()).showAlert(errorMessages.get(0).getMessageDescription());
                                    }*/new PopulateSR().execute();
                                }else{
                                    List<ServiceRequestModel> serviceRequestModelList = serviceRequestModelFindResponse.getResults();
                                    if(serviceRequestModelList != null && serviceRequestModelList.size() > 0){
                                        for(ServiceRequestModel serviceRequestModel : serviceRequestModelList){
                                            new CommonAction().storeOrUpdateAssignedServiceReqDetails(serviceRequestModel);
                                        }
                                        new CommonAction().updateSyncDownloadStatus(ApplicationConstants.SYNC_ASSIGNED_SR);
                                        new PopulateSR().execute();
                                    }else
                                        new PopulateSR().execute();
                                }

                            } catch (IOException ioe) {
//                                ((BaseFragmentActivity)getActivity()).showAlert("Unable to process your request. Please try again.");
                                new PopulateSR().execute();
                            } catch (NhanceException e) {
//                                ((BaseFragmentActivity)getActivity()).showAlert("Unable to process your request. Please try again.");
                                new PopulateSR().execute();
                            }
                        } else if (responseCode == 404 || responseCode == 503) {
                            LOG.d(TAG, "Server Unreachable. Please try after some time");
//                            ((BaseFragmentActivity)getActivity()).showAlert("Server Unreachable. Please try after some time");
                            new PopulateSR().execute();
                        } else if (responseCode == 500) {
                            LOG.d(TAG, "Internal server error.");
//                            ((BaseFragmentActivity)getActivity()).showAlert("Error while communicating with server, please contact administrator.");
                            new PopulateSR().execute();
                        } else {
//                            ((BaseFragmentActivity)getActivity()).showAlert("Error while communicating with server, please contact administrator.");
                            new PopulateSR().execute();
                        }

                    } else {
//                        ((BaseFragmentActivity)getActivity()).showAlert("Error while communicating with server, please contact administrator.");
                        new PopulateSR().execute();
                    }
                }

            };
            ServiceRequestModel serviceRequestModel = new ServiceRequestModel();

            serviceRequestModel.setUserId(Application.getInstance().getUserProfileUserIdOrGuid());
            long lastSyncTime = new CommonAction().updateSyncInProgress(ApplicationConstants.SYNC_ASSIGNED_SR).getLastSyncTime();
            serviceRequestModel.setLastSyncTime(lastSyncTime);
            serviceRequestModel.setDefaultLocale("en_US");
            RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.SYNC_SER_REQ_DETAILS, JSONAdaptor.toJSON(serviceRequestModel), call);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NhanceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callCustomer(String customerMobileNumber, int selectedIndexPos) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+customerMobileNumber));
        getActivity().startActivity(intent);
    }

    @Override
    public void initiateInvoiceGenerator(ServiceRequestModel serviceRequestModel, int selectedIndexPos) {
//        ServiceRequestModel serviceRequestModel = assignedServiceRequest.get(position);
        try {
            ServiceRequestModel selectedServiceRequestModel = new CommonAction().getAssignedServiceRequestDetails(serviceRequestModel.getGuid());
            showGenerateInvoiceFragment(selectedServiceRequestModel);
        } catch (NhanceException e) {
            e.printStackTrace();
            ((BaseFragmentActivity)getActivity()).showAlert(e.getLocalizedMessage());
        }
    }

    class PopulateSR extends AsyncTask<Void, Void, Exception> {

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected Exception doInBackground(Void... voids) {
            Exception exception = null;
            try {
                assignedServiceRequest = new CommonAction().getAssignedServiceRequest(Application.getInstance().getUserProfileUserIdOrGuid(), com.nhance.technician.model.newapis.enums.Status.PENDING.getCode());
                if(assignedServiceRequest != null && assignedServiceRequest.size() > 0){
                    updateToUI(assignedServiceRequest);
                }
            } catch (NhanceException e) {
                e.printStackTrace();
                exception = e;
            }

            return exception;
        }



        @Override
        protected void onPostExecute(Exception e) {
            showProgress(false);
            if(e != null){
                service_not_available_tv.setVisibility(View.VISIBLE);
                service_request_summary_rl.setVisibility(View.INVISIBLE);
                ((BaseFragmentActivity)getActivity()).showAlert(e.getLocalizedMessage());
            }
        }
    }

    private void updateToUI(final List<ServiceRequestModel> serviceRequestModelList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assignedServiceRequestAdapter = new AssignedServiceRequestAdapter(serviceRequestModelList, SearchSRNFragment.this);
                service_request_rv.setAdapter(assignedServiceRequestAdapter);
            }
        });
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
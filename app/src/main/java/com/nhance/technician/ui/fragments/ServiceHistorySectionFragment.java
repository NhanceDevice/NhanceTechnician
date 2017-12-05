package com.nhance.technician.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.datasets.GeneratedInvoiceTable;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.newapis.ServiceRequestInvoiceModel;
import com.nhance.technician.ui.custom.adapter.ServiceRequestHistoryAdapter;

import java.util.List;

/**
 * Created by afsarhussain on 11/07/17.
 */

public class ServiceHistorySectionFragment extends Fragment {

    public static final String TAG = ServiceHistorySectionFragment.class.getName();
    private View mProgressView;
    private TextView noDataTextView;
    private RecyclerView serviceHistoryRecyclerView;
    private UpdateViewBroadcastReceiver updateViewBroadcastReceiver;
    private RelativeLayout serviceHistorySummaryRL;
    /*private double totalCashCollected, totalOnlinePayment = 0D;
    private AppCompatTextView totalCashCollectedACTV, totalCashCollectedCurrencyACTV, totalOnlinePaymentACTV, totalOnlinePaymentCurrencyACTV;
*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        updateViewBroadcastReceiver = new UpdateViewBroadcastReceiver();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_serv_history, container, false);
        mProgressView = rootView.findViewById(R.id.fetch_progress);
        noDataTextView = (TextView) rootView.findViewById(R.id.service_history_not_available_tv);
        serviceHistoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.service_history_rv);
        serviceHistorySummaryRL = (RelativeLayout) rootView.findViewById(R.id.service_history_summary_rl);
        /*totalCashCollectedACTV = (AppCompatTextView) rootView.findViewById(R.id.total_cash_collected_tv);
        totalCashCollectedCurrencyACTV = (AppCompatTextView) rootView.findViewById(R.id.total_cash_collected_currency_symbol_tv);
        totalOnlinePaymentACTV = (AppCompatTextView) rootView.findViewById(R.id.total_online_payment_tv);
        totalOnlinePaymentCurrencyACTV = (AppCompatTextView) rootView.findViewById(R.id.total_online_payment_currency_symbol_tv);
        */return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ApplicationConstants.ACTION_UPDATE_VIEW);
        getActivity().registerReceiver(updateViewBroadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(updateViewBroadcastReceiver);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setHasOptionsMenu(true);
        if (isVisibleToUser) {
            GetStoredInvoiceTask getStoredInvoiceTask = new GetStoredInvoiceTask(Application.getInstance().getUserProfileUserIdOrGuid());
            getStoredInvoiceTask.execute();
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem logoutItem = menu.findItem(R.id.action_logout);
        if (logoutItem != null)
            logoutItem.setVisible(false);
        MenuItem previewItem = menu.findItem(R.id.action_preview);
        if (previewItem != null)
            previewItem.setVisible(false);
        MenuItem sendItem = menu.findItem(R.id.action_send);
        if (sendItem != null)
            sendItem.setVisible(false);
    }

    /* @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         inflater.inflate(R.menu.menu_service_history, menu);
     }

     public boolean onOptionsItemSelected(MenuItem item) {
         if (item.getItemId() == R.id.action_clear) {

             AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
             builder.setTitle(getResources().getString(R.string.clear_ser_history));
             builder.setMessage(getResources().getString(R.string.sure_to_clear));
             builder.setPositiveButton(getResources().getString(R.string.clear_all), new DialogInterface.OnClickListener() {

                 public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                     try {
                         GeneratedInvoiceTable.clearAllServiceHistoryForUser(Application.getInstance().getUserCode());
                         GetStoredInvoiceTask getStoredInvoiceTask = new GetStoredInvoiceTask(Application.getInstance().getUserProfileUserIdOrGuid());
                         getStoredInvoiceTask.execute();
                     } catch (NhanceException e) {
                         e.printStackTrace();
                     }

                 }
             });
             builder.setCancelable(false);
             builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {

                 public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                 }
             });
             builder.show();
             return true;

         }
         return super.onOptionsItemSelected(item);
     }*/
    public class GetStoredInvoiceTask extends AsyncTask<Void, Object, List<ServiceRequestInvoiceModel>> {

        private final String mUser_guid;

        GetStoredInvoiceTask(String userCode) {
            mUser_guid = userCode;
        }

        @Override
        protected List<ServiceRequestInvoiceModel> doInBackground(Void... params) {


            try {
                showProgress(true);
                List<ServiceRequestInvoiceModel> serviceRequestInvoiceDTOList = new GeneratedInvoiceTable().getServiceRequestInvoices(mUser_guid);
                /*totalCashCollected = 0D;
                totalOnlinePayment = 0D;
                for (ServiceRequestInvoiceDTO serviceRequestInvoiceDTO : serviceRequestInvoiceDTOList) {
                    if (serviceRequestInvoiceDTO.getModeOfPayment() == ApplicationConstants.MODE_OF_PAYMENT_CASH) {
                        totalCashCollected += serviceRequestInvoiceDTO.getNetPaybleAmount();
                    } else if (serviceRequestInvoiceDTO.getModeOfPayment() == ApplicationConstants.MODE_OF_PAYMENT_ONLINE) {
                        totalOnlinePayment += serviceRequestInvoiceDTO.getNetPaybleAmount();
                    }
                }*/
                return serviceRequestInvoiceDTOList;
            } catch (NhanceException e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<ServiceRequestInvoiceModel> serviceRequestInvoiceDTOList) {
            showProgress(false);

            if (serviceRequestInvoiceDTOList != null && serviceRequestInvoiceDTOList.size()>0) {
                LOG.d(TAG, serviceRequestInvoiceDTOList.toString());
                serviceHistorySummaryRL.setVisibility(View.VISIBLE);

                noDataTextView.setVisibility(View.GONE);
                serviceHistoryRecyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                serviceHistoryRecyclerView.setLayoutManager(layoutManager);
                ServiceRequestHistoryAdapter adapter = new ServiceRequestHistoryAdapter(getActivity(), serviceRequestInvoiceDTOList);
                serviceHistoryRecyclerView.setAdapter(adapter);
                /*totalCashCollectedCurrencyACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestInvoiceDTOList.get(0).getCurrencyCode(), 16))));
                totalOnlinePaymentCurrencyACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestInvoiceDTOList.get(0).getCurrencyCode(), 16))));
                totalCashCollectedACTV.setText(Util.getFormattedAmount(totalCashCollected));
                totalOnlinePaymentACTV.setText(Util.getFormattedAmount(totalOnlinePayment));*/

            } else {
                serviceHistoryRecyclerView.removeAllViews();
                serviceHistorySummaryRL.setVisibility(View.GONE);
                noDataTextView.setVisibility(View.VISIBLE);
                noDataTextView.setText(R.string.no_history_text);
            }
        }


        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }

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

    private class UpdateViewBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            GetStoredInvoiceTask getStoredInvoiceTask = new GetStoredInvoiceTask(Application.getInstance().getUserProfileUserIdOrGuid());
            getStoredInvoiceTask.execute();
        }
    }
}

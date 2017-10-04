package com.nhance.technician.ui.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.datasets.GeneratedInvoiceTable;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.ServicePartDTO;
import com.nhance.technician.model.ServiceRequestDTO;
import com.nhance.technician.model.ServiceRequestInvoiceDTO;
import com.nhance.technician.networking.RestCall;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.networking.util.RestConstants;
import com.nhance.technician.ui.BaseFragmentActivity;
import com.nhance.technician.ui.TechOperationsActivity;
import com.nhance.technician.util.Util;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by afsarhussain on 10/07/17.
 */

public class InvoicePreviewFragment extends Fragment implements ApplicationConstants{


    public static final String TAG = InvoicePreviewFragment.class.getName();
    private ServiceRequestInvoiceDTO serviceRequestInvoiceDTO;
    private double totalAmount = 0D;
    private double discountAmount = 0D;
    private double netPayableAmount = 0D;
    private RelativeLayout mPartDetailsView;
    AutoCompleteTextView discountAmountACTV, additionalLabourChargeACTV;
    AppCompatTextView serviceReqNoACTV, customerNameACTV, customerMobNoACTV, installationChargesACTV,serviceReqChargesHeaderACTV,
            totalAmountACTV, netPayableAmountACTV, discountAmountCurrencySymbolACTV, additionalLabourChargeCurrencySymbolACTV, taxNameACTV, taxAmountACTV, taxAmountCurrencySymbolACTV,
            selectedModeOfPaymentTV;
    LinearLayout partsInstalledLL;
    private RadioGroup newPartsInstalledRG;
    LinearLayout partsDetailsContainerLL;
    private List<ServicePartDTO> partDetails;
    private ServiceRequestDTO serviceRequestDTO;
    private double additionalLabourCharge = 0D;
    private Double taxAmount = 0D;
    private Integer selectedModeOfPayment = MODE_OF_PAYMENT_ONLINE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_invoice_preview, container, false);
        mPartDetailsView = (RelativeLayout) rootView.findViewById(R.id.part_details_input_ll);
        serviceReqNoACTV = (AppCompatTextView) mPartDetailsView.findViewById(R.id.ser_req_no_tv);
        customerNameACTV = (AppCompatTextView) mPartDetailsView.findViewById(R.id.cust_name_tv);
        customerMobNoACTV = (AppCompatTextView) mPartDetailsView.findViewById(R.id.cust_mob_no_tv);
        serviceReqChargesHeaderACTV =  (AppCompatTextView) rootView.findViewById(R.id.ser_req_charges_header);
        installationChargesACTV = (AppCompatTextView) mPartDetailsView.findViewById(R.id.inst_charges_tv);
        totalAmountACTV = (AppCompatTextView) mPartDetailsView.findViewById(R.id.total_amount_tv);
        discountAmountCurrencySymbolACTV = (AppCompatTextView) rootView.findViewById(R.id.discount_amount_currency_symbol_tv);
        discountAmountACTV = (AutoCompleteTextView) rootView.findViewById(R.id.discount_amount_tv);
        additionalLabourChargeCurrencySymbolACTV = (AppCompatTextView) rootView.findViewById(R.id.additional_labour_charge_currency_symbol_tv);
        additionalLabourChargeACTV = (AutoCompleteTextView) rootView.findViewById(R.id.additional_labour_charge_tv);
        taxNameACTV = (AppCompatTextView) rootView.findViewById(R.id.tax_name_tv);
        taxAmountCurrencySymbolACTV = (AppCompatTextView) rootView.findViewById(R.id.tax_amount_currency_symbol_tv);
        taxAmountACTV = (AppCompatTextView) rootView.findViewById(R.id.tax_amount_tv);
        discountAmountACTV = (AutoCompleteTextView) mPartDetailsView.findViewById(R.id.discount_amount_tv);
        netPayableAmountACTV = (AppCompatTextView) mPartDetailsView.findViewById(R.id.net_payable_amount_tv);
        selectedModeOfPaymentTV = (AppCompatTextView) mPartDetailsView.findViewById(R.id.selected_mode_of_payment_tv);
        partsInstalledLL = (LinearLayout) mPartDetailsView.findViewById(R.id.part_installed_rg_ll);
        partsDetailsContainerLL = (LinearLayout) mPartDetailsView.findViewById(R.id.part_details_container_ll);
        serviceReqChargesHeaderACTV.setText(String.format(getResources().getString(R.string.instllation_charges),serviceRequestDTO.getServiceRequestSubject()));
        discountAmountCurrencySymbolACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))));
        additionalLabourChargeCurrencySymbolACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))));
        additionalLabourChargeACTV.setText(Util.getFormattedAmount(additionalLabourCharge));
        taxAmountCurrencySymbolACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))));
        taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));
        discountAmountACTV.setText(Util.getFormattedAmount(discountAmount));
        if (serviceRequestDTO != null) {
            if (serviceRequestDTO.getTaxName() != null) {
                taxNameACTV.setText(serviceRequestDTO.getTaxName());
            }
            if (serviceRequestDTO.getServiceRequestNumber() != null) {
                serviceReqNoACTV.setText(serviceRequestDTO.getServiceRequestNumber());
            }
            if (serviceRequestDTO.getCustomerName() != null) {
                customerNameACTV.setText(serviceRequestDTO.getCustomerName());
            }
            if (serviceRequestDTO.getMobileNumber() != null) {
                customerMobNoACTV.setText(serviceRequestDTO.getMobileNumber());
            }
            if (serviceRequestDTO.getAmount() != null) {
                installationChargesACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(serviceRequestDTO.getAmount()));
            }
            totalAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(totalAmount));
            netPayableAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(netPayableAmount));

            if(selectedModeOfPayment == MODE_OF_PAYMENT_CASH){
                selectedModeOfPaymentTV.setText(CASH_PAYMENT);
            }else if(selectedModeOfPayment == MODE_OF_PAYMENT_ONLINE){
                selectedModeOfPaymentTV.setText(ONLINE_PAYMENT);
            }


            partsInstalledLL.setVisibility(View.VISIBLE);
            newPartsInstalledRG = (RadioGroup) partsInstalledLL.findViewById(R.id.parts_installed_rg);
            newPartsInstalledRG.setEnabled(false);
            if (partDetails != null && partDetails.size() > 0) {
                partsDetailsContainerLL.setVisibility(View.VISIBLE);
                newPartsInstalledRG.check(R.id.parts_installed_yes_rb);
                for (ServicePartDTO partDetail : partDetails) {
                    inflatePartDetailsView(partDetail);
                }

            } else {
                newPartsInstalledRG.check(R.id.parts_installed_no_rb);
                partsDetailsContainerLL.setVisibility(View.INVISIBLE);
            };
        }
        return rootView;
    }

    private void inflatePartDetailsView(ServicePartDTO partDetails) {
        if (partDetails == null) {
            return;
        }
        View inflatedView = View.inflate(getActivity(), R.layout.content_part_details_preview, null);
        AppCompatTextView partNameTV = (AppCompatTextView) inflatedView.findViewById(R.id.partname_preview_actv);
        AppCompatTextView quantityTV = (AppCompatTextView) inflatedView.findViewById(R.id.quantity_preview_actv);
        AppCompatTextView amountTV = (AppCompatTextView) inflatedView.findViewById(R.id.amount_preview_actv);

        partNameTV.setText(partDetails.getPartName());
        quantityTV.setText(String.valueOf(partDetails.getQuantity()));
        amountTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount((partDetails.getCalculatedAmount())));

        partsDetailsContainerLL.addView(inflatedView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_invoice_preview, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                if (((BaseFragmentActivity)getActivity()).getmSystemService().getActiveNetworkInfo() == null) {
                    ((BaseFragmentActivity)getActivity()).showAlert(getString(R.string.network_error));
                }else
                    sendDetailsToGenerateInvoice();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendDetailsToGenerateInvoice() {
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
                                serviceRequestInvoiceDTO = JSONAdaptor.fromJSON(resStr, ServiceRequestInvoiceDTO.class);

                                if (serviceRequestInvoiceDTO != null) {
                                    int status = 0;
                                    if (serviceRequestInvoiceDTO.getResponseStatus() != null) {
                                        status = serviceRequestInvoiceDTO.getResponseStatus();
                                    }
                                    if (status > 0) {
                                        String errorMsg = serviceRequestInvoiceDTO.getMessageDescription();
                                        ((BaseFragmentActivity)getActivity()).showAlert(errorMsg);
                                    } else {
                                        LOG.d("", serviceRequestInvoiceDTO.toString());
                                        new GeneratedInvoiceTable().storeServiceReqInvoiceDetails(serviceRequestDTO, serviceRequestInvoiceDTO,selectedModeOfPayment);
                                        showInvoiceGeneratedDialog(serviceRequestInvoiceDTO);
                                    }
                                } else {
                                    ((BaseFragmentActivity)getActivity()).showAlert( getResources().getString(R.string.unable_to_process));
                                }
                            } catch (IOException ioe) {
                                ((BaseFragmentActivity)getActivity()).showAlert("Unable to process your request. Please try again.");

                            } catch (NhanceException e) {
                                ((BaseFragmentActivity)getActivity()).showAlert("Unable to process your request. Please try again.");
                            }
                        } else if (responseCode == 404 || responseCode == 503) {
                            LOG.d(TAG, "Server Unreachable. Please try after some time");
                            ((BaseFragmentActivity)getActivity()).showAlert("Server Unreachable. Please try after some time");
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
            serviceRequestInvoiceDTO = new ServiceRequestInvoiceDTO();
            serviceRequestInvoiceDTO.setUserCode(((TechOperationsActivity) getActivity()).getUserCode());
            serviceRequestInvoiceDTO.setServiceRequestNumber(serviceRequestDTO.getServiceRequestNumber());
            serviceRequestInvoiceDTO.setServiceRequestSubject(serviceRequestDTO.getServiceRequestSubject());
            serviceRequestInvoiceDTO.setBaseAmount(serviceRequestDTO.getAmount());
            serviceRequestInvoiceDTO.setTotalAmount(totalAmount);
            serviceRequestInvoiceDTO.setDiscount(discountAmount);
            serviceRequestInvoiceDTO.setNetPaybleAmount(netPayableAmount);
            serviceRequestInvoiceDTO.setAdditionLabourCharge(additionalLabourCharge);
            serviceRequestInvoiceDTO.setTaxAmount(taxAmount);
            serviceRequestInvoiceDTO.setParts(partDetails);
            serviceRequestInvoiceDTO.setModeOfPayment(selectedModeOfPayment);
            serviceRequestInvoiceDTO.setDefaultLocale("en_US");
            LOG.d("Request===> ", serviceRequestInvoiceDTO.toString());
            RestCall.post(NhanceApplication.getApplication().getBackendUrl() + RestConstants.UPLOAD_INVOICE, JSONAdaptor.toJSON(serviceRequestInvoiceDTO), call);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NhanceException e) {
            e.printStackTrace();
        }
    }

    private void showInvoiceGeneratedDialog(ServiceRequestInvoiceDTO serviceRequestInvoiceDTO) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
                builder.setTitle(getResources().getString(R.string.invoice_generated));
                builder.setMessage(getResources().getString(R.string.invoice_generated_successfully));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.popBackStack(SearchSRNFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });

    }

    private void showProgress(final boolean show) {

        if(show){
            ((BaseFragmentActivity)getActivity()).showProgressDialog(getActivity(), "");
        }else{
            ((BaseFragmentActivity)getActivity()).dismissProgressDialog();
        }

        /*getActivity().runOnUiThread(new Runnable() {
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
        });*/
    }

    public void setServiceRequestDTO(ServiceRequestDTO serviceRequestDTO) {
        this.serviceRequestDTO = serviceRequestDTO;
    }

    public void setAmounts(double totalAmount, double discountAmount, double netPayableAmount, double additionalLabourCharge, double taxAmount) {
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.netPayableAmount = netPayableAmount;
        this.additionalLabourCharge = additionalLabourCharge;
        this.taxAmount = taxAmount;
    }

    public void setPartDetails(List<ServicePartDTO> partDetails) {
        this.partDetails = partDetails;
    }

    public void setModeOfPayment(Integer modeOfPayment) {
        this.selectedModeOfPayment = modeOfPayment;
    }
}

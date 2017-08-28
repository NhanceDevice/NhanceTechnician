package com.nhance.technician.ui.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.model.ServicePartDTO;
import com.nhance.technician.model.ServiceRequestDTO;
import com.nhance.technician.ui.TechOperationsActivity;
import com.nhance.technician.ui.custom.adapter.PartDetailsAdapter;
import com.nhance.technician.util.Util;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by afsarhussain on 10/07/17.
 */

public class GenerateInvoiceFragment extends Fragment implements ApplicationConstants {

    public static final String TAG = GenerateInvoiceFragment.class.getName();
    private View mProgressView;
    AutoCompleteTextView discountAmountACTV, additionalLabourChargeACTV;
    AppCompatTextView serviceReqNoACTV, customerNameACTV, customerMobNoACTV, installationChargesACTV, serviceReqChargesHeaderACTV,
            totalAmountACTV, netPayableAmountACTV, discountAmountCurrencySymbolACTV, additionalLabourChargeCurrencySymbolACTV, taxNameACTV, taxAmountACTV, taxAmountCurrencySymbolACTV;
    LinearLayout partsInstalledLL, modeOfPaymentRGLL;
    private RadioGroup newPartsInstalledRG, modeOfPaymentRG;
    LinearLayout partsDetailsContainerLL;
    RecyclerView partDetailsContainerRV;
    double totalAmount, netPayableAmount, discountAmount, additionalLabourCharge, taxAmount = 0D;
    private ImageButton addPartRowButton;
    private PartDetailsAdapter partDetailsAdapter;
    private List<ServicePartDTO> selectedPartList = null;
    private ServiceRequestDTO serviceRequestDTO;
    private Integer selectedModeOfPayment = MODE_OF_PAYMENT_ONLINE;

    public void setServiceRequestDTO(ServiceRequestDTO serviceRequestDTO) {
        this.serviceRequestDTO = serviceRequestDTO;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_generate_invoice, container, false);

        mProgressView = rootView.findViewById(R.id.fetch_progress);
        serviceReqNoACTV = (AppCompatTextView) rootView.findViewById(R.id.ser_req_no_tv);
        customerNameACTV = (AppCompatTextView) rootView.findViewById(R.id.cust_name_tv);
        customerMobNoACTV = (AppCompatTextView) rootView.findViewById(R.id.cust_mob_no_tv);
        serviceReqChargesHeaderACTV = (AppCompatTextView) rootView.findViewById(R.id.ser_req_charges_header);
        installationChargesACTV = (AppCompatTextView) rootView.findViewById(R.id.inst_charges_tv);
        totalAmountACTV = (AppCompatTextView) rootView.findViewById(R.id.total_amount_tv);
        discountAmountCurrencySymbolACTV = (AppCompatTextView) rootView.findViewById(R.id.discount_amount_currency_symbol_tv);
        discountAmountACTV = (AutoCompleteTextView) rootView.findViewById(R.id.discount_amount_tv);
        additionalLabourChargeCurrencySymbolACTV = (AppCompatTextView) rootView.findViewById(R.id.additional_labour_charge_currency_symbol_tv);
        additionalLabourChargeACTV = (AutoCompleteTextView) rootView.findViewById(R.id.additional_labour_charge_tv);
        taxNameACTV = (AppCompatTextView) rootView.findViewById(R.id.tax_name_tv);
        taxAmountCurrencySymbolACTV = (AppCompatTextView) rootView.findViewById(R.id.tax_amount_currency_symbol_tv);
        taxAmountACTV = (AppCompatTextView) rootView.findViewById(R.id.tax_amount_tv);
        addPartRowButton = (ImageButton) rootView.findViewById(R.id.part_row_add_button);
        addPartRowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = partDetailsAdapter.setRowIndex();
                partDetailsAdapter.addItemToPartDetailsMap(position);
                //partDetailsAdapter.notifyItemInserted(position);
                //partDetailsContainerRV.scrollToPosition(position);
                partDetailsAdapter.notifyDataSetChanged();
            }
        });
        serviceReqChargesHeaderACTV.setText(String.format(getResources().getString(R.string.instllation_charges), serviceRequestDTO.getServiceRequestSubject()));
        discountAmountCurrencySymbolACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))));
        additionalLabourChargeCurrencySymbolACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))));
        taxAmountCurrencySymbolACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))));
        discountAmountACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String discountAmountStr = s.toString();
                if (discountAmountStr != null && discountAmountStr.length() > 0 && discountAmountStr.matches("\\d+")) {
                    discountAmount = Double.parseDouble(discountAmountStr);
                } else {
                    discountAmount = 0;
                }
                double tempAmount = totalAmount - discountAmount;
                if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
                    int taxPercentage = serviceRequestDTO.getTaxPercentage();
                    taxAmount = tempAmount * ((float) taxPercentage / 100);
                    netPayableAmount = tempAmount + taxAmount;
                    taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));
                } else {
                    netPayableAmount = tempAmount;
                }
                if (netPayableAmount < 0) {
                    netPayableAmount = 0;
                }
                netPayableAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(netPayableAmount));

            }
        });
        additionalLabourChargeACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String additionalLabourChargeAmountStr = s.toString();
                if (additionalLabourChargeAmountStr != null && additionalLabourChargeAmountStr.length() > 0 && additionalLabourChargeAmountStr.matches("\\d+")) {
                    totalAmount = totalAmount - additionalLabourCharge;
                    additionalLabourCharge = Double.parseDouble(additionalLabourChargeAmountStr);
                    totalAmount = totalAmount + additionalLabourCharge;
                    if (totalAmount < 0) {
                        totalAmount = 0;
                    }
                    double tempAmount = totalAmount - discountAmount;
                    if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
                        int taxPercentage = serviceRequestDTO.getTaxPercentage();
                        taxAmount = tempAmount * ((float) taxPercentage / 100);
                        netPayableAmount = tempAmount + taxAmount;
                        taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));
                    } else {
                        netPayableAmount = tempAmount;
                    }
                    totalAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(totalAmount));

                    if (netPayableAmount < 0) {
                        netPayableAmount = 0;
                    }
                    netPayableAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(netPayableAmount));
                } else {
                    totalAmount = totalAmount - additionalLabourCharge;
                    additionalLabourCharge = 0;
                    if (totalAmount < 0) {
                        totalAmount = 0;
                    }
                    double tempAmount = totalAmount - discountAmount;
                    if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
                        int taxPercentage = serviceRequestDTO.getTaxPercentage();
                        taxAmount = tempAmount * ((float) taxPercentage / 100);
                        netPayableAmount = totalAmount + taxAmount;
                        taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));
                    } else {
                        netPayableAmount = tempAmount;
                    }
                    totalAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(totalAmount));

                    if (netPayableAmount < 0) {
                        netPayableAmount = 0;
                    }
                    netPayableAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(netPayableAmount));
                }
            }
        });
        netPayableAmountACTV = (AppCompatTextView) rootView.findViewById(R.id.net_payable_amount_tv);
        partsInstalledLL = (LinearLayout) rootView.findViewById(R.id.part_installed_rg_ll);
        modeOfPaymentRGLL = (LinearLayout) rootView.findViewById(R.id.mode_of_payment_rg_ll);
        partsDetailsContainerLL = (LinearLayout) rootView.findViewById(R.id.part_details_container_ll);
        partDetailsContainerRV = (RecyclerView) rootView.findViewById(R.id.part_details_container_rv);
        if (partDetailsContainerRV.getChildCount() > 0) {
            partDetailsContainerRV.removeAllViews();
        }
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
                totalAmount = serviceRequestDTO.getAmount();
                double tempAmount = 0D;
                if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
                    int taxPercentage = serviceRequestDTO.getTaxPercentage();
                    taxAmount = totalAmount * ((float) taxPercentage / 100);
                    tempAmount = totalAmount + taxAmount;
                    taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));

                } else {
                    tempAmount = totalAmount;
                }
                totalAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(totalAmount));
                netPayableAmount = tempAmount;
                netPayableAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(netPayableAmount));
            }
            if (serviceRequestDTO.getModeOfPayment() != null) {
                if (serviceRequestDTO.getModeOfPayment() == MODE_OF_PAYMENT_CASH) {
                    selectedModeOfPayment = MODE_OF_PAYMENT_CASH;
                    modeOfPaymentRGLL.setVisibility(View.GONE);
                } else if (serviceRequestDTO.getModeOfPayment() == MODE_OF_PAYMENT_ONLINE) {
                    selectedModeOfPayment = MODE_OF_PAYMENT_ONLINE;
                    modeOfPaymentRGLL.setVisibility(View.GONE);
                } else if (serviceRequestDTO.getModeOfPayment() == MODE_OF_PAYMENT_BOTH_CASH_ONLINE) {
                    modeOfPaymentRGLL.setVisibility(View.VISIBLE);
                    modeOfPaymentRG = (RadioGroup) rootView.findViewById(R.id.mode_of_payment_rg);
                    modeOfPaymentRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                            switch (checkedId) {
                                case R.id.mode_of_payment_online_rb:
                                    selectedModeOfPayment = MODE_OF_PAYMENT_ONLINE;
                                    break;
                                case R.id.mode_of_payment_cash_rb:
                                    selectedModeOfPayment = MODE_OF_PAYMENT_CASH;
                                    break;
                            }
                        }
                    });
                }

            } else {
                modeOfPaymentRGLL.setVisibility(View.GONE);
            }
            if (serviceRequestDTO.getParts() != null && serviceRequestDTO.getParts().size() > 0) {
                partsInstalledLL.setVisibility(View.VISIBLE);
                newPartsInstalledRG = (RadioGroup) partsInstalledLL.findViewById(R.id.parts_installed_rg);
                newPartsInstalledRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (checkedId) {
                            case R.id.parts_installed_yes_rb: {
                                partsDetailsContainerLL.setVisibility(View.VISIBLE);
                                addPartRowButton.setVisibility(View.VISIBLE);
                                partDetailsContainerRV.setLayoutManager(new LinearLayoutManager(getActivity()));
                                List<Integer> partRowId = new ArrayList<Integer>();
                                partRowId.add(0);
                                partDetailsAdapter = new PartDetailsAdapter(getActivity(), serviceRequestDTO.getParts(), partRowId, serviceRequestDTO.getCurrencyCode());
                                partDetailsContainerRV.setAdapter(partDetailsAdapter);
                                partDetailsAdapter.setPartAmountChangeListener(new PartDetailsAdapter.PartAmountChangeListener() {
                                    @Override
                                    public void onPartAmountChanged(List<ServicePartDTO> partDetailsMap) {
                                        double partTotalAmount = 0;
                                        if (partDetailsMap != null) {
                                            selectedPartList = new ArrayList<ServicePartDTO>();
                                            for (ServicePartDTO partDetails : partDetailsMap) {
//                                                ServicePartDTO partDetails = entry.getValue();
                                                selectedPartList.add(partDetails);
                                                partTotalAmount += partDetails.getAmount();
                                            }
                                            totalAmount = ((serviceRequestDTO.getAmount() != null) ? serviceRequestDTO.getAmount() : 0) + partTotalAmount;
                                            totalAmount = totalAmount + additionalLabourCharge;
                                            double tempAmount = totalAmount - discountAmount;
                                            if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
                                                int taxPercentage = serviceRequestDTO.getTaxPercentage();
                                                taxAmount = tempAmount * ((float) taxPercentage / 100);
                                                netPayableAmount = tempAmount + taxAmount;
                                                taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));
                                            } else {
                                                netPayableAmount = tempAmount;
                                            }

                                            totalAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(totalAmount));
                                            if (netPayableAmount < 0) {
                                                netPayableAmount = 0;
                                            }
                                            netPayableAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(netPayableAmount));
                                        }
                                    }
                                });
                                break;
                            }
                            case R.id.parts_installed_no_rb: {
                                if (partDetailsAdapter != null) {
                                    partDetailsAdapter.clearAllItems();
                                    partDetailsAdapter = null;
                                    /*if (partDetailsContainerRV != null) {
                                        partDetailsContainerRV.setAdapter(null);
                                    }*/
                                }
                                partsDetailsContainerLL.setVisibility(View.GONE);
                                addPartRowButton.setVisibility(View.GONE);
                                selectedPartList = null;
                                totalAmount = (serviceRequestDTO.getAmount() != null) ? serviceRequestDTO.getAmount() : 0;
                                if (additionalLabourCharge < 0) {
                                    additionalLabourCharge = 0;
                                }
                                totalAmount = totalAmount + additionalLabourCharge;
                                totalAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(totalAmount));
                                double tempAmount = totalAmount - discountAmount;
                                if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
                                    int taxPercentage = serviceRequestDTO.getTaxPercentage();
                                    taxAmount = tempAmount * ((float) taxPercentage / 100);
                                    netPayableAmount = tempAmount + taxAmount;
                                    taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));
                                } else {
                                    netPayableAmount = tempAmount;
                                }
                                if (netPayableAmount < 0) {
                                    netPayableAmount = 0;
                                }
                                netPayableAmountACTV.setText(new String(Character.toChars(Integer.parseInt(serviceRequestDTO.getCurrencyCode(), 16))) + " " + Util.getFormattedAmount(netPayableAmount));
                                break;
                            }
                        }
                    }
                });
            } else {
                partsInstalledLL.setVisibility(View.GONE);
            }
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gen_invoice, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_preview) {
            if (netPayableAmount > 0)
                makeViewsReadyForPreview();
            else {
                Snackbar snackbar = Snackbar.make(((TechOperationsActivity) getActivity()).getCoordinatorLayout(), "Invoice can't be generated for Zero(0) amount.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeViewsReadyForPreview() {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        InvoicePreviewFragment invoicePreviewFragment = new InvoicePreviewFragment();
        invoicePreviewFragment.setServiceRequestDTO(serviceRequestDTO);
        invoicePreviewFragment.setAmounts(totalAmount, discountAmount, netPayableAmount, additionalLabourCharge, taxAmount);
        invoicePreviewFragment.setPartDetails(selectedPartList);
        invoicePreviewFragment.setModeOfPayment(selectedModeOfPayment);
        trans.add(R.id.root_frame, invoicePreviewFragment, RootFragment.FRAGMENT_TAG);
        trans.hide(GenerateInvoiceFragment.this);
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(TAG);
        trans.commit();
    }
}


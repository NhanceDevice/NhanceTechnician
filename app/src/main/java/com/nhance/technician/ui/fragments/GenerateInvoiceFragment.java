package com.nhance.technician.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.model.newapis.ServiceRequestInvoiceComponentModel;
import com.nhance.technician.model.newapis.ServiceRequestModel;
import com.nhance.technician.ui.BaseFragmentActivity;
import com.nhance.technician.util.TypefaceUtils.TypefaceHelper;
import com.nhance.technician.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by afsarhussain on 10/07/17.
 */

public class GenerateInvoiceFragment extends Fragment implements ApplicationConstants {

    public static final String TAG = GenerateInvoiceFragment.class.getName();
    private View mProgressView;
    AutoCompleteTextView discountAmountACTV, additionalLabourChargeACTV;
    AppCompatTextView serviceReqLabelTV, serviceReqNoACTV, customerNameACTV, customerMobNoACTV, installationChargesACTV, serviceReqChargesHeaderACTV,
            totalAmountACTV, netPayableAmountLabelTV, netPayableAmountACTV, discountAmountCurrencySymbolACTV, additionalLabourChargeCurrencySymbolACTV,
            taxNameACTV, taxAmountACTV, taxAmountCurrencySymbolACTV;
    LinearLayout partsInstalledLL, modeOfPaymentRGLL;
    private RadioGroup newPartsInstalledRG, modeOfPaymentRG;
    private LinearLayout partsDetailsMainContainerLL, partDetailsContainerll;
    double cumulativeAmountPaidAgainstParts, netPayableAmount, discountAmount, additionalLabourCharge, taxAmount = 0D;
    private ImageButton addPartRowButton;
    private List<ServiceRequestInvoiceComponentModel> selectedPartList = null;
    private ServiceRequestModel serviceRequestDTO;
    private Integer selectedModeOfPayment = MODE_OF_PAYMENT_CASH;

    public void setServiceRequest(ServiceRequestModel serviceRequestModel) {
        this.serviceRequestDTO = serviceRequestModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void hideSoftKeyPad() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getActivity().getWindow().getCurrentFocus() != null) {
                    inputManager.hideSoftInputFromWindow(getActivity().getWindow().getCurrentFocus().getWindowToken(), 0);
                    getActivity().getWindow().getCurrentFocus().clearFocus();
                }
            }
        });
    }

    public void showSoftKeyPad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }, 200);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_generate_invoice, container, false);

        mProgressView = rootView.findViewById(R.id.fetch_progress);
        serviceReqLabelTV = (AppCompatTextView) rootView.findViewById(R.id.serv_req_label_tv);
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
                hideSoftKeyPad();
                addPartRowToParentLayout();
            }
        });
        serviceReqChargesHeaderACTV.setText(String.format(getResources().getString(R.string.instllation_charges), serviceRequestDTO.getSubject()));
        discountAmountCurrencySymbolACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()));
        additionalLabourChargeCurrencySymbolACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()));
        taxAmountCurrencySymbolACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()));
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
                double tempAmount = cumulativeAmountPaidAgainstParts - discountAmount;
                if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
                    int taxPercentage = serviceRequestDTO.getTaxPercentage().intValue();
                    taxAmount = tempAmount * ((float) taxPercentage / 100);
                    netPayableAmount = tempAmount + taxAmount;
                    taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));
                } else {
                    netPayableAmount = tempAmount;
                }
                if (netPayableAmount < 0) {
                    netPayableAmount = 0;
                }
                netPayableAmountACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(netPayableAmount));

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
                    cumulativeAmountPaidAgainstParts = cumulativeAmountPaidAgainstParts - additionalLabourCharge;
                    additionalLabourCharge = Double.parseDouble(additionalLabourChargeAmountStr);
                    cumulativeAmountPaidAgainstParts = cumulativeAmountPaidAgainstParts + additionalLabourCharge;
                    if (cumulativeAmountPaidAgainstParts < 0) {
                        cumulativeAmountPaidAgainstParts = 0;
                    }
                    double tempAmount = cumulativeAmountPaidAgainstParts - discountAmount;
                    if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
                        int taxPercentage = serviceRequestDTO.getTaxPercentage().intValue();
                        taxAmount = tempAmount * ((float) taxPercentage / 100);
                        netPayableAmount = tempAmount + taxAmount;
                        taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));
                    } else {
                        netPayableAmount = tempAmount;
                    }
                    totalAmountACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(cumulativeAmountPaidAgainstParts));

                    if (netPayableAmount < 0) {
                        netPayableAmount = 0;
                    }
                    netPayableAmountACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(netPayableAmount));
                } else {
                    cumulativeAmountPaidAgainstParts = cumulativeAmountPaidAgainstParts - additionalLabourCharge;
                    additionalLabourCharge = 0;
                    if (cumulativeAmountPaidAgainstParts < 0) {
                        cumulativeAmountPaidAgainstParts = 0;
                    }
                    double tempAmount = cumulativeAmountPaidAgainstParts - discountAmount;
                    if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
                        int taxPercentage = serviceRequestDTO.getTaxPercentage().intValue();
                        taxAmount = tempAmount * ((float) taxPercentage / 100);
                        netPayableAmount = cumulativeAmountPaidAgainstParts + taxAmount;
                        taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));
                    } else {
                        netPayableAmount = tempAmount;
                    }
                    totalAmountACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(cumulativeAmountPaidAgainstParts));

                    if (netPayableAmount < 0) {
                        netPayableAmount = 0;
                    }
                    netPayableAmountACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(netPayableAmount));
                }
            }
        });
        netPayableAmountLabelTV = (AppCompatTextView) rootView.findViewById(R.id.net_payable_amount_label_tv);
        netPayableAmountACTV = (AppCompatTextView) rootView.findViewById(R.id.net_payable_amount_tv);
        partsInstalledLL = (LinearLayout) rootView.findViewById(R.id.part_installed_rg_ll);
        modeOfPaymentRGLL = (LinearLayout) rootView.findViewById(R.id.mode_of_payment_rg_ll);
        partsDetailsMainContainerLL = (LinearLayout) rootView.findViewById(R.id.part_details_container_ll);
        partDetailsContainerll = (LinearLayout) rootView.findViewById(R.id.part_details_container_rv);

        if (serviceRequestDTO != null) {
            if (serviceRequestDTO.getTaxName() != null) {
                taxNameACTV.setText(serviceRequestDTO.getTaxName());
            }
            if (serviceRequestDTO.getSrn() != null) {
                serviceReqNoACTV.setText(serviceRequestDTO.getSrn());
            }
            if (serviceRequestDTO.getUserName() != null) {
                customerNameACTV.setText(serviceRequestDTO.getUserName());
            }
            if (serviceRequestDTO.getContact() != null && serviceRequestDTO.getContact().getMobile() != null) {

                StringBuilder stringBuilder = new StringBuilder();

                if(serviceRequestDTO.getContact().getIsdCode() != null)
                    stringBuilder.append("+"+serviceRequestDTO.getContact().getIsdCode());

                stringBuilder.append(serviceRequestDTO.getContact().getMobile());

                customerMobNoACTV.setText(stringBuilder.toString());
            }
            if (serviceRequestDTO.getAmount() != null) {
                installationChargesACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(serviceRequestDTO.getAmount()));
                cumulativeAmountPaidAgainstParts = serviceRequestDTO.getAmount();
                double tempAmount = 0D;
                if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
                    int taxPercentage = serviceRequestDTO.getTaxPercentage().intValue();
                    taxAmount = cumulativeAmountPaidAgainstParts * ((float) taxPercentage / 100);
                    tempAmount = cumulativeAmountPaidAgainstParts + taxAmount;
                    taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));

                } else {
                    tempAmount = cumulativeAmountPaidAgainstParts;
                }
                totalAmountACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(cumulativeAmountPaidAgainstParts));
                netPayableAmount = tempAmount;
                netPayableAmountACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(netPayableAmount));
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

                                showSoftKeyPad();
                                partsDetailsMainContainerLL.setVisibility(View.VISIBLE);
                                addPartRowButton.setVisibility(View.VISIBLE);
                                addPartRowToParentLayout();

                                break;
                            }
                            case R.id.parts_installed_no_rb: {

                                clearAllPartsRowsFromParentLayout();

                                partsDetailsMainContainerLL.setVisibility(View.GONE);
                                addPartRowButton.setVisibility(View.GONE);
                                selectedPartList = null;
                                cumulativeAmountPaidAgainstParts = (serviceRequestDTO.getAmount() != null) ? serviceRequestDTO.getAmount() : 0;
                                if (additionalLabourCharge < 0) {
                                    additionalLabourCharge = 0;
                                }
                                cumulativeAmountPaidAgainstParts = cumulativeAmountPaidAgainstParts + additionalLabourCharge;
                                totalAmountACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(cumulativeAmountPaidAgainstParts));
                                double tempAmount = cumulativeAmountPaidAgainstParts - discountAmount;
                                if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
                                    int taxPercentage = serviceRequestDTO.getTaxPercentage().intValue();
                                    taxAmount = tempAmount * ((float) taxPercentage / 100);
                                    netPayableAmount = tempAmount + taxAmount;
                                    taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));
                                } else {
                                    netPayableAmount = tempAmount;
                                }
                                if (netPayableAmount < 0) {
                                    netPayableAmount = 0;
                                }
                                netPayableAmountACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(netPayableAmount));
                                break;
                            }
                        }
                    }
                });
            } else {
                partsInstalledLL.setVisibility(View.GONE);
            }
        }
        TypefaceHelper.getInstance().setTypeface(rootView, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
        TypefaceHelper.getInstance().setTypeface(serviceReqLabelTV, TypefaceHelper.getFont(TypefaceHelper.FONT.BOLD));
        TypefaceHelper.getInstance().setTypeface(netPayableAmountLabelTV, TypefaceHelper.getFont(TypefaceHelper.FONT.BOLD));
        return rootView;
    }

    private void clearAllPartsRowsFromParentLayout() {
        if(partDetailsContainerll != null && partDetailsContainerll.getChildCount() > 0)
            partDetailsContainerll.removeAllViews();
        totalPartsAdded = -1;
        cumulativeAmountPaidAgainstParts = 0D;
        if(selectedPartsBasedOnTag != null && selectedPartsBasedOnTag.size() > 0)
            selectedPartsBasedOnTag.clear();
        if(selectedPartsBasedOnPartName != null && selectedPartsBasedOnPartName.size() > 0)
            selectedPartsBasedOnPartName.clear();
    }

    private int totalPartsAdded = -1;

    private Integer[] digits = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private String currencyCode;
    private List<String> partNameList;
    private Map<String, ServiceRequestInvoiceComponentModel> servicePartsMap;
    private Map<String, ServiceRequestInvoiceComponentModel> selectedPartsBasedOnTag;
    private Map<String, ServiceRequestInvoiceComponentModel> selectedPartsBasedOnPartName;

    private void prepareRequiredForParts(){

        selectedPartsBasedOnTag = new HashMap<>();
        selectedPartsBasedOnPartName = new HashMap<>();
        partNameList = new ArrayList<>();
        servicePartsMap = new HashMap<>();
        List<ServiceRequestInvoiceComponentModel> servicePartDTOList = serviceRequestDTO.getParts();
        currencyCode = serviceRequestDTO.getCurrencyCode();

        if (servicePartDTOList != null && servicePartDTOList.size() > 0) {
            for (int index = 0; index < servicePartDTOList.size(); index++) {
                partNameList.add(servicePartDTOList.get(index).getPartName());
                servicePartsMap.put(servicePartDTOList.get(index).getPartName(), servicePartDTOList.get(index));
            }
        }
    }

    private void addPartRowToParentLayout() {

        if(!isLastRowDataFilled()){
            ((BaseFragmentActivity)getActivity()).showAlert("Part not selected or doesn't exists.");
            return;
        }

        if(partNameList == null || servicePartsMap == null)
            prepareRequiredForParts();

        final ArrayAdapter<String> partNameAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spn, partNameList);
        final ArrayAdapter<Integer> digitsAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, digits);
        digitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.content_part_details, partDetailsContainerll, false);

        totalPartsAdded++;
        final int currentRowIndex = totalPartsAdded;
        final String rowTag = String.valueOf(currentRowIndex);
        view.setTag(rowTag);

        final AppCompatAutoCompleteTextView partNameACTV = (AppCompatAutoCompleteTextView) view.findViewById(R.id.partname_actv);
        final AppCompatSpinner quantityACTV = (AppCompatSpinner) view.findViewById(R.id.quantity_actv);

        final TextInputEditText amountACTV = (TextInputEditText) view.findViewById(R.id.amount_actv);

        partNameACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                hideSoftKeyPad();
                String selectedPart = parent.getAdapter().getItem(pos).toString();
                ServiceRequestInvoiceComponentModel servicePartDTO = servicePartsMap.get(selectedPart);
                ServiceRequestInvoiceComponentModel oldServicePartDTO = null;
                if(selectedPartsBasedOnTag.containsKey(rowTag)){
                    oldServicePartDTO = selectedPartsBasedOnTag.get(rowTag);
                }

                if(oldServicePartDTO != null && oldServicePartDTO.getPartName().equalsIgnoreCase(servicePartDTO.getPartName())){
                    //TODO: Ignore selection and reset the previous selection using oldServicePartDTO.
                    partNameACTV.setText(servicePartDTO.getPartName());

                    ((BaseFragmentActivity)getActivity()).showAlert("Same part is selected.");

                }else if(selectedPartsBasedOnPartName.containsKey(selectedPart)){
                    //TODO:If item is already selected in some other row then check for the old data of same if exists other wise set nothing.
                    if(oldServicePartDTO != null){
                        partNameACTV.setText(oldServicePartDTO.getPartName());
                    }else
                        partNameACTV.setText("");

                    ((BaseFragmentActivity)getActivity()).showAlert("This part is already selected.");
                }
                else{
                    if(oldServicePartDTO != null){
                        selectedPartsBasedOnPartName.remove(oldServicePartDTO.getPartName());
                    }

                    servicePartDTO.setQuantity(1);
                    servicePartDTO.setCalculatedAmount(servicePartDTO.getAmount());

                    selectedPartsBasedOnTag.put(rowTag, servicePartDTO);
                    selectedPartsBasedOnPartName.put(selectedPart, servicePartDTO);

                    amountACTV.setText(Util.getFormattedAmount(servicePartDTO.getAmount()));
                    quantityACTV.setSelection(1);
                }
            }
        });

        quantityACTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Integer quantity = digits[pos];

                ServiceRequestInvoiceComponentModel oldServicePartDTO = null;
                if(selectedPartsBasedOnTag.containsKey(rowTag)){
                    oldServicePartDTO = selectedPartsBasedOnTag.get(rowTag);
                }
                if(oldServicePartDTO != null){
                    oldServicePartDTO.setQuantity(quantity.intValue());

                    double totalAmount = oldServicePartDTO.getAmount()*quantity.intValue();

                    oldServicePartDTO.setCalculatedAmount(totalAmount);

                    selectedPartsBasedOnTag.put(rowTag, oldServicePartDTO);
                    selectedPartsBasedOnPartName.put(oldServicePartDTO.getPartName(), oldServicePartDTO);

                    amountACTV.setText(Util.getFormattedAmount(totalAmount));
                    partNameACTV.setText(oldServicePartDTO.getPartName());

                    calculateTotalAmount();
                }else{
                    if(quantity.intValue() > 0)
                        ((BaseFragmentActivity)getActivity()).showAlert("Please choose parts.");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        amountACTV.setKeyListener(null);

        final TextView currenctCodeTV = (TextView) view.findViewById(R.id.currencycode_tv);
        currenctCodeTV.setText(new String(Character.toChars(Integer.parseInt(currencyCode, 16))));

        final ImageButton deleteButton = (ImageButton) view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO:Get selected ServicePartDTO and service part name, total no of parts, amount.

                String tag = view.getTag().toString();
//                int selectedIndexToRemove = Integer.parseInt(tag);
                if(selectedPartsBasedOnTag.containsKey(tag)){
                    ServiceRequestInvoiceComponentModel oldServicePartDTO = selectedPartsBasedOnTag.get(tag);
                    selectedPartsBasedOnPartName.remove(oldServicePartDTO.getPartName());
                    selectedPartsBasedOnTag.remove(tag);
                    calculateTotalAmount();
                }
                partDetailsContainerll.removeView(view);
            }
        });

        if(totalPartsAdded == 0){
            deleteButton.setVisibility(View.INVISIBLE);
        }

        quantityACTV.setAdapter(digitsAdapter);
        partNameACTV.setAdapter(partNameAdapter);

        partDetailsContainerll.addView(view/*, currentRowIndex*/);
    }

    private boolean isLastRowDataFilled() {

        boolean status = false;

        if(partDetailsContainerll != null && partDetailsContainerll.getChildCount() > 0){

            View view = partDetailsContainerll.getChildAt(partDetailsContainerll.getChildCount()-1);
            if(view != null){
                String rowTag = view.getTag().toString();
                if(rowTag != null && selectedPartsBasedOnTag != null && selectedPartsBasedOnTag.containsKey(rowTag)){
                    status = true;
                }else
                    status = false;
            }

        }else
            status = true;

        return status;
    }

    private void calculateTotalAmount() {

        cumulativeAmountPaidAgainstParts = 0D;

        if(selectedPartsBasedOnTag != null && selectedPartsBasedOnTag.size() > 0){

            for (Map.Entry<String, ServiceRequestInvoiceComponentModel> entry : selectedPartsBasedOnTag.entrySet()) {
                ServiceRequestInvoiceComponentModel servicePartDTO = entry.getValue();
                cumulativeAmountPaidAgainstParts += servicePartDTO.getCalculatedAmount();
            }
        }
        cumulativeAmountPaidAgainstParts+=serviceRequestDTO.getAmount();
        cumulativeAmountPaidAgainstParts+=additionalLabourCharge;

        totalAmountACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(cumulativeAmountPaidAgainstParts));

        double tempAmount = 0D;
        if (serviceRequestDTO.getTaxPercentage() != null && serviceRequestDTO.getTaxPercentage() > 0) {
            int taxPercentage = serviceRequestDTO.getTaxPercentage().intValue();
            taxAmount = (cumulativeAmountPaidAgainstParts-discountAmount) * ((float) taxPercentage / 100);
            tempAmount = cumulativeAmountPaidAgainstParts + taxAmount-discountAmount;
            taxAmountACTV.setText(Util.getFormattedAmount(taxAmount));

        } else {
            tempAmount = cumulativeAmountPaidAgainstParts-discountAmount;
        }
        netPayableAmount = tempAmount;
        netPayableAmountACTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestDTO.getCurrencyCode()) + " " + Util.getFormattedAmount(netPayableAmount));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gen_invoice, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_preview) {
            if (netPayableAmount > 0){

                if(newPartsInstalledRG != null){
                    int checkedId = newPartsInstalledRG.getCheckedRadioButtonId();
                    if(checkedId > -1){
                        switch (checkedId) {
                            case R.id.parts_installed_yes_rb: {
                                if (selectedPartsBasedOnTag!=null && selectedPartsBasedOnTag.size() > 0) {
                                    boolean showErrorAlert = false;

                                    if(partDetailsContainerll != null && partDetailsContainerll.getChildCount() > 0){
                                        for(int i = 0; i < partDetailsContainerll.getChildCount(); i++){
                                            View childView = partDetailsContainerll.getChildAt(i);
                                            String tag = childView.getTag().toString();
                                            if(!selectedPartsBasedOnTag.containsKey(tag))
                                            {
                                                showErrorAlert = true;
                                                break;
                                            }
                                        }
                                    }
                                    if(!showErrorAlert)
                                        makeViewsReadyForPreview();
                                    else
                                        ((BaseFragmentActivity)getActivity()).showAlert("Please select a valid part name from the list.");
                                } else {
                                    ((BaseFragmentActivity)getActivity()).showAlert("Please select a valid part name from the list.");
                                }
                                break;
                            }
                            case R.id.parts_installed_no_rb: {
                                makeViewsReadyForPreview();
                                break;
                            }
                        }
                    } else {
                        ((BaseFragmentActivity)getActivity()).showAlert("Please choose new parts installed or not.");
                    }
                }else
                    makeViewsReadyForPreview();
            }else {
                ((BaseFragmentActivity)getActivity()).showAlert("Invoice can't be generated for Zero(0) amount.");
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeViewsReadyForPreview() {

        if(selectedPartList != null)
            selectedPartList.clear();

        if(selectedPartsBasedOnTag != null && selectedPartsBasedOnTag.size() > 0)
        {
            selectedPartList = new ArrayList<ServiceRequestInvoiceComponentModel>(selectedPartsBasedOnTag.values());
        }

        FragmentTransaction trans = getFragmentManager().beginTransaction();
        InvoicePreviewFragment invoicePreviewFragment = new InvoicePreviewFragment();
        invoicePreviewFragment.setServiceRequestDTO(serviceRequestDTO);
        invoicePreviewFragment.setAmounts(cumulativeAmountPaidAgainstParts, discountAmount, netPayableAmount, additionalLabourCharge, taxAmount);
        invoicePreviewFragment.setPartDetails(selectedPartList);
        invoicePreviewFragment.setModeOfPayment(selectedModeOfPayment);
        trans.add(R.id.root_frame, invoicePreviewFragment, RootFragment.FRAGMENT_TAG);
        trans.hide(GenerateInvoiceFragment.this);
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(TAG);
        trans.commit();
    }
}


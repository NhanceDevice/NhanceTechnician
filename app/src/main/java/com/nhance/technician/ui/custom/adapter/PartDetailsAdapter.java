package com.nhance.technician.ui.custom.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nhance.technician.R;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.ServicePartDTO;
import com.nhance.technician.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afsarhussain on 09/07/17.
 */

public class PartDetailsAdapter extends RecyclerView.Adapter<PartDetailsAdapter.PartViewHolder> {


    private List<Integer> rowIndex;
    private Context mContext;
    private List<ServicePartDTO> servicePartDTOList;
    private ArrayAdapter<String> partNameAdapter;
    private ArrayAdapter<Integer> digitsAdapter;
    private List<String> partNameList;
    private List<ServicePartDTO> partDetailsMap;
    private Integer[] digits = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    private String currencyCode;

    public PartDetailsAdapter(Context context, List<ServicePartDTO> servicePartDTOList, List<Integer> rowIndex, String currencyCode) {
        this.mContext = context;
        this.servicePartDTOList = servicePartDTOList;
        this.rowIndex = rowIndex;
        this.currencyCode = currencyCode;
        getPartNameList();

        partNameAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, partNameList);
        partNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        digitsAdapter = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_spinner_item, digits);
        digitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partDetailsMap = new ArrayList<>();
        addItemToPartDetailsMap(0);
    }

    @Override
    public PartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_part_details, parent, false);
        PartViewHolder partViewHolder = new PartViewHolder(view);
        return partViewHolder;
    }

    @Override
    public void onBindViewHolder(final PartViewHolder holder, final int position) {
        LOG.d("onBindViewHolder : AdapterPosition  : >>>> : ", holder.getAdapterPosition() + "");
        LOG.d("onBindViewHolder : Position         : >>>> : ", position + "");
        if (holder.getAdapterPosition() == 0) {
            holder.deleteButton.setVisibility(View.INVISIBLE);
        }
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOG.d("holder.deleteButton.setOnClickListener : AdapterPosition  : >>>> : ", holder.getAdapterPosition() + "");
                LOG.d("holder.deleteButton.setOnClickListener : Position         : >>>> : ", position + "");
                LOG.d("holder.deleteButton.setOnClickListener : rowIndex.size()  : >>>> : ", rowIndex.size() + "");
                LOG.d("holder.deleteButton.setOnClickListener : partDetailsMap  : >> partDetailsMap.size() : >> ", partDetailsMap.size() + " : 111111111 >> : " + partDetailsMap + "");
                if (rowIndex.size() > position) {
                    rowIndex.remove(position);
                    partDetailsMap.remove(position);
//                    notifyItemRemoved(position);
//                    notifyItemRangeChanged(position, rowIndex.size());
                    notifyDataSetChanged();
                    LOG.d("holder.deleteButton.setOnClickListener : partDetailsMap  : >> partDetailsMap.size() : >> ", partDetailsMap.size() + " : 222222222 >> : " + partDetailsMap + "");
                    mPartAmountChangeListener.onPartAmountChanged(partDetailsMap);
                } else {
                    partDetailsMap = new ArrayList<>();
                }
            }
        });
        holder.partnameACTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                LOG.d("holder.partnameACTV.setOnItemSelectedListener : pos  : >> ", pos + "");

                ServicePartDTO servicePartDTO = servicePartDTOList.get(pos);
                ServicePartDTO partDetails = null;
                LOG.d("holder.partnameACTV.setOnItemSelectedListener  : >> position : >> ", position + " : 3333333333 >> : partDetails : " + partDetails + "");
                partDetails = partDetailsMap.get(position);
                if (!partDetails.getPartName().equalsIgnoreCase(servicePartDTO.getPartName())) {
                    partDetails = new ServicePartDTO(servicePartDTO.getPartId(), servicePartDTO.getPartName(), servicePartDTO.getAmount(), digits[0]);
                    partDetailsMap.set(position, partDetails);
                    holder.quantityACTV.setSelection(0);
                    holder.amountACTV.setText(Util.getFormattedAmount(servicePartDTO.getAmount()));
                }
//                    partDetailsMap.set(position, partDetails);

                holder.itemView.setTag(position);
                LOG.d("holder.partnameACTV.setOnItemSelectedListener : partDetailsMap  : >> partDetailsMap.size() : >> ", partDetailsMap.size() + " : 4444444444 >> : " + partDetailsMap + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.quantityACTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Integer digit = digits[pos];
                ServicePartDTO partDetails = partDetailsMap.get(position);
                LOG.d("holder.quantityACTV.setOnItemSelectedListener  : >> position : >> ", position + " : 5555555555 >> : partDetails : " + partDetails + "");
                double partUnitPrice = 0;
                if (partDetails != null) {
                    for (int index = 0; index < servicePartDTOList.size(); index++) {
                        if (partDetails.getPartName().equalsIgnoreCase(servicePartDTOList.get(index).getPartName())) {
                            partUnitPrice = servicePartDTOList.get(index).getAmount();
                            break;
                        }
                    }
                }
                LOG.d("holder.quantityACTV.setOnItemSelectedListener : partDetailsMap  : >> position : >> ", position + " : 6666666666 >> : " + partDetails + "");
                double amount = partUnitPrice * digit;
                partDetails.setQuantity(digit);
                partDetails.setAmount(amount);
                partDetailsMap.set(position, partDetails);
                holder.amountACTV.setText(Util.getFormattedAmount(amount));
                LOG.d("holder.quantityACTV.setOnItemSelectedListener : partDetailsMap  : >> partDetailsMap.size() : >> ", partDetailsMap.size() + " : 7777777777 >> : " + partDetailsMap + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.currenctCodeTV.setText(new String(Character.toChars(Integer.parseInt(currencyCode, 16))));
        holder.amountACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPartAmountChangeListener.onPartAmountChanged(partDetailsMap);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (partDetailsMap != null)
                    LOG.d("values assigned while redrawing : partDetailsMap  : >> partDetailsMap.size() : >> ", partDetailsMap.size() + " : 8888888888 >> :  " + partDetailsMap + "");
                ServicePartDTO partDetailsDto = (partDetailsMap != null && partDetailsMap.size() > position) ? partDetailsMap.get(position) : null;
                if (partDetailsDto != null) {
                    String partName = partDetailsDto.getPartName();
                    if (partName != null) {
                        int selectedPartNameIndex = -1;
                        for (int index = 0; index < partNameList.size(); index++) {
                            if (partName.equalsIgnoreCase(partNameList.get(index))) {
                                selectedPartNameIndex = index;
                                break;
                            }
                        }
                        if (selectedPartNameIndex != -1) {
                            holder.partnameACTV.setSelection(selectedPartNameIndex);
                        }
                    }
                    Integer quantity = partDetailsDto.getQuantity();
                    if (quantity != null && quantity > 0) {
                        int selectedQuantityIndex = -1;
                        for (int quantityIndex = 0; quantityIndex < digits.length; quantityIndex++) {
                            if (quantity == digits[quantityIndex]) {
                                selectedQuantityIndex = quantityIndex;
                                break;
                            }
                        }
                        if (selectedQuantityIndex != -1) {
                            holder.quantityACTV.setSelection(selectedQuantityIndex);
                        }
                    }
                    Double amount = partDetailsDto.getAmount();
                    if (amount != null && amount > 0) {
                        holder.amountACTV.setText(Util.getFormattedAmount(amount));
                    }
                }
            }
        }, 100);

    }

    @Override
    public int getItemCount() {
        return rowIndex.size();
    }

    public List<Integer> getRowIndex() {
        return rowIndex;
    }

    public int setRowIndex() {
        int rowIndx = getRowIndex().size();
        rowIndex.add(rowIndx);
        return rowIndx;
    }

    public void addItemToPartDetailsMap(int position) {
        ServicePartDTO servicePartDTO = servicePartDTOList.get(0);
        ServicePartDTO partDetails = new ServicePartDTO(servicePartDTO.getPartId(), servicePartDTO.getPartName(), servicePartDTO.getAmount(), digits[0]);
        if (partDetailsMap.size() > position) {
            partDetailsMap.set(position, partDetails);
        } else {
            partDetailsMap.add(partDetails);
        }
    }

    public void clearAllItems() {
        rowIndex.clear();
        partDetailsMap.clear();
    }

    public class PartViewHolder extends RecyclerView.ViewHolder {
        private AppCompatSpinner partnameACTV, quantityACTV;
        private TextInputEditText amountACTV;
        private TextView currenctCodeTV;
        public ImageButton deleteButton;

        public PartViewHolder(View v) {

            super(v);
            partnameACTV = (AppCompatSpinner) v.findViewById(R.id.partname_actv);
            quantityACTV = (AppCompatSpinner) v.findViewById(R.id.quantity_actv);
            amountACTV = (TextInputEditText) v.findViewById(R.id.amount_actv);
            currenctCodeTV = (TextView) v.findViewById(R.id.currencycode_tv);
            deleteButton = (ImageButton) v.findViewById(R.id.delete_button);
            amountACTV.setKeyListener(null);
            quantityACTV.setAdapter(digitsAdapter);
            partnameACTV.setAdapter(partNameAdapter);


        }
    }

    private void getPartNameList() {
        partNameList = new ArrayList<>();
        if (servicePartDTOList != null && servicePartDTOList.size() > 0) {
            for (int index = 0; index < servicePartDTOList.size(); index++) {
                partNameList.add(servicePartDTOList.get(index).getPartName());
            }
        }
    }

    private static PartAmountChangeListener mPartAmountChangeListener;

    public interface PartAmountChangeListener {
        void onPartAmountChanged(List<ServicePartDTO> partDetailsMap);
    }

    public void setPartAmountChangeListener(final PartAmountChangeListener partAmountChangeListener) {
        mPartAmountChangeListener = partAmountChangeListener;
    }


}

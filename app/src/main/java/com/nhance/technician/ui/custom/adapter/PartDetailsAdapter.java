package com.nhance.technician.ui.custom.adapter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
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
import com.nhance.technician.ui.fragments.GenerateInvoiceFragment;
import com.nhance.technician.util.TypefaceUtils.TypefaceHelper;
import com.nhance.technician.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by afsarhussain on 09/07/17.
 */

public class PartDetailsAdapter extends RecyclerView.Adapter<PartDetailsAdapter.PartViewHolder> {

    private Map<String, ServicePartDTO> servicePartsMap;
    private List<Integer> rowIndex;
    private Context mContext;
    private List<ServicePartDTO> servicePartDTOList;
    private ArrayAdapter<String> partNameAdapter;
    private ArrayAdapter<Integer> digitsAdapter;
    private List<String> partNameList;
    private List<ServicePartDTO> partDetailsMap;
    private Integer[] digits = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private String currencyCode;
    private GenerateInvoiceFragment mFrag;
    public boolean doesPartExists = false;

    public PartDetailsAdapter(Context context, List<ServicePartDTO> servicePartDTOList, List<Integer> rowIndex, String currencyCode, GenerateInvoiceFragment frag) {
        this.mContext = context;
        this.servicePartDTOList = servicePartDTOList;
        this.rowIndex = rowIndex;
        this.currencyCode = currencyCode;
        getPartNameList();
        this.mFrag = frag;
        partNameAdapter = new ArrayAdapter<String>(mContext, R.layout.row_spn, partNameList);
        digitsAdapter = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_spinner_item, digits);
        digitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partDetailsMap = new ArrayList<>();
        addItemToPartDetailsMap(0);
    }

    public PartDetailsAdapter(Context context, List<ServicePartDTO> servicePartDTOList, List<Integer> rowIndex,
                              String currencyCode, GenerateInvoiceFragment frag, List<ServicePartDTO> mPartDetailsMap) {
        this.mContext = context;
        this.servicePartDTOList = servicePartDTOList;
        this.rowIndex = rowIndex;
        this.currencyCode = currencyCode;
        getPartNameList();
        this.mFrag = frag;
        partNameAdapter = new ArrayAdapter<String>(mContext, R.layout.row_spn, partNameList);
        digitsAdapter = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_spinner_item, digits);
        digitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partDetailsMap = mPartDetailsMap;
        addItemToPartDetailsMap(rowIndex.size() - 1);
    }

    @Override
    public PartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_part_details, parent, false);
        final PartViewHolder partViewHolder = new PartViewHolder(view);
        TypefaceHelper.getInstance().setTypeface(view, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
        return partViewHolder;
    }

    @Override
    public void onBindViewHolder(final PartViewHolder holder, final int position) {
        if (partDetailsMap.size() > 0 && partDetailsMap.size() != 1 && position != (partDetailsMap.size() - 1)) {
            holder.partNameACTV.setText(partDetailsMap.get(position).getPartName());
            holder.quantityACTV.setSelection(partDetailsMap.get(position).getQuantity());
            holder.amountACTV.setText(Util.getFormattedAmount(partDetailsMap.get(position).getAmount()));
        }
        if (position == (partDetailsMap.size() - 1)) {
            holder.partNameACTV.requestFocus();
        }
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
                    notifyItemRemoved(position);
                    LOG.d("holder.deleteButton.setOnClickListener : partDetailsMap  : >> partDetailsMap.size() : >> ", partDetailsMap.size() + " : 222222222 >> : " + partDetailsMap + "");
                    mPartAmountChangeListener.onPartAmountChanged(partDetailsMap);
                } else {
                    partDetailsMap = new ArrayList<>();
                }
            }
        });
        holder.partNameACTV.setThreshold(1);
        holder.partNameACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                mFrag.hideSoftKeyPad();
                LOG.d("holder.partnameACTV.setOnItemSelectedListener : pos  : >> ", pos + "");
                ServicePartDTO servicePartDTO = servicePartsMap.get(parent.getAdapter().getItem(pos));
                ServicePartDTO partDetails = null;
                LOG.d("holder.partnameACTV.setOnItemSelectedListener  : >> position : >> ", position + " : 3333333333 >> : partDetails : " + partDetails + "");
                partDetails = partDetailsMap.get(position);
                if (!partDetails.getPartName().equalsIgnoreCase(servicePartDTO.getPartName())) {
                    partDetails = new ServicePartDTO(servicePartDTO.getPartId(), servicePartDTO.getPartName(), servicePartDTO.getAmount(), digits[0]);
                    partDetailsMap.set(position, partDetails);
                    holder.quantityACTV.setSelection(0);
                    holder.amountACTV.setText(Util.getFormattedAmount(servicePartDTO.getAmount()));
                }
                holder.itemView.setTag(position);
                holder.quantityACTV.setSelection(1);
                LOG.d("holder.partnameACTV.setOnItemSelectedListener : partDetailsMap  : >> partDetailsMap.size() : >> ", partDetailsMap.size() + " : 4444444444 >> : " + partDetailsMap + "");
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
                if (holder.partNameACTV.getText().length() > 0)
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

        holder.partNameACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isServicePart = false;
                for (int i = 0; i < servicePartDTOList.size(); i++) {
                    if (servicePartDTOList.get(i).getPartName().trim().equalsIgnoreCase(s.toString())) {
                        isServicePart = true;
                    }
                }

                if (isServicePart) {
                    doesPartExists = true;
                }
            }
        });
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

    public List<ServicePartDTO> getPartDetailsMap() {
        return partDetailsMap;
    }

    public void addItemToPartDetailsMap(int position) {
        ServicePartDTO servicePartDTO = servicePartDTOList.get(0);
        ServicePartDTO partDetails = new ServicePartDTO(servicePartDTO.getPartId(), servicePartDTO.getPartName(), servicePartDTO.getAmount(), digits[0]);
       /* if (partDetailsMap.size() > position) {
            partDetailsMap.set(position, partDetails);
        } else {*/
        partDetailsMap.add(position, partDetails);
        notifyDataSetChanged();
        // }
    }

    public void clearAllItems() {
        rowIndex.clear();
        partDetailsMap.clear();
    }

    public class PartViewHolder extends RecyclerView.ViewHolder {
        private AppCompatSpinner quantityACTV;
        private AppCompatAutoCompleteTextView partNameACTV;
        private TextInputEditText amountACTV;
        private TextView currenctCodeTV;
        public ImageButton deleteButton;

        public PartViewHolder(View v) {

            super(v);
            partNameACTV = (AppCompatAutoCompleteTextView) v.findViewById(R.id.partname_actv);
            quantityACTV = (AppCompatSpinner) v.findViewById(R.id.quantity_actv);
            amountACTV = (TextInputEditText) v.findViewById(R.id.amount_actv);
            currenctCodeTV = (TextView) v.findViewById(R.id.currencycode_tv);
            deleteButton = (ImageButton) v.findViewById(R.id.delete_button);
            amountACTV.setKeyListener(null);
            quantityACTV.setAdapter(digitsAdapter);
            partNameACTV.setAdapter(partNameAdapter);
        }
    }

    private void getPartNameList() {
        partNameList = new ArrayList<>();
        servicePartsMap = new HashMap<>();
        if (servicePartDTOList != null && servicePartDTOList.size() > 0) {
            for (int index = 0; index < servicePartDTOList.size(); index++) {
                partNameList.add(servicePartDTOList.get(index).getPartName());
                servicePartsMap.put(servicePartDTOList.get(index).getPartName(), servicePartDTOList.get(index));
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

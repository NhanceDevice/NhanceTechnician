package com.nhance.technician.ui.custom.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nhance.technician.R;
import com.nhance.technician.model.newapis.AddressModel;
import com.nhance.technician.model.newapis.ContactModel;
import com.nhance.technician.model.newapis.ServiceRequestModel;
import com.nhance.technician.ui.util.EditTextUtils;
import com.nhance.technician.util.DateUtil;

import java.util.List;

/**
 * Created by Javeed on 05-12-2017.
 */
public class AssignedServiceRequestAdapter extends RecyclerView.Adapter<AssignedServiceRequestAdapter.MyViewHolder> {

    private List<ServiceRequestModel> serviceRequestModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout mainLay;
        private final AppCompatTextView ser_req_no_tv;
        private final AppCompatTextView created_date_tv;
        private final AppCompatTextView ser_req_sub_tv;
        private final AppCompatTextView user_name_tv;
        private final AppCompatTextView address_tv;
        private final AppCompatTextView mobile_number_tv;

        public MyViewHolder(View view) {
            super(view);
            mainLay = (LinearLayout) view.findViewById(R.id.main_layout);
            ser_req_no_tv = (AppCompatTextView) view.findViewById(R.id.ser_req_no_tv);

            created_date_tv = (AppCompatTextView) view.findViewById(R.id.created_date_tv);
            ser_req_sub_tv = (AppCompatTextView) view.findViewById(R.id.ser_req_sub_tv);
            user_name_tv = (AppCompatTextView) view.findViewById(R.id.user_name_tv);
            address_tv = (AppCompatTextView) view.findViewById(R.id.address_tv);
            mobile_number_tv = (AppCompatTextView) view.findViewById(R.id.mobile_number_tv);
        }
    }

    public AssignedServiceRequestAdapter(List<ServiceRequestModel> requestModels, AssignedServiceRequestAdapterInterface assignedServiceRequestAdapterInterface) {
        this.serviceRequestModelList = requestModels;
        mAssignedServiceRequestAdapterInterface = assignedServiceRequestAdapterInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assigned_service_request_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final ServiceRequestModel serviceRequestModel = serviceRequestModelList.get(position);

        holder.ser_req_no_tv.setText(serviceRequestModel.getSrn());

        holder.created_date_tv.setText(DateUtil.convertDateToDateStr(serviceRequestModel.getCreatedDate(), DateUtil.DATE_FORMAT_dd_MM_yyyy_SEP_HIPHEN));

        holder.ser_req_sub_tv.setText(serviceRequestModel.getSubject());

        holder.user_name_tv.setText(serviceRequestModel.getUserName());

        AddressModel location = null;

        if(serviceRequestModel.getServiceLocations() != null && serviceRequestModel.getServiceLocations().size() > 0){
            location = serviceRequestModel.getServiceLocations().get(0);
        }

        if(location != null){
            String address = location.getLineOne();
            if (location.getLineTwo() != null && location.getLineTwo().length() > 0) {
                address = address + ", " + location.getLineTwo();
            }
            address = address + ", " + location.getCity() + ", " + location.getState() + " - " + location.getPinCode();

            holder.address_tv.setText(address);
        }

        ContactModel contactModel = serviceRequestModel.getContact();
        if(contactModel != null){
            StringBuilder stringBuilder = new StringBuilder();

            if(contactModel.getIsdCode() != null)
                stringBuilder.append("+"+contactModel.getIsdCode());

            if(contactModel.getMobile() != null)
                stringBuilder.append(contactModel.getMobile());

            holder.mobile_number_tv.setText(stringBuilder.toString());
            holder.mobile_number_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAssignedServiceRequestAdapterInterface.callCustomer(EditTextUtils.getText(holder.mobile_number_tv), position);
                }
            });
            /*holder.mobile_number_tv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mAssignedServiceRequestAdapterInterface.callCustomer(EditTextUtils.getText(holder.mobile_number_tv), position);
                    return true;
                }
            });*/
        }

        holder.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAssignedServiceRequestAdapterInterface.initiateInvoiceGenerator(serviceRequestModel, position);
            }
        });

        /*holder.mainLay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mAssignedServiceRequestAdapterInterface.initiateInvoiceGenerator(serviceRequestModel, position);
                return true;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return serviceRequestModelList.size();
    }

    private AssignedServiceRequestAdapterInterface mAssignedServiceRequestAdapterInterface;

    public interface AssignedServiceRequestAdapterInterface{
        void callCustomer(final String customerMobileNumber, final int selectedIndexPos);
        void initiateInvoiceGenerator(final ServiceRequestModel serviceRequestModel, final int selectedIndexPos);
    }
}

package com.nhance.technician.ui.custom.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.model.ServiceRequestInvoiceDTO;
import com.nhance.technician.util.DateUtil;
import com.nhance.technician.util.Util;

import java.util.List;


/**
 * Created by sahana on 28/1/17.
 */
public class ServiceRequestHistoryAdapter extends RecyclerView.Adapter<ServiceRequestHistoryAdapter.ViewHolder> {

    Context mContext;
    List<ServiceRequestInvoiceDTO> serviceRequestInvoiceDTOList;

    public ServiceRequestHistoryAdapter(Context context, List<ServiceRequestInvoiceDTO> serviceRequestInvoiceDTOList) {
        this.mContext = context;
        this.serviceRequestInvoiceDTOList = serviceRequestInvoiceDTOList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_history_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.serReqNoTV.setText(mContext.getResources().getString(R.string.srn)+" : "+serviceRequestInvoiceDTOList.get(position).getServiceRequestNumber());
            holder.serReqSubjectTV.setText(serviceRequestInvoiceDTOList.get(position).getServiceRequestSubject());

            holder.invoiceNoTV.setText(serviceRequestInvoiceDTOList.get(position).getInvoiceNumber()!=null?serviceRequestInvoiceDTOList.get(position).getInvoiceNumber():"");
        if(serviceRequestInvoiceDTOList.get(position).getCurrencyCode()!=null && serviceRequestInvoiceDTOList.get(position).getCurrencyCode().length()>0) {
            holder.amountTV.setText(Util.getCurrencySymbolFromUniCode(serviceRequestInvoiceDTOList.get(position).getCurrencyCode()) +
                    " " + Util.getFormattedAmount(serviceRequestInvoiceDTOList.get(position).getNetPaybleAmount()));
        }
        else{
            holder.amountTV.setText(" " + Util.getFormattedAmount(serviceRequestInvoiceDTOList.get(position).getNetPaybleAmount()));
        }
        if(serviceRequestInvoiceDTOList.get(position).getInvoiceGenerationDate()!= 0){
            String dateStr = DateUtil.convertTimeInMillisToDateStr(serviceRequestInvoiceDTOList.get(position).getInvoiceGenerationDate(),"dd-MM-yyyy hh:mm a");
            holder.invoiceDateTV.setText(dateStr);
        }
        String paymentType="";
        if(serviceRequestInvoiceDTOList.get(position).getModeOfPayment()==ApplicationConstants.MODE_OF_PAYMENT_CASH){
            paymentType = ApplicationConstants.CASH_PAYMENT+" - ";
        }else if(serviceRequestInvoiceDTOList.get(position).getModeOfPayment()==ApplicationConstants.MODE_OF_PAYMENT_ONLINE){
            paymentType = ApplicationConstants.ONLINE_PAYMENT+" - ";
        }
        if(serviceRequestInvoiceDTOList.get(position).getStatus() == ApplicationConstants.PAYMENT_STATUS_PENDING_BILL_GENERATED){
            holder.paymentStatusTV.setText(paymentType +mContext.getResources().getString(R.string.payment_pending));
            holder.paymentStatusTV.setTextColor(mContext.getResources().getColor(R.color.red));
        }else if(serviceRequestInvoiceDTOList.get(position).getStatus() == ApplicationConstants.PAYMENT_STATUS_PAID){
            holder.paymentStatusTV.setText(paymentType +mContext.getResources().getString(R.string.payment_paid));
            holder.paymentStatusTV.setTextColor(mContext.getResources().getColor(R.color.green));
        }

    }

    @Override
    public int getItemCount() {
        return serviceRequestInvoiceDTOList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView serReqNoTV;
        AppCompatTextView amountTV;
        AppCompatTextView serReqSubjectTV;
        AppCompatTextView invoiceNoTV;
        AppCompatTextView paymentStatusTV;
        AppCompatTextView invoiceDateTV;

        ViewHolder(View itemView) {
            super(itemView);
            serReqNoTV = (AppCompatTextView) itemView.findViewById(R.id.history_ser_req_no_tv);
            amountTV = (AppCompatTextView) itemView.findViewById(R.id.invoice_amount_tv);
            serReqSubjectTV = (AppCompatTextView) itemView.findViewById(R.id.ser_req_sub_tv);
            invoiceNoTV = (AppCompatTextView) itemView.findViewById(R.id.invoice_no_tv);
            invoiceDateTV = (AppCompatTextView)itemView.findViewById(R.id.invoice_date_tv);
            paymentStatusTV = (AppCompatTextView)itemView.findViewById(R.id.history_payment_status_tv);
        }
    }

}

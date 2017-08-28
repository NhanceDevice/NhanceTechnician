package com.nhance.technician.model;

import java.util.List;

/**
 * Created by afsarhussain on 08/08/17.
 */

public class MasterDataDTO extends BaseModel{
    private List<ServiceRequestInvoiceDTO> invoiceHistory;

    public List<ServiceRequestInvoiceDTO> getInvoiceHistory() {
        return invoiceHistory;
    }

    public void setInvoiceHistory(List<ServiceRequestInvoiceDTO> invoiceHistory) {
        this.invoiceHistory = invoiceHistory;
    }
}

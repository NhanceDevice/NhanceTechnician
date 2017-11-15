package com.nhance.technician.model.newapis;

/**
 * The Class ServiceRequestInvoiceComponentModel.
 */
public class ServiceRequestInvoiceComponentModel extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4317020684973141934L;

	/** The part id. */
	private Integer partId;
	
	/** The part name. */
	private String partName;
	
	/** The quantity. */
	private Integer quantity;
	
	/** The amount. */
	private Double amount;
	
	/** The invoice. */
	private ServiceRequestInvoiceModel invoice;

	/**
	 * Gets the part id.
	 *
	 * @return the part id
	 */
	public Integer getPartId() {
		return partId;
	}

	/**
	 * Sets the part id.
	 *
	 * @param partId the new part id
	 */
	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	/**
	 * Gets the part name.
	 *
	 * @return the part name
	 */
	public String getPartName() {
		return partName;
	}

	/**
	 * Sets the part name.
	 *
	 * @param partName the new part name
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the invoice.
	 *
	 * @return the invoice
	 */
	public ServiceRequestInvoiceModel getInvoice() {
		return invoice;
	}

	/**
	 * Sets the invoice.
	 *
	 * @param invoice the new invoice
	 */
	public void setInvoice(ServiceRequestInvoiceModel invoice) {
		this.invoice = invoice;
	}
}

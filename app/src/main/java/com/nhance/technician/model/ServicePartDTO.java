package com.nhance.technician.model;

/* Copyright Â© EasOfTech 2015. All rights reserved.
*
* This software is the confidential and proprietary information
* of EasOfTech. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with EasOfTech.
*
* Id: ServicePartDTO.java
*
* Date Author Changes
* 30 Jun, 2017 Saroj Created
*/


/**
 * The Class ServicePartDTO.
 */
public class ServicePartDTO extends BaseModel {

    /** The part id. */
    private Long partId;

    /** The part name. */
    private String partName;

    /** The amount. */
    private Double amount;

    /** The quantity. */
    private Integer quantity;

    public ServicePartDTO(Long partId, String partName, Double amount, int quantity) {
        this.partId = partId;
        this.partName = partName;
        this.amount = amount;
        this.quantity = quantity;
    }

    /**
     * Gets the part id.
     *
     * @return the part id
     */
    public Long getPartId() {
        return partId;
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
     * Gets the amount.
     *
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Sets the part id.
     *
     * @param partId the new part id
     */
    public void setPartId(Long partId) {
        this.partId = partId;
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
     * Sets the amount.
     *
     * @param amount the new amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
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

    @Override
    public String toString() {
        return "ServicePartDTO{" +
                "partId=" + partId +
                ", partName='" + partName + '\'' +
                ", amount=" + amount +
                ", quantity=" + quantity +
                '}';
    }
}
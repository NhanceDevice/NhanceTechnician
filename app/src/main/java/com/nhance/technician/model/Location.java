/* Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of EasOfTech. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms and
 * conditions entered into with EasOfTech.
 *
 * Id: Location.java
 *
 * Date Author Changes
 * 4 Jan, 2016 Swadhin Created
 */
package com.nhance.technician.model;

import java.util.Map;

/**
 * The Class Location.
 */
public class Location extends BaseModel {

    /**
     * The customer location id.
     */
    private Long locationId;

    private String name;

    //private String mobileNumber;

    /**
     * The address1.
     */
    private String address;

    /**
     * The address2.
     */
    private String landMark;

    /**
     * The district.
     */
    private String city;

    /**
     * The city.
     */
    private String district;

    /**
     * The pin code.
     */
    private String pinCode;

    /**
     * The state id.
     */
    private Integer state;

    /**
     * The state.
     */
    private String stateName;

    /**
     * The country id.
     */
    private Long countryId;

    /**
     * The country.
     */
    private String countryName;

    /**
     * The location status.
     */
    private Integer locationStatus;

    private Double latitude;


    private Double longitude;

    /**
     * The default location status.
     */
    private Integer isDefaultAddress;

    public Integer getIsDefaultAddress() {
        return isDefaultAddress;
    }

    public void setIsDefaultAddress(Integer isDefaultAddress) {
        this.isDefaultAddress = isDefaultAddress;
    }

    private Map<String, String> addnInfo;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Map<String, String> getAddnInfo() {
        return addnInfo;
    }

    public void setAddnInfo(Map<String, String> addnInfo) {
        this.addnInfo = addnInfo;
    }

    /**
     * Gets the location id.
     *
     * @return the location id
     */
    public Long getLocationId() {
        return locationId;
    }

    /**
     * Sets the location id.
     *
     * @param locationId the new location id
     */
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    /**
     * Gets the location status.
     *
     * @return the location status
     */
    public Integer getLocationStatus() {
        return locationStatus;
    }

    /**
     * Sets the location status.
     *
     * @param locationStatus the new location status
     */
    public void setLocationStatus(Integer locationStatus) {
        this.locationStatus = locationStatus;
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the district.
     *
     * @return the district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the city.
     *
     * @param district
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * Gets the pin code.
     *
     * @return the pin code
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * Sets the pin code.
     *
     * @param pinCode the new pin code
     */
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    /**
     * Gets the state id.
     *
     * @return the state
     */
    public Integer getState() {
        return state;
    }

    /**
     * Sets the state id.
     *
     * @param state the new state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * Gets the country id.
     *
     * @return the country id
     */
    public Long getCountryId() {
        return countryId;
    }

    /**
     * Sets the country id.
     *
     * @param countryId the new country id
     */
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the land mark.
     *
     * @return the land mark
     */
    public String getLandMark() {
        return landMark;
    }

    /**
     * Sets the land mark.
     *
     * @param landMark the new land mark
     */
    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }*/

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", landMark='" + landMark + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", state=" + state +
                ", stateName='" + stateName + '\'' +
                ", countryId=" + countryId +
                ", countryName='" + countryName + '\'' +
                ", locationStatus=" + locationStatus +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", addnInfo=" + addnInfo +
                '}';
    }
}

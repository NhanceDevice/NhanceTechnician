/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ServiceRequestModelFindResponse.java
*
* Date Author Changes
* 6 Sep, 2017 Sudhanshu Created
*/

package com.nhance.technician.model.newapis;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class ServiceRequestModelFindResponse.
 */
public class ServiceRequestModelFindResponse extends FindResultModel<ServiceRequestModel> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6509214449699045500L;

	/** The service type map. */
	private Map<Integer, String> serviceTypeMap = new HashMap<Integer, String>();

	/**
	 * Gets the service type map.
	 *
	 * @return the service type map
	 */
	public Map<Integer, String> getServiceTypeMap() {
		return serviceTypeMap;
	}

	/**
	 * Sets the service type map.
	 *
	 * @param serviceTypeMap the service type map
	 */
	public void setServiceTypeMap(Map<Integer, String> serviceTypeMap) {
		this.serviceTypeMap = serviceTypeMap;
	} 

}

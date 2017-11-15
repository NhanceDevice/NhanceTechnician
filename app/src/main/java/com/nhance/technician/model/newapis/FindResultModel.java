/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: FindResultModel.java
*
* Date Author Changes
* 1 Aug, 2017 Saroj Created
*/
package com.nhance.technician.model.newapis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class FindResultModel.
 *
 * @param <M> the generic type
 */
public abstract class FindResultModel<M extends NhanceModel> extends ResponseModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6551022950627738650L;

	/** The results. */
	private List<M> results = new ArrayList<>();
	
	/** The count. */
	private Long count;
	
	/** The status map. */
	private Map<Integer, String> statusMap = new HashMap<Integer, String>();

	/**
	 * Gets entity result.
	 * @return Result Model
	 */
	public List<M> getResults() {
		return this.results;
	}

	/**
	 * Sets the results.
	 *
	 * @param results the new results
	 */
	public void setResults(List<M> results) {
		this.results = results;
	}

	/**
	 * Gets the status map.
	 *
	 * @return the status map
	 */
	public Map<Integer, String> getStatusMap() {
		return statusMap;
	}

	/**
	 * Sets the status map.
	 *
	 * @param statusMap the status map
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(Long count) {
		this.count = count;
	}

}

package com.nhance.technician.model;


import java.io.Serializable;

/*
*
* Id: EncryptedModel
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/
public abstract class EncryptedModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7420882710497322182L;

	/**
	 * 
	 */
	protected String uniqueId;


	/**
	 * 
	 */
	protected String terminalId;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

}

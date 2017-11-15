package com.nhance.technician.model.newapis;

/**
 * The Class ServiceRequestExtnModel.
 */
public class ServiceRequestExtnModel extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3787786109387977724L;

	/** The extn key. */
	private String extnKey;
	
	/** The extn value. */
	private String extnValue;
	
	/** The service request. */
	private ServiceRequestModel serviceRequest;

	/**
	 * Gets the extn key.
	 *
	 * @return the extn key
	 */
	public String getExtnKey() {
		return extnKey;
	}

	/**
	 * Sets the extn key.
	 *
	 * @param extnKey the new extn key
	 */
	public void setExtnKey(String extnKey) {
		this.extnKey = extnKey;
	}

	/**
	 * Gets the extn value.
	 *
	 * @return the extn value
	 */
	public String getExtnValue() {
		return extnValue;
	}

	/**
	 * Sets the extn value.
	 *
	 * @param extnValue the new extn value
	 */
	public void setExtnValue(String extnValue) {
		this.extnValue = extnValue;
	}

	/**
	 * Gets the service request.
	 *
	 * @return the service request
	 */
	public ServiceRequestModel getServiceRequest() {
		return serviceRequest;
	}

	/**
	 * Sets the service request.
	 *
	 * @param serviceRequest the new service request
	 */
	public void setServiceRequest(ServiceRequestModel serviceRequest) {
		this.serviceRequest = serviceRequest;
	}

}

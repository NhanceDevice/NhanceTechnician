/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.nhance.technician.networking.util;

public interface RequestTypes {

	int RES_SUCCESS = 0;

	/** The Dummy Request Type. */
    int REQUEST_DUMMY 	= 	-1;

	int OTP_REQ 						=	1;
	int VERIFY_OTP_REQ 					=	2;
	int LOGIN_REQ 						=	3;
	int FORGOT_PASSWORD_REQ		 		=	4;
	int UPDATE_CUSTOMER_PROFILE_REQ 	=	5;
	int PERFORM_SYNC_MASTER_DATA_REQ 	=	6;
	int SEND_FEEDBACK_REQ				= 	7;
	int DOWNLOAD_DIGITAL_KIT_REQ		= 	8;
	int ADD_PRODUCT_REQ					= 	9;
	int EDIT_PRODUCT_REQ				= 	10;
	int UPLOAD_CRASH_LOG_URL			= 	11;
	int FETCH_RESOURCES_LINKS_URL		= 	12;
	int CHANGE_DIGITAL_KIT_STATUS_URL	= 	13;
	int RESEND_OTP_REQ					=	14;
	int CHANGE_PASSWORD_REQ				=	15;
	int CREATE_PASSWORD_REQ				=	16;

	int GET_WARRANTY_REQ = 17;
	int CREATE_SERVICE_REQUEST_REQ = 18;
	int GET_SERVICE_REQUEST_DETAILS_REQ = 19;
	int REPLY_SERVICE_REQUEST_REQ = 20;
	int CLOSE_SERVICE_REQUEST_REQ = 21;

	int GET_PAYUMONEY_SERVER_SIDE_HASH_REQ			= 	22;
	int POST_PURCHASE_LOG_TO_SERVER_REQ =	23;
	int ADD_USER_MOBILE_NUMBER_REQUEST_REQ = 31;
	int VALIDATE_OTP_USER_MOBILE_NUMBER_REQUEST_REQ = 32;
	int REMOVE_USER_MOBILE_NUMBER_REQUEST_REQ = 33;
	int RESEND_OTP_USER_MOBILE_NUMBER_REQUEST_REQ = 34;
	int FETCH_DIGITAL_KIT_DOCUMENT_LINKS_URL_REQ = 35;
	int ADD_DK_ADDITIONAL_DOCUMENT_URL_REQ = 36;



}

package com.nhance.technician.networking.util;

public interface RestConstants {

    int DES_KEY_LENGTH = 16;

    String TRUST_STORE_PASSWORD = "e@s0fTech";

    String USER_AGENT_HEADER = "User-Agent";
    String TRANSPORT_KEY_HEADER = "Trans-Key";
    String REST_AUTHORIZATION_HEADER = "Authorization";
    String REST_AUTHORIZATION_FORMAT = "Bearer %s";
    String CHARSET_HEADER = "Accept-Charset";
    String CONTENT_TYPE_HEADER = "Content-Type";
    String APPLICATION_TYPE_HEADER = "Application-Type";
    String DEVICE_ID_HEADER = "Device-Id";
    String APPLICATION_VERSION_HEADER = "Application-Version";

    String LOGIN_REQ = "/auth";
    String GET_SER_REQ_DETAILS = "/NHance-Module-Dk/rest/serviceRequest/technician/getServiceRequestDetails";
    String UPLOAD_INVOICE = "/NHance-Module-Dk/rest/serviceRequest/technician/uploadInvoiceComponent";
    String SYNC_REQ = "/NHance-Module-Auth/rest/user/sync";
    String GCM_REGISTRATION_REQ_URL = "/user/deviceRegistration";

    String OTP_REQUEST_URL						= "/auth/createotp";

    String CREATE_OR_RESEND_OTP_REQUEST_URL = "/auth/createotp";
    String REGISTER_USER_REQUEST_URL = "/user";
    String VERIFY_OTP_URL = "/auth/validateotp";
    String LOGOUT_URL = "/auth/logout";
    String FORGOT_PASSWORD_URL = "/auth/createotp";

    String RESET_PASSWORD_REQ_URL 				= "/auth/resetpassword";
    String CHANGE_PASSWORD_URL 					= "/auth/changepassword";
}

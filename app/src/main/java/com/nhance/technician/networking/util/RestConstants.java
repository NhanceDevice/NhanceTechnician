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

    String LOGIN_REQ = "/NHance-Module-Auth/rest/auth/login";
    String GET_SER_REQ_DETAILS = "/NHance-Module-Dk/rest/serviceRequest/technician/getServiceRequestDetails";
    String UPLOAD_INVOICE = "/NHance-Module-Dk/rest/serviceRequest/technician/uploadInvoiceComponent";
    String SYNC_REQ = "/NHance-Module-Auth/rest/user/sync";
    String GCM_REGISTRATION_REQ_URL = "/NHance-Module-Auth/rest/user/registerDeviceId";
}

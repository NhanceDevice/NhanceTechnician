package com.nhance.technician.app;

/* 
*
* Id: MixpanelConstants
*
* Date Author Changes
* 14-Jan-16 afsar Created
*/
public interface MixpanelConstants {


    /**
     * Token for MIXPANEL API
     */
    //public static final String MIXPANEL_TOKEN = "dca1d51ee6a3594e2d0060a0708e669c"; // Token For DEMO build

    String MIXPANEL_TOKEN = "6f5d35bda7c418c2c7d200d3ef39f845"; // Token For QA Build

//    public static final String  MIXPANEL_TOKEN = "97e11d75bf8eef00055c1a782fd318fa"; // Token For PROD Build

    String MIXPANEL_DISTINCT_ID_NAME = "Mixpanel_Distinct_Id_For_Nhance";

    String USER_PROFILE_DETAILS = "ProfileDetails";
    String USER_MOBILE_NO = "MobileNumber";
    String USER_CUSTOMER_CODE = "CustomerCode";
    String USER_NAME = "Name";
    String USER_EMAIL_ID = "EmailId";
    String USER_GENDER = "Gender";

    String USER_FIRST_VIEWED_ON = "First Viewed On";


    String USER_PAGE_VISIT_EVENT = "PageVisit";
    String USER_REGISTRATION_INIT_EVENT = "SignUp View";
    String USER_REGISTRATION_DONE_EVENT = "Registration";
    String USER_OTP_VERIFICATION_DONE_EVENT = "OTPVerification";
    String USER_SIGN_UP_COMPLETE_EVENT = "SignUpComplete";
    String NOTIFICATION_IMPRESSION_EVENT = "Impression";
    String NOTIFICATION_CLICKED_EVENT = "Clicked";
    String NOTIFICATION_DISMISS_EVENT = "Dismissed";


    String MENU_CLICK_EVENT = "MenuClick";
    String PROPERTY_MENU_NAME = "MenuName";
    String PROPERTY_MENU_CODE = "MenuCode";
    String PROPERTY_BRAND_NAME = "BrandName";
    String PROPERTY_BRAND_CODE = "BrandCode";
    String PROPERTY_SELLER_NAME = "SellerName";
    String PROPERTY_SELLER_CODE = "SellerCode";
    String PROPERTY_DK_CODE = "DKCode";
    String PROPERTY_PRODUCT_TYPE_NAME = "ProductTypeName";
    String PROPERTY_PRODUCT_TYPE_ID = "ProductTypeId";
    String PROPERTY_PRODUCT_CATEGORY_NAME = "ProductCategoryName";
    String PROPERTY_PRODUCT_CATEGORY_ID = "ProductCategoryId";

    String PROPERTY_TRACK_EVENT_TIME = "TrackEventTime";
    String PROPERTY_MENU_LEVEL = "MenuLevel";
    String PROPERTY_PARENT_MENU_CODE = "ParentMenuCode";
    String PROPERTY_SERVICE_REQUEST_PRICE = "ServiceRequestPrice";
    String PROPERTY_PURCHASE_AMOUNT = "PurchaseAmount";


    String PROPERTY_NOTIFICATION_CODE = "NotificationCode";
    String PROPERTY_NOTIFICATION_CODE_EXTRA = "NotificationCodeExtra";
    String PROPERTY_CUSTOMER_CODE = "CustomerCode";
    String PROPERTY_MOBILE_NUMBER = "MobileNumber";
    String PROPERTY_NOTIFICATION_RECEIVED_TIME = "ReceivedTime";


    String PROPERTY_PAGE_NAME = "PageName";

    String VALUE_SIGNUP_PAGE_NAME = "SignUp";
    String VALUE_OTP_VERIFICATION_PAGE_NAME = "OTPVerification";
    String VALUE_LOGIN_PAGE_NAME = "Login";
    String VALUE_FORGOT_PASSWORD_PAGE_NAME = "ForgotPassword";
    String VALUE_MY_PRODUCTS_PAGE_NAME = "MyProducts";
    String VALUE_DOCUMENTS_PAGE_NAME = "Documents";
    String VALUE_TRAINING_PAGE_NAME = "Training";
    String VALUE_PURCHASE_PAGE_NAME = "Purchase";
    String VALUE_SERVICE_REQ_PAGE_NAME = "ServiceRequest";
    String VALUE_HELPDESK_PAGE_NAME = "HelpDesk";
    String VALUE_VIDEO_CHAT_PAGE_NAME = "VideoChat";


    String VALUE_ADD_ANOTHER_NO_PAGE_NAME = "AddAnotherNumber";
    String VALUE_PLACE_REQUEST_PAGE_NAME = "PlaceServiceRequest";
    String VALUE_PROFILE_PAGE_NAME = "MyProfile";

    String VALUE_PARENT_MENU_LEVEL = "1";
    String VALUE_SUB_MENU_LEVEL = "2";
}

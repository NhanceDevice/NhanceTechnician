package com.nhance.technician.ui.action;

import android.database.Cursor;
import android.util.Log;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.datasets.UserProfileTable;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.BaseModel;
import com.nhance.technician.model.newapis.ContactModel;
import com.nhance.technician.model.newapis.MessageModel;
import com.nhance.technician.util.AccessPreferences;

/**
 * Created by afsarhussain on 12/07/17.
 */

public class CommonAction {


    public void addCommonRequestParameters(BaseModel baseModel){
        Application application = Application.getInstance();
        baseModel.setMobileNumber(application.getMobileNumber());
        baseModel.setAppType(application.getApplicationType());
        baseModel.setOs(application.getOs());
        baseModel.setDefaultLocale("en_US");
    }

    public void addCommonRequestParameters(MessageModel baseModel) {
        Application application = Application.getInstance();
        int appType = NhanceApplication.getApplication().getResources().getInteger(R.integer.application_type);

        if(application.getApplicationType() != appType)
            application.setApplicationType(appType);

        baseModel.setApplicationType(application.getApplicationType());
        baseModel.setDefaultLocale("en_US");
        baseModel.setUserId(application.getUserProfileUserIdOrGuid());
        baseModel.setCustomerId(application.getCustomerId());
        baseModel.setTenantId(application.getTenantId());
        baseModel.setApplicationVersion(Application.getInstance().getVersionNumber());

        ContactModel contactModel = new ContactModel();

        contactModel.setEmail(application.getEmailId());
        contactModel.setMobile(application.getMobileNumber());
        if(application.getIsdCode() > 0)
            contactModel.setIsdCode(application.getIsdCode());

        baseModel.setContact(contactModel);

        NhanceApplication.setModelToTakeAction(baseModel);
    }

    public void loadBasicUserDeatilsToApplicationModel(String userId) throws NhanceException {
        Cursor cursor = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + UserProfileTable.USER_PROFILE_TABLE+" where " +
                    UserProfileTable.COLUMN_USER_ID_OR_GUID + " = '" + userId+"'";
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            if (null != cursor && cursor.moveToFirst()) {
                do {

                    Application application = Application.getInstance();
                    application.setUserProfileUserIdOrGuid(userId);
                    int isdCode = cursor.getInt((cursor.getColumnIndex(UserProfileTable.COLUMN_ISD_CODE)));
                    application.setIsdCode(isdCode);
                    application.setEmailId(cursor.getString((cursor.getColumnIndex(UserProfileTable.COLUMN_SELLER_EMAIL_ID))));
                    application.setSellerCode(cursor.getString((cursor.getColumnIndex(UserProfileTable.COLUMN_SELLER_CODE))));
//                    application.setUserCode(cursor.getString((cursor.getColumnIndex(UserProfileTable.COLUMN_USER_CODE))));

                    application.setMobileNumber(cursor.getString((cursor.getColumnIndex(UserProfileTable.COLUMN_MOBILE_NO))));
                    application.setUserProfileUserIdOrGuid(cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_USER_ID_OR_GUID)));

                    String loggedInUserName = null;
                    StringBuffer sb = new StringBuffer();
                    String sellerName = cursor.getString((cursor.getColumnIndex(UserProfileTable.COLUMN_SELLER_NAME)));
                    if(sellerName != null && sellerName.length() > 0){
                        sb.append(sellerName);
                        loggedInUserName = sb.toString();
                        application.setSellerName(loggedInUserName);

                        application.setLoggedInUserName(application.getSellerName());
                    }

                    int appType = NhanceApplication.getApplication().getResources().getInteger(R.integer.application_type);

                    if(application.getApplicationType() != appType)
                        application.setApplicationType(appType);

                    application.setApplicationType(NhanceApplication.getApplication().getResources().getInteger(R.integer.application_type));

                } while (cursor.moveToNext());
            }
        }
        catch(Exception e)
        {
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        }
        finally {
            if(null != cursor) cursor.close();
            cursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }

    public void reloadApplicationContextDetails(){
        try{
            Application application = Application.getInstance();
            if(application == null || (application != null && (application.getUserCode() == null || (application.getUserCode() != null && application.getUserCode().length() == 0)))){
                Integer userStatus = AccessPreferences.get(NhanceApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_NEW);
                if (userStatus == ApplicationConstants.USER_LOGGED_IN) {
                    String loggedInUsersMobileNo = AccessPreferences.get(NhanceApplication.getContext(), ApplicationConstants.LOGGED_IN_USER, "");
                    Log.d(CommonAction.class.getName(), "loggedInUsersMobileNo : " + loggedInUsersMobileNo);
                    if (loggedInUsersMobileNo != null && loggedInUsersMobileNo.length() > 0) {
                        loadBasicUserDeatilsToApplicationModel(loggedInUsersMobileNo);
                        Application loadedApplication1 = Application.getInstance();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

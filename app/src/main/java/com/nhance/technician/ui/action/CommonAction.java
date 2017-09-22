package com.nhance.technician.ui.action;

import android.database.Cursor;
import android.util.Log;

import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.datasets.UserProfileTable;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.BaseModel;
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

    public void loadBasicUserDeatilsToApplicationModel(String mobileNo) throws NhanceException {
        Cursor cursor = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + UserProfileTable.USER_PROFILE_TABLE+" where " +
                    UserProfileTable.COLUMN_MOBILE_NO + " = '" + mobileNo+"'";
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            if (null != cursor && cursor.moveToFirst()) {
                do {

                    Application application = Application.getInstance();
                    application.setMobileNumber(mobileNo);
                    application.setSellerCode(cursor.getString((cursor.getColumnIndex(UserProfileTable.COLUMN_SELLER_CODE))));
                    application.setUserCode(cursor.getString((cursor.getColumnIndex(UserProfileTable.COLUMN_USER_CODE))));
                    String loggedInUserName = null;
                    StringBuffer sb = new StringBuffer();
                    sb.append(cursor.getString((cursor.getColumnIndex(UserProfileTable.COLUMN_SELLER_NAME))));
                    loggedInUserName = sb.toString();
                    application.setLoggedInUserName(loggedInUserName);

                    /*String loggedInUserProfilePicPath = cursor.getString((cursor.getColumnIndex(UserProfileTable.COLUMN_PROFILE_PIC_PATH)));
                    if(loggedInUserProfilePicPath != null && loggedInUserProfilePicPath.length() > 0 && !(loggedInUserProfilePicPath.equals("") || loggedInUserProfilePicPath.equals(" ")))
                        application.setLoggedInUserProfilePicPath(loggedInUserProfilePicPath);*/

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

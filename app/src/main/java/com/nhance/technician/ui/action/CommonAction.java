package com.nhance.technician.ui.action;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.datasets.AssignedServiceRequestTable;
import com.nhance.technician.datasets.DataSyncTrackTable;
import com.nhance.technician.datasets.UserProfileTable;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.BaseModel;
import com.nhance.technician.model.newapis.AddressModel;
import com.nhance.technician.model.newapis.ContactModel;
import com.nhance.technician.model.newapis.DataSyncTrackModel;
import com.nhance.technician.model.newapis.MessageModel;
import com.nhance.technician.model.newapis.ServiceRequestModel;
import com.nhance.technician.model.newapis.enums.Status;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.util.AccessPreferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
//                    application.setUserCode(cursor.getString((cursor.getColumnIndex(UserProfileTable.COLUMN_USER_GUID))));

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

    public DataSyncTrackModel updateSyncInProgress(Integer syncType) throws NhanceException {
        DataSyncTrackModel dataSyncTrackModel = getDataSyncTrackFromType(syncType);
        long lastSyncTime = 0;

        if (dataSyncTrackModel != null) {
            lastSyncTime = dataSyncTrackModel.getLastSyncTime();
        } else {
            dataSyncTrackModel = new DataSyncTrackModel();
            dataSyncTrackModel.setSyncType(syncType);
            dataSyncTrackModel.setLastSyncTime(0L);
        }

        dataSyncTrackModel.setSyncStatus(ApplicationConstants.SYNC_IN_PROGRESS);
        storeOrUpdateDataSyncTrack(dataSyncTrackModel);

        return dataSyncTrackModel;
    }

    public void updateSyncDownloadStatus(Integer syncType) throws NhanceException {
        DataSyncTrackModel dataSyncTrackModel = getDataSyncTrackFromType(syncType);

        dataSyncTrackModel.setSyncStatus(syncType);
        dataSyncTrackModel.setLastSyncTime(new Date().getTime());

        storeOrUpdateDataSyncTrack(dataSyncTrackModel);
    }

    /**
     * Method gets Data Sync Status based on sync type
     *
     * @return String BrandName
     * @throws NhanceException
     */
    public DataSyncTrackModel getDataSyncTrackFromType(Integer syncType) throws NhanceException {
        DataSyncTrackModel dataSyncTrackModel = null;
        Cursor cursor = null;
        try {

            String guid = Application.getInstance().getUserProfileUserIdOrGuid();

            NhanceApplication.applicationDataBase.beginTransaction();

            String sqlQuery = "select * from " + DataSyncTrackTable.DATA_SYNC_TRACK_TABLE
                    + " where "
                    + DataSyncTrackTable.COLUMN_FK_USER_ID_OR_GUID + " ='" + guid
                    + "' and "
                    + DataSyncTrackTable.COLUMN_SYNC_TYPE + " = " + syncType;

            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            if (null != cursor && cursor.moveToFirst()) {
                dataSyncTrackModel = new DataSyncTrackModel();

                dataSyncTrackModel.setLastSyncTime(cursor.getLong((cursor.getColumnIndex(DataSyncTrackTable.COLUMN_LAST_SYNC_DATE))));
                dataSyncTrackModel.setSyncStatus(cursor.getInt((cursor.getColumnIndex(DataSyncTrackTable.COLUMN_SYNC_STATUS))));
                dataSyncTrackModel.setSyncType(cursor.getInt((cursor.getColumnIndex(DataSyncTrackTable.COLUMN_SYNC_TYPE))));
            }
        } catch (Exception e) {
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        } finally {
            if (null != cursor) cursor.close();
            cursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
        return dataSyncTrackModel;
    }

    /**
     * Method stores user's sync status to db
     *
     * @param dataSyncTrackModel
     * @throws NhanceException
     */
    public void storeOrUpdateDataSyncTrack(DataSyncTrackModel dataSyncTrackModel) throws NhanceException {
        if (dataSyncTrackModel != null) {

            String guid = Application.getInstance().getUserProfileUserIdOrGuid();

            ContentValues values = new ContentValues();
            values.put(DataSyncTrackTable.COLUMN_FK_USER_ID_OR_GUID, guid);
            values.put(DataSyncTrackTable.COLUMN_LAST_SYNC_DATE, dataSyncTrackModel.getLastSyncTime());
            values.put(DataSyncTrackTable.COLUMN_SYNC_STATUS, dataSyncTrackModel.getSyncStatus());
            values.put(DataSyncTrackTable.COLUMN_SYNC_TYPE, dataSyncTrackModel.getSyncType());

            if (valueDetailsExistsInTable(DataSyncTrackTable.DATA_SYNC_TRACK_TABLE, DataSyncTrackTable.COLUMN_FK_USER_ID_OR_GUID, guid,
                    DataSyncTrackTable.COLUMN_SYNC_TYPE, dataSyncTrackModel.getSyncType())) {
                NhanceApplication.applicationDataBase.update(DataSyncTrackTable.DATA_SYNC_TRACK_TABLE, values, DataSyncTrackTable.COLUMN_FK_USER_ID_OR_GUID + " = ? and "
                                + DataSyncTrackTable.COLUMN_SYNC_TYPE+" = ?",
                        new String[]{guid, String.valueOf(dataSyncTrackModel.getSyncType())});
            } else {
                NhanceApplication.applicationDataBase.insert(DataSyncTrackTable.DATA_SYNC_TRACK_TABLE, null, values);
            }
        }
    }

    public void storeOrUpdateAssignedServiceReqDetails(ServiceRequestModel serviceRequestModel) throws NhanceException {

        try {
            ContentValues invoiceValues = new ContentValues();

            if (serviceRequestModel.getUserId() != null && serviceRequestModel.getUserId().length() > 0) {
                invoiceValues.put(AssignedServiceRequestTable.COLUMN_USER_GUID, serviceRequestModel.getUserId());
            }

            if (serviceRequestModel.getGuid() != null && serviceRequestModel.getGuid().length() > 0) {
                invoiceValues.put(AssignedServiceRequestTable.COLUMN_GUID, serviceRequestModel.getGuid());
            }

            if (serviceRequestModel.getSrn() != null && serviceRequestModel.getSrn().length() > 0) {
                invoiceValues.put(AssignedServiceRequestTable.COLUMN_SER_REQ_NO, serviceRequestModel.getSrn());
            }

            if (serviceRequestModel.getSubject() != null && serviceRequestModel.getSubject().length() > 0) {
                invoiceValues.put(AssignedServiceRequestTable.COLUMN_SER_REQ_SUBJECT, serviceRequestModel.getSubject());
            }
            if (serviceRequestModel.getCreatedDate() != null && serviceRequestModel.getCreatedDate() != null)
                invoiceValues.put(AssignedServiceRequestTable.COLUMN_SER_REQ_CREATED_DATE, serviceRequestModel.getCreatedDate().getTime());

            if (serviceRequestModel.getUserName() != null && serviceRequestModel.getUserName().length() > 0) {
                invoiceValues.put(AssignedServiceRequestTable.COLUMN_USER_NAME, serviceRequestModel.getUserName());
            }

            ContactModel contactModel = serviceRequestModel.getContact();

            if(contactModel != null){
                if (contactModel.getIsdCode() != null && contactModel.getIsdCode() > 0) {
                    invoiceValues.put(AssignedServiceRequestTable.COLUMN_ISD_CODE, contactModel.getIsdCode());
                }
                if (contactModel.getMobile() != null && contactModel.getMobile().length() > 0) {
                    invoiceValues.put(AssignedServiceRequestTable.COLUMN_MOBILE_NUMBER, contactModel.getMobile());
                }
            }
            if(serviceRequestModel.getServiceLocations() != null && serviceRequestModel.getServiceLocations().size() > 0) {
                String address = JSONAdaptor.toJSON(serviceRequestModel.getServiceLocations().get(0));
                if (address != null && address.length() > 0) {
                    invoiceValues.put(AssignedServiceRequestTable.COLUMN_ADDRESS, address);
                }
            }

            String assignedSR = JSONAdaptor.toJSON(serviceRequestModel);
            if(assignedSR != null && assignedSR.length() > 0){
                invoiceValues.put(AssignedServiceRequestTable.COLUMN_SR_JSON, assignedSR);
            }

            invoiceValues.put(AssignedServiceRequestTable.COLUMN_PAYMENT_STATUS, Status.PENDING.getCode());

            if (serviceRequestModel.getGuid() != null && AssignedServiceRequestTable.isDataExists(AssignedServiceRequestTable.COLUMN_GUID, serviceRequestModel.getGuid())) {
                NhanceApplication.applicationDataBase.update(AssignedServiceRequestTable.ASSIGNED_SR_TABLE, invoiceValues, AssignedServiceRequestTable.COLUMN_GUID + " = ?",
                        new String[]{serviceRequestModel.getGuid()});
            } else {
                NhanceApplication.applicationDataBase.insert(AssignedServiceRequestTable.ASSIGNED_SR_TABLE, null, invoiceValues);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_SAVE_ERR);
        }
    }

    public void updateAssignedSRStatus(String guid, int paymentStatus) throws NhanceException {
        ContentValues values = null;
        values = new ContentValues();
        values.put(AssignedServiceRequestTable.COLUMN_PAYMENT_STATUS, paymentStatus);

        NhanceApplication.applicationDataBase.update(AssignedServiceRequestTable.ASSIGNED_SR_TABLE, values, AssignedServiceRequestTable.COLUMN_GUID + " = ?"
                , new String[]{guid});
    }

    private static final String TAG = CommonAction.class.getName();

    public List<ServiceRequestModel> getAssignedServiceRequest(String user_guid, int payment_status) throws NhanceException {

        List<ServiceRequestModel> serviceRequestModelList = null;

        LOG.d(TAG, "getAssignedServiceRequest");
        Cursor cursor = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + AssignedServiceRequestTable.ASSIGNED_SR_TABLE + " where " +
                    AssignedServiceRequestTable.COLUMN_USER_GUID + " = '" + user_guid + "' and " +
                    AssignedServiceRequestTable.COLUMN_PAYMENT_STATUS + " = " + payment_status
                    + " ORDER BY " + AssignedServiceRequestTable.COLUMN_SER_REQ_CREATED_DATE + " DESC";

            LOG.d(TAG, "getAssignedServiceRequest : query : " + sqlQuery);
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            if (null != cursor && cursor.moveToFirst()) {
                serviceRequestModelList = new ArrayList<>();
                do {
                    ServiceRequestModel serviceRequestModel = new ServiceRequestModel();

                    serviceRequestModel.setUserId(cursor.getString((cursor.getColumnIndex(AssignedServiceRequestTable.COLUMN_USER_GUID))));
                    serviceRequestModel.setGuid(cursor.getString((cursor.getColumnIndex(AssignedServiceRequestTable.COLUMN_GUID))));
                    serviceRequestModel.setSrn(cursor.getString((cursor.getColumnIndex(AssignedServiceRequestTable.COLUMN_SER_REQ_NO))));
                    serviceRequestModel.setSubject(cursor.getString((cursor.getColumnIndex(AssignedServiceRequestTable.COLUMN_SER_REQ_SUBJECT))));
                    serviceRequestModel.setCreatedDate(new Date(cursor.getLong(cursor.getColumnIndex(AssignedServiceRequestTable.COLUMN_SER_REQ_CREATED_DATE))));
                    serviceRequestModel.setUserName(cursor.getString((cursor.getColumnIndex(AssignedServiceRequestTable.COLUMN_USER_NAME))));

                    String mobileNumber = cursor.getString((cursor.getColumnIndex(AssignedServiceRequestTable.COLUMN_MOBILE_NUMBER)));
                    String isdCode = cursor.getString((cursor.getColumnIndex(AssignedServiceRequestTable.COLUMN_ISD_CODE)));
                    ContactModel contactModel = null;
                    if(mobileNumber != null){
                        contactModel = new ContactModel();
                        contactModel.setMobile(mobileNumber);
                    }
                    if(isdCode != null){
                        if(contactModel == null)
                            contactModel = new ContactModel();

                        contactModel.setIsdCode(Integer.parseInt(isdCode));
                    }
                    if(contactModel != null)
                        serviceRequestModel.setContact(contactModel);

                    String address = cursor.getString((cursor.getColumnIndex(AssignedServiceRequestTable.COLUMN_ADDRESS)));
                    if(address != null){
                        AddressModel addressModel = JSONAdaptor.fromJSON(address, AddressModel.class);
                        if(addressModel != null) {
                            List<AddressModel> addressModels = new ArrayList<>();
                            addressModels.add(addressModel);

                            serviceRequestModel.setServiceLocations(addressModels);
                        }
                    }

                    serviceRequestModelList.add(serviceRequestModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        } finally {
            if (null != cursor) cursor.close();
            cursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
        return serviceRequestModelList;
    }

    public ServiceRequestModel getAssignedServiceRequestDetails(String guid) throws NhanceException {
        ServiceRequestModel serviceRequestModel = null;
        LOG.d(TAG, "getAssignedServiceRequestDetails");
        Cursor cursor = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + AssignedServiceRequestTable.ASSIGNED_SR_TABLE + " where " +
                    AssignedServiceRequestTable.COLUMN_GUID + " = '" + guid + "'";

            LOG.d(TAG, "getAssignedServiceRequestDetails : query : " + sqlQuery);
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            if (null != cursor && cursor.moveToFirst()) {

                String assignedSR = cursor.getString((cursor.getColumnIndex(AssignedServiceRequestTable.COLUMN_SR_JSON)));
                if(assignedSR != null){
                    serviceRequestModel = JSONAdaptor.fromJSON(assignedSR, ServiceRequestModel.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        } finally {
            if (null != cursor) cursor.close();
            cursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
        return serviceRequestModel;
    }

    /**
     * Method to check whether given column value already exists in table
     *
     * @param tableName
     * @param columnName
     * @param columnValue
     * @return boolean
     * @throws NhanceException
     */
    public boolean valueDetailsExistsInTable(String tableName, String columnName, String columnValue) throws NhanceException {

        Cursor checkerCursor = null;
        boolean status = false;
        try {

            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + tableName + " where " +
                    columnName + " = '" + columnValue + "'";

            checkerCursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);
            status = null != checkerCursor && checkerCursor.moveToFirst();

        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        } finally {
            if (null != checkerCursor)
                checkerCursor.close();
            checkerCursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }

        return status;
    }

    /**
     * Method to check whether given column value already exists in table
     *
     * @param tableName
     * @param columnName
     * @param columnValue
     * @return boolean
     * @throws NhanceException
     */
    public boolean valueDetailsExistsInTable(String tableName, String columnName, int columnValue) throws NhanceException {

        Cursor checkerCursor = null;
        try {

            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + tableName + " where " +
                    columnName + " = '" + columnValue + "'";

            checkerCursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);
            return null != checkerCursor && checkerCursor.moveToFirst();

        } catch (Exception e) {
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        } finally {
            if (null != checkerCursor)
                checkerCursor.close();
            checkerCursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }

    public boolean valueDetailsExistsInTable(String tableName, String columnName, long columnValue) throws NhanceException {

        Cursor checkerCursor = null;
        try {

            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + tableName + " where " +
                    columnName + " = '" + columnValue + "'";

            checkerCursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);
            return null != checkerCursor && checkerCursor.moveToFirst();

        } catch (Exception e) {
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        } finally {
            if (null != checkerCursor)
                checkerCursor.close();
            checkerCursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }

    /**
     * Method to check whether given column value already exists in table
     *
     * @param tableName
     * @return boolean
     * @throws NhanceException
     */
    public boolean valueDetailsExistsInTable(String tableName, String columnName1, String columnValue1, String columnName2, String columnValue2) throws NhanceException {

        Cursor checkerCursor = null;
        try {

            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + tableName + " where " +
                    columnName1 + " = '" + columnValue1 + "' AND " + columnName2 + " = '" + columnValue2 + "'";

            checkerCursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);
            return null != checkerCursor && checkerCursor.moveToFirst();

        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        } finally {
            if (null != checkerCursor)
                checkerCursor.close();
            checkerCursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }

    /**
     * Method to check whether given column value already exists in table
     *
     * @param tableName
     * @return boolean
     * @throws NhanceException
     */
    public boolean valueDetailsExistsInTable(String tableName, String columnName1, String columnValue1, String columnName2, int columnValue2) throws NhanceException {

        Cursor checkerCursor = null;
        try {

            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + tableName + " where " +
                    columnName1 + " = '" + columnValue1 + "' AND " + columnName2 + " = " + columnValue2;

            checkerCursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);
            return null != checkerCursor && checkerCursor.moveToFirst();

        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_RETREIVAL_ERR);
        } finally {
            if (null != checkerCursor)
                checkerCursor.close();
            checkerCursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }
}
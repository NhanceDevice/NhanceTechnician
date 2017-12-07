package com.nhance.technician.datasets;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.ServiceRequestInvoiceDTO;
import com.nhance.technician.model.newapis.DocumentModel;
import com.nhance.technician.model.newapis.ServiceRequestInvoiceModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by afsarhussain on 11/07/17.
 */

public class GeneratedInvoiceTable {

    private static final String TAG = GeneratedInvoiceTable.class.getName();
    public static String COLUMN_ID = "id";
    public static String GENERATED_INVOICE_TABLE = "GeneratedInvoice";

    public static String COLUMN_SER_REQ_NO = "service_req_no";
    public static String COLUMN_SER_REQ_SUBJECT = "service_req_subject";
    public static String COLUMN_SER_REQ_CREATED_DATE = "service_req_created_date";
    public static String COLUMN_SER_REQ_STAGE = "ser_req_stage";
    public static String COLUMN_INVOICE_NUMBER = "invoice_number";
    public static String COLUMN_INVOICE_LINK = "invoice_link";
    public static String COLUMN_BASE_AMOUNT = "base_amount";
    public static String COLUMN_TOTAL_AMOUNT = "total_amount";
    public static String COLUMN_DISCOUNT_AMOUNT = "discount_amount";
    public static String COLUMN_TAX_AMOUNT = "tax_amount";
    public static String COLUMN_NET_PAYABLE_AMOUNT = "net_payable_amount";
    public static String COLUMN_USER_GUID = "user_id";
    public static String COLUMN_CUSTOMER_NAME = "customer_name";
    public static String COLUMN_CURRENCY_CODE = "currency_code";
    public static String COLUMN_INVOICE_GENERATION_DATE = "invoice_generation_date";
    public static String COLUMN_PAYMENT_STATUS = "payment_status";
    public static String COLUMN_ADDITIONAL_LABOUR_CHARGE = "additional_labour_charge";
    public static String COLUMN_PAYMENT_TYPE = "payment_type";



    private static final String GeneratedInvoiceTable_Create_Table = "CREATE TABLE IF NOT EXISTS " + GENERATED_INVOICE_TABLE
            + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SER_REQ_NO + " TEXT ,"
            + COLUMN_SER_REQ_SUBJECT + " TEXT ,"
            + COLUMN_SER_REQ_CREATED_DATE + " REAL ,"
            + COLUMN_SER_REQ_STAGE + " TEXT ,"
            + COLUMN_INVOICE_NUMBER + " TEXT ,"
            + COLUMN_INVOICE_LINK + " TEXT ,"
            + COLUMN_BASE_AMOUNT + " REAL ,"
            + COLUMN_ADDITIONAL_LABOUR_CHARGE+" REAL ,"
            + COLUMN_TOTAL_AMOUNT + " REAL ,"
            + COLUMN_DISCOUNT_AMOUNT + " REAL ,"
            + COLUMN_TAX_AMOUNT + " REAL ,"
            + COLUMN_NET_PAYABLE_AMOUNT + " REAL ,"
            + COLUMN_USER_GUID + " TEXT ,"
            + COLUMN_CUSTOMER_NAME + " TEXT ,"
            + COLUMN_CURRENCY_CODE + " TEXT, "
            + COLUMN_INVOICE_GENERATION_DATE + " REAL ,"
            + COLUMN_PAYMENT_STATUS + " INTEGER ,"
            + COLUMN_PAYMENT_TYPE   + " INTEGER "
            + " );";


    public static void createTables(SQLiteDatabase db) {
        db.execSQL(GeneratedInvoiceTable_Create_Table);
        Log.d(TAG, " created");
    }


    public static void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + GENERATED_INVOICE_TABLE);
    }


    public static void reset(SQLiteDatabase db) {
        dropTables(db);
        createTables(db);
    }


    public static boolean isDataExists(String columnName, String columnValue) throws NhanceException {

        Cursor cursor = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + GENERATED_INVOICE_TABLE + " where " + columnName + " = '" + columnValue + "'";
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            return null != cursor && cursor.moveToFirst();
        } finally {
            if (null != cursor) cursor.close();
            cursor = null;
            NhanceApplication.applicationDataBase.endTransaction();
        }
    }

    public void storeServiceReqInvoiceDetails(ServiceRequestInvoiceModel serviceRequestInvoiceDTO) throws NhanceException {

        try {
            ContentValues invoiceValues = new ContentValues();

            if (serviceRequestInvoiceDTO.getSrn() != null && serviceRequestInvoiceDTO.getSrn().length() > 0) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_SER_REQ_NO, serviceRequestInvoiceDTO.getSrn());
            }

            if (serviceRequestInvoiceDTO.getSubject() != null && serviceRequestInvoiceDTO.getSubject().length() > 0) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_SER_REQ_SUBJECT, serviceRequestInvoiceDTO.getSubject());
            }
            if (serviceRequestInvoiceDTO.getCreatedDate() != null && serviceRequestInvoiceDTO.getCreatedDate().getTime() > 0)
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_SER_REQ_CREATED_DATE, serviceRequestInvoiceDTO.getCreatedDate().getTime());

//            if (serviceRequestDTO.getStage() != null && serviceRequestDTO.getStage() > 0)
//                invoiceValues.put(GeneratedInvoiceTable.COLUMN_SER_REQ_STAGE, serviceRequestDTO.getStage());

            if (serviceRequestInvoiceDTO.getInvoiceNumber() != null && serviceRequestInvoiceDTO.getInvoiceNumber().length() > 0) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_INVOICE_NUMBER, serviceRequestInvoiceDTO.getInvoiceNumber());
            }

            if (serviceRequestInvoiceDTO.getDocument() != null && serviceRequestInvoiceDTO.getDocument().getDocumentPath() != null) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_INVOICE_LINK, serviceRequestInvoiceDTO.getDocument().getDocumentPath());
            }

            if (serviceRequestInvoiceDTO.getBaseAmount() != null) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_BASE_AMOUNT, serviceRequestInvoiceDTO.getBaseAmount());
            }
            if (serviceRequestInvoiceDTO.getLabourAmount() != null) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_ADDITIONAL_LABOUR_CHARGE, serviceRequestInvoiceDTO.getLabourAmount());
            }
            if (serviceRequestInvoiceDTO.getTotalAmount() != null) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_TOTAL_AMOUNT, serviceRequestInvoiceDTO.getTotalAmount());
            }
            if (serviceRequestInvoiceDTO.getDiscount() != null) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_DISCOUNT_AMOUNT, serviceRequestInvoiceDTO.getDiscount());
            }
            if (serviceRequestInvoiceDTO.getTaxAmount() != null) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_TAX_AMOUNT, serviceRequestInvoiceDTO.getTaxAmount());
            }
            if (serviceRequestInvoiceDTO.getNetPayableAmount() != null) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_NET_PAYABLE_AMOUNT, serviceRequestInvoiceDTO.getNetPayableAmount());
            }
            if (Application.getInstance().getUserProfileUserIdOrGuid() != null && Application.getInstance().getUserProfileUserIdOrGuid().length() > 0) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_USER_GUID, Application.getInstance().getUserProfileUserIdOrGuid());
            }
            if (serviceRequestInvoiceDTO.getUserName() != null && serviceRequestInvoiceDTO.getUserName().length() > 0) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_CUSTOMER_NAME, serviceRequestInvoiceDTO.getUserName());
            }
            if (serviceRequestInvoiceDTO.getCurrencyCode() != null && serviceRequestInvoiceDTO.getCurrencyCode().length() > 0) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_CURRENCY_CODE, serviceRequestInvoiceDTO.getCurrencyCode());
            }
            if(serviceRequestInvoiceDTO.getPaymentMode() !=null && serviceRequestInvoiceDTO.getPaymentMode()==ApplicationConstants.MODE_OF_PAYMENT_ONLINE) {
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_PAYMENT_STATUS, ApplicationConstants.PAYMENT_STATUS_PENDING_BILL_GENERATED);
            }else if(serviceRequestInvoiceDTO.getPaymentMode()!=null && serviceRequestInvoiceDTO.getPaymentMode()==ApplicationConstants.MODE_OF_PAYMENT_CASH){
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_PAYMENT_STATUS, ApplicationConstants.PAYMENT_STATUS_PAID);
            }
            invoiceValues.put(GeneratedInvoiceTable.COLUMN_PAYMENT_TYPE, serviceRequestInvoiceDTO.getPaymentMode());
            invoiceValues.put(GeneratedInvoiceTable.COLUMN_INVOICE_GENERATION_DATE, System.currentTimeMillis());

            if (serviceRequestInvoiceDTO.getInvoiceNumber() != null && isDataExists(GeneratedInvoiceTable.COLUMN_INVOICE_NUMBER, serviceRequestInvoiceDTO.getInvoiceNumber())) {
                NhanceApplication.applicationDataBase.update(GeneratedInvoiceTable.GENERATED_INVOICE_TABLE, invoiceValues, GeneratedInvoiceTable.COLUMN_INVOICE_NUMBER + " = ?",
                        new String[]{serviceRequestInvoiceDTO.getInvoiceNumber()});
            } else {
                NhanceApplication.applicationDataBase.insert(GeneratedInvoiceTable.GENERATED_INVOICE_TABLE, null, invoiceValues);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_SAVE_ERR);
        }

    }

    public static void updatePaymentStatus(String serviceReqNo, int paymentStatus) throws NhanceException {
        ContentValues values = null;
        values = new ContentValues();
        values.put(GeneratedInvoiceTable.COLUMN_PAYMENT_STATUS, paymentStatus);

        NhanceApplication.applicationDataBase.update(GENERATED_INVOICE_TABLE, values, COLUMN_SER_REQ_NO + " = ?", new String[]{serviceReqNo});
    }
    public static void updatePaymentStatus(String serviceReqNo,String invoiceNo, int paymentStatus) throws NhanceException {
        ContentValues values = null;
        values = new ContentValues();
        values.put(GeneratedInvoiceTable.COLUMN_PAYMENT_STATUS, paymentStatus);

        NhanceApplication.applicationDataBase.update(GENERATED_INVOICE_TABLE, values, COLUMN_SER_REQ_NO + " = ? and "
                + COLUMN_INVOICE_NUMBER + " = ?", new String[]{serviceReqNo,invoiceNo});
    }

    public static void clearAllServiceHistoryForUser(String userCode) throws NhanceException{
        try {
            NhanceApplication.applicationDataBase.delete(GENERATED_INVOICE_TABLE, COLUMN_USER_GUID + " = ?", new String[]{userCode});
        } catch (NhanceException e) {
            throw new NhanceException(NhanceException.DATABASE_DELETE_ERR);
        }
    }

    public static List<ServiceRequestInvoiceModel> getServiceRequestInvoices(String userCode) throws NhanceException {

        List<ServiceRequestInvoiceModel> serviceRequestInvoiceDTOList = null;

        LOG.d(TAG, "getServiceRequestInvoices");
        Cursor cursor = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + GENERATED_INVOICE_TABLE + " where " +
                    COLUMN_USER_GUID + " = '" + userCode + "'"
                    + " ORDER BY " + COLUMN_INVOICE_GENERATION_DATE + " DESC";

            LOG.d(TAG, "getServiceRequestInvoices : query : " + sqlQuery);
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            if (null != cursor && cursor.moveToFirst()) {
                serviceRequestInvoiceDTOList = new ArrayList<>();
                do {
                    ServiceRequestInvoiceModel serviceRequestInvoiceDTO = new ServiceRequestInvoiceModel();
                    serviceRequestInvoiceDTO.setSrn(cursor.getString((cursor.getColumnIndex(COLUMN_SER_REQ_NO))));
                    serviceRequestInvoiceDTO.setSubject(cursor.getString((cursor.getColumnIndex(COLUMN_SER_REQ_SUBJECT))));
                    serviceRequestInvoiceDTO.setInvoiceNumber(cursor.getString((cursor.getColumnIndex(COLUMN_INVOICE_NUMBER))));
                    String invoiceLink = cursor.getString((cursor.getColumnIndex(COLUMN_INVOICE_LINK)));
                    if(invoiceLink != null){
                        DocumentModel documentModel = new DocumentModel();
                        documentModel.setDocumentPath(invoiceLink);

                        serviceRequestInvoiceDTO.setDocument(documentModel);
                    }

                    serviceRequestInvoiceDTO.setBaseAmount(cursor.getDouble((cursor.getColumnIndex(COLUMN_BASE_AMOUNT))));
                    serviceRequestInvoiceDTO.setTotalAmount(cursor.getDouble((cursor.getColumnIndex(COLUMN_TOTAL_AMOUNT))));
                    serviceRequestInvoiceDTO.setLabourAmount(cursor.getDouble((cursor.getColumnIndex(COLUMN_ADDITIONAL_LABOUR_CHARGE))));
                    serviceRequestInvoiceDTO.setDiscount(cursor.getDouble((cursor.getColumnIndex(COLUMN_DISCOUNT_AMOUNT))));
                    serviceRequestInvoiceDTO.setTaxAmount(cursor.getDouble((cursor.getColumnIndex(COLUMN_TAX_AMOUNT))));
                    serviceRequestInvoiceDTO.setNetPayableAmount(cursor.getDouble((cursor.getColumnIndex(COLUMN_NET_PAYABLE_AMOUNT))));
                    serviceRequestInvoiceDTO.setUserId(cursor.getString((cursor.getColumnIndex(COLUMN_USER_GUID))));
                    serviceRequestInvoiceDTO.setCreatedDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_INVOICE_GENERATION_DATE))));
                    serviceRequestInvoiceDTO.setInvoiceStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_STATUS)));
                    serviceRequestInvoiceDTO.setCurrencyCode(cursor.getString(cursor.getColumnIndex(COLUMN_CURRENCY_CODE)));
                    serviceRequestInvoiceDTO.setPaymentMode(cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_TYPE)));
                    serviceRequestInvoiceDTOList.add(serviceRequestInvoiceDTO);

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
        return serviceRequestInvoiceDTOList;
    }
    public static List<ServiceRequestInvoiceDTO> getServiceRequestInvoices() throws NhanceException {

        List<ServiceRequestInvoiceDTO> serviceRequestInvoiceDTOList = null;

        LOG.d(TAG, "getServiceRequestInvoices");
        Cursor cursor = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select * from " + GENERATED_INVOICE_TABLE + " ORDER BY " + COLUMN_INVOICE_GENERATION_DATE + " DESC";

            LOG.d(TAG, "getServiceRequestInvoices : query : " + sqlQuery);
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            if (null != cursor && cursor.moveToFirst()) {
                serviceRequestInvoiceDTOList = new ArrayList<>();
                do {
                    ServiceRequestInvoiceDTO serviceRequestInvoiceDTO = new ServiceRequestInvoiceDTO();
                    serviceRequestInvoiceDTO.setServiceRequestNumber(cursor.getString((cursor.getColumnIndex(COLUMN_SER_REQ_NO))));
                    serviceRequestInvoiceDTO.setServiceRequestSubject(cursor.getString((cursor.getColumnIndex(COLUMN_SER_REQ_SUBJECT))));
                    serviceRequestInvoiceDTO.setInvoiceNumber(cursor.getString((cursor.getColumnIndex(COLUMN_INVOICE_NUMBER))));
                    serviceRequestInvoiceDTO.setInvoiceLink(cursor.getString((cursor.getColumnIndex(COLUMN_INVOICE_LINK))));
                    serviceRequestInvoiceDTO.setBaseAmount(cursor.getDouble((cursor.getColumnIndex(COLUMN_BASE_AMOUNT))));
                    serviceRequestInvoiceDTO.setTotalAmount(cursor.getDouble((cursor.getColumnIndex(COLUMN_TOTAL_AMOUNT))));
                    serviceRequestInvoiceDTO.setDiscount(cursor.getDouble((cursor.getColumnIndex(COLUMN_DISCOUNT_AMOUNT))));
                    serviceRequestInvoiceDTO.setTaxAmount(cursor.getDouble((cursor.getColumnIndex(COLUMN_TAX_AMOUNT))));
                    serviceRequestInvoiceDTO.setNetPaybleAmount(cursor.getDouble((cursor.getColumnIndex(COLUMN_NET_PAYABLE_AMOUNT))));
                    serviceRequestInvoiceDTO.setUserCode(cursor.getString((cursor.getColumnIndex(COLUMN_USER_GUID))));
                    serviceRequestInvoiceDTO.setInvoiceGenerationDate(cursor.getLong(cursor.getColumnIndex(COLUMN_INVOICE_GENERATION_DATE)));
                    serviceRequestInvoiceDTO.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_STATUS)));
                    serviceRequestInvoiceDTO.setCurrencyCode(cursor.getString(cursor.getColumnIndex(COLUMN_CURRENCY_CODE)));
                    serviceRequestInvoiceDTO.setModeOfPayment(cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_TYPE)));
                    serviceRequestInvoiceDTOList.add(serviceRequestInvoiceDTO);

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
        return serviceRequestInvoiceDTOList;
    }

    public static int getCountOfServiceRequestInvoices(String userCode) throws NhanceException {

        int countOfServReqInvs = 0;

        LOG.d(TAG, "getServiceRequestInvoices");
        Cursor cursor = null;
        try {
            NhanceApplication.applicationDataBase.beginTransaction();
            String sqlQuery = "select COUNT(*) as totalServReq from " + GENERATED_INVOICE_TABLE + " where " +
                    COLUMN_USER_GUID + " = '" + userCode + "'";

            LOG.d(TAG, "getServiceRequestInvoices : query : " + sqlQuery);
            cursor = NhanceApplication.applicationDataBase.getQueryResult(sqlQuery, null);

            if (null != cursor && cursor.moveToFirst()) {
                do {
                    countOfServReqInvs = cursor.getInt(cursor.getColumnIndex("totalServReq"));
                    LOG.d(TAG,"countOfServReqInvs : "+countOfServReqInvs);
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
        return countOfServReqInvs;
    }

    public void storeServiceReqInvoiceDetails(List<ServiceRequestInvoiceModel> serviceRequestInvoiceDTOList) throws NhanceException {

        try {
            for(ServiceRequestInvoiceModel serviceRequestInvoiceDTO : serviceRequestInvoiceDTOList) {
                ContentValues invoiceValues = new ContentValues();

                if (serviceRequestInvoiceDTO.getSrn() != null && serviceRequestInvoiceDTO.getSrn().length() > 0) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_SER_REQ_NO, serviceRequestInvoiceDTO.getSrn());
                }

                if (serviceRequestInvoiceDTO.getSubject() != null && serviceRequestInvoiceDTO.getSubject().length() > 0) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_SER_REQ_SUBJECT, serviceRequestInvoiceDTO.getSubject());
                }
                if (serviceRequestInvoiceDTO.getCreatedDate() != null )
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_SER_REQ_CREATED_DATE, serviceRequestInvoiceDTO.getCreatedDate().getTime());
                else
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_SER_REQ_CREATED_DATE, System.currentTimeMillis());

                if (serviceRequestInvoiceDTO.getInvoiceNumber() != null && serviceRequestInvoiceDTO.getInvoiceNumber().length() > 0) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_INVOICE_NUMBER, serviceRequestInvoiceDTO.getInvoiceNumber());
                }

                if (serviceRequestInvoiceDTO.getDocument() != null && serviceRequestInvoiceDTO.getDocument().getDocumentPath() != null) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_INVOICE_LINK, serviceRequestInvoiceDTO.getDocument().getDocumentPath());
                }

                if (serviceRequestInvoiceDTO.getBaseAmount() != null) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_BASE_AMOUNT, serviceRequestInvoiceDTO.getBaseAmount());
                }
                if (serviceRequestInvoiceDTO.getLabourAmount() != null) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_ADDITIONAL_LABOUR_CHARGE, serviceRequestInvoiceDTO.getLabourAmount());
                }
                if (serviceRequestInvoiceDTO.getTotalAmount() != null) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_TOTAL_AMOUNT, serviceRequestInvoiceDTO.getTotalAmount());
                }
                if (serviceRequestInvoiceDTO.getDiscount() != null) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_DISCOUNT_AMOUNT, serviceRequestInvoiceDTO.getDiscount());
                }
                if (serviceRequestInvoiceDTO.getTaxAmount() != null) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_TAX_AMOUNT, serviceRequestInvoiceDTO.getTaxAmount());
                }
                if (serviceRequestInvoiceDTO.getNetPayableAmount() != null) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_NET_PAYABLE_AMOUNT, serviceRequestInvoiceDTO.getNetPayableAmount());
                }
                if (serviceRequestInvoiceDTO.getUserId() != null && serviceRequestInvoiceDTO.getUserId().length() > 0) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_USER_GUID, serviceRequestInvoiceDTO.getUserId());
                }
                else{
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_USER_GUID, Application.getInstance().getUserProfileUserIdOrGuid());
                }

                if (serviceRequestInvoiceDTO.getCurrencyCode() != null && serviceRequestInvoiceDTO.getCurrencyCode().length() > 0) {
                    invoiceValues.put(GeneratedInvoiceTable.COLUMN_CURRENCY_CODE, serviceRequestInvoiceDTO.getCurrencyCode());
                }
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_PAYMENT_STATUS, serviceRequestInvoiceDTO.getInvoiceStatus());

                invoiceValues.put(GeneratedInvoiceTable.COLUMN_PAYMENT_TYPE, serviceRequestInvoiceDTO.getPaymentMode());
                invoiceValues.put(GeneratedInvoiceTable.COLUMN_INVOICE_GENERATION_DATE, serviceRequestInvoiceDTO.getCreatedDate().getTime());

                if (serviceRequestInvoiceDTO.getInvoiceNumber() != null && isDataExists(GeneratedInvoiceTable.COLUMN_INVOICE_NUMBER, serviceRequestInvoiceDTO.getInvoiceNumber())) {
                    NhanceApplication.applicationDataBase.update(GeneratedInvoiceTable.GENERATED_INVOICE_TABLE, invoiceValues, GeneratedInvoiceTable.COLUMN_INVOICE_NUMBER + " = ?",
                            new String[]{serviceRequestInvoiceDTO.getInvoiceNumber()});
                } else {
                    NhanceApplication.applicationDataBase.insert(GeneratedInvoiceTable.GENERATED_INVOICE_TABLE, null, invoiceValues);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new NhanceException(NhanceException.DATABASE_SAVE_ERR);
        }

    }

}

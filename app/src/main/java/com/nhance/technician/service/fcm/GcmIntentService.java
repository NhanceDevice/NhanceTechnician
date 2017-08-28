package com.nhance.technician.service.fcm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.datasets.GeneratedInvoiceTable;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.PushNotification;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.service.task.TaskShowNotification;
import com.nhance.technician.ui.action.CommonAction;
import com.nhance.technician.util.AccessPreferences;
import com.nhance.technician.util.Util;

import java.util.Map;


/**
 * Created by afsar on 11-05-2016.
 */
public class GcmIntentService extends FirebaseMessagingService implements ApplicationConstants {
    private final String TAG = GcmIntentService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage message) {
        String from = message.getFrom();
        String notificationStr = "";
        if (message.getData().size() > 0) {
            LOG.d(TAG, "======== Message data payload======: " + message.getData());
            Map data = message.getData();
            try {
                notificationStr = JSONAdaptor.toJSON(data);
            } catch (NhanceException e) {
                e.printStackTrace();
            }
        }
        if (message.getNotification() != null) {
            LOG.d(TAG, "======= Message Notification Body======: " + message.getNotification().getBody());
            String title = message.getNotification().getTitle();
            String body = message.getNotification().getBody();

        }
        try {
            if (notificationStr != null && (!(notificationStr.contains("mp_message") || notificationStr.contains("mp_campaign_id")))) {
                LOG.d(TAG, "GCM Message : " + notificationStr);
                PushNotification pushNotification = JSONAdaptor.fromJSON(notificationStr, PushNotification.class);
                String action = pushNotification.getAction();
                if (action != null && action.equalsIgnoreCase(ACTION_CODE_SERVICE_REQUEST_INVOICE_PAID)) {
                    String mobileNumber = Application.getInstance().getMobileNumber();
                    try {
                        if (mobileNumber == null) {
                            String loggedInUsersEmailOrMobileNo = AccessPreferences.get(NhanceApplication.getContext(), ApplicationConstants.LOGGED_IN_USER, "");
                            LOG.d(TAG, "loggedInUsersEmailOrMobileNo : " + loggedInUsersEmailOrMobileNo);

                            if (loggedInUsersEmailOrMobileNo != null && loggedInUsersEmailOrMobileNo.length() > 0) {
                                new CommonAction().loadBasicUserDeatilsToApplicationModel(loggedInUsersEmailOrMobileNo);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    updatePaymentStatus( pushNotification);
                    new TaskShowNotification(this, pushNotification).execute();
                    Intent intent = new Intent();
                    intent.setAction(ACTION_UPDATE_VIEW);
                    sendBroadcast(intent);
                }
            }
        } catch (NhanceException e) {
            e.printStackTrace();
        }
    }


    private void updatePaymentStatus(PushNotification pushNotification) throws NhanceException {
         new GeneratedInvoiceTable().updatePaymentStatus(pushNotification.getPropertyOne(),pushNotification.getPropertyFive(),PAYMENT_STATUS_PAID);

    }
}
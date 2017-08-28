package com.nhance.technician.service.fcm;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.nhance.technician.logger.LOG;

import java.io.IOException;


/**
 * Created by afsar on 11-05-2016.
 */
public class GcmIDListenerService extends FirebaseInstanceIdService {

    public static final String TAG = GcmIDListenerService.class.getName();

    @Override
    public void onTokenRefresh() {
        LOG.d(TAG, "Going to call registration intent service");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }

    public void unregisterGCMToken(Context context) {
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

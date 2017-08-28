package com.nhance.technician.service.task;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.MixpanelConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.PushNotification;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.ui.LoginActivity;
import com.nhance.technician.util.AccessPreferences;
import com.nhance.technician.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by afsarhussain on 27/06/16.
 */
public class TaskShowNotification extends AsyncTask<String, Void, Bitmap> implements ApplicationConstants {


    private static final String TAG = TaskShowNotification.class.getName();
    private Context mContext;
    private PushNotification pushNotification;

    public TaskShowNotification(Context context, PushNotification pushNotification) {
        super();
        this.mContext = context;
        this.pushNotification = pushNotification;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        InputStream in;
        if (this.pushNotification.getImage() != null) {
            try {
                URL url = new URL(this.pushNotification.getImage());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        Date now = new Date();
        String notificationReceivedTime = simpleDateFormat.format(now);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_stat_ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentTitle(pushNotification.getTitle())
                .setContentText(pushNotification.getMessage())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));

        if (result != null) {
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result));
        }

        Intent intent = null;
        intent = new Intent(mContext, LoginActivity.class);
        if (intent != null) {
            int notificationId = Util.generateRandom();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            notificationBuilder.setContentIntent(pendingIntent);


            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, notificationBuilder.build());
        }
    }

    public boolean isAppOpenedAndVisible() {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        return appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND || appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
    }
}
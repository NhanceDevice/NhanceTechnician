package com.nhance.technician.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.nhance.technician.logger.LOG;

/**
 * Created by RAHUL on 16-08-2016.
 */
public class ApplicationLifecycleHandler implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    private static final String TAG = ApplicationLifecycleHandler.class.getSimpleName();
    private static boolean isInBackground = true;
    private static int stateCounter;
    public static Activity mActivity;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        stateCounter = 0;
    }

    @Override
    public void onActivityStarted(final Activity activity) {
        Log.v("qw1", "" + stateCounter);
        stateCounter++;
        Log.v("qw2", "" + stateCounter);
        if (isInBackground && stateCounter < 2) {
            LOG.d(TAG, "app went to foreground");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendTaskActiveStatus(activity);
                }
            }, 500);
            // Toast.makeText(activity, "I'm alive", Toast.LENGTH_SHORT).show();
            isInBackground = false;
        }
    }

    @Override
    public void onActivityResumed(final Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(final Activity activity) {
        Log.v("qw3", "" + stateCounter);
        stateCounter--;
        Log.v("qw4", "" + stateCounter);
        if (isInBackground == false && stateCounter < 0) {
            // Toast.makeText(activity, "I'm dead", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendTaskActiveStatus(activity);
                }
            }, 800);
        }
    }

    private void sendTaskActiveStatus(Activity activity) {
        Intent intent = new Intent();
        intent.setAction("com.nhance.technician.TASK_INTENT");
        activity.sendBroadcast(intent);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public void onTrimMemory(int i) {
        if (i == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            LOG.d(TAG, "app went to background");
            isInBackground = true;
        }
    }
}

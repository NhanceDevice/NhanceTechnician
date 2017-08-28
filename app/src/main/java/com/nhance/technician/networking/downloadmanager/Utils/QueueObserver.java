package com.nhance.technician.networking.downloadmanager.Utils;

/**
 * Created by Rahul on 6/8/2017.
 */
public interface QueueObserver {

    void wakeUp(int taskID);
}

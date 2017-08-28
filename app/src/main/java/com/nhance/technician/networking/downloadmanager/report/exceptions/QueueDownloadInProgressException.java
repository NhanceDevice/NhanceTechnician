package com.nhance.technician.networking.downloadmanager.report.exceptions;

/**
 * Created by Rahul on 6/8/2017.
 */
public class QueueDownloadInProgressException extends IllegalAccessException {

    public QueueDownloadInProgressException(){
        super("queue download is already in progress");
    }
}

package com.nhance.technician.networking.downloadmanager.report.exceptions;

/**
 * Created by Rahul on 6/8/2017.
 */
public class QueueDownloadNotStartedException extends IllegalStateException {

    public QueueDownloadNotStartedException(){
        super("Queue Download not started yet");
    }
}

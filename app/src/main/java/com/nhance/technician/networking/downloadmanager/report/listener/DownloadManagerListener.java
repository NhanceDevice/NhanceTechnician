package com.nhance.technician.networking.downloadmanager.report.listener;

/**
 * Created by Rahul on 6/8/2017.
 */
public interface DownloadManagerListener {

    void OnDownloadStarted(long taskId);

    void OnDownloadPaused(long taskId);

    void onDownloadProcess(long taskId, double percent, long downloadedLength);

    void OnDownloadFinished(long taskId);

    void OnDownloadRebuildStart(long taskId);

    void OnDownloadRebuildFinished(long taskId);

    void OnDownloadCompleted(long taskId);
    
    void connectionLost(long taskId);

}

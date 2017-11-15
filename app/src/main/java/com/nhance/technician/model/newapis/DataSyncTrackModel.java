package com.nhance.technician.model.newapis;

/**
 * Created by Javeed on 10/17/2017.
 */

public class DataSyncTrackModel extends MessageModel {

    private Integer syncStatus;

    private Integer syncType;

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Integer getSyncType() {
        return syncType;
    }

    public void setSyncType(Integer syncType) {
        this.syncType = syncType;
    }

    @Override
    public String toString() {
        return "DataSyncTrackModel{" +
                "lastSyncDateTime=" + getLastSyncTime() +
                ", syncStatus=" + syncStatus +
                ", syncType=" + syncType +
                '}';
    }
}

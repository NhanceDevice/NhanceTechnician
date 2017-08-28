package com.nhance.technician.model;

/**
 * Created by afsarhussain on 28/06/16.
 */
public class Notification extends BaseModel {

    private int notificationType;

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }
}

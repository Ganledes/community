package com.carl.community.enums;

public enum NotificationStatus {

    UNREAD(0),
    READ(1);

    private int status;

    NotificationStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

package com.carl.community.enums;

import com.carl.community.exception.CustomizeException;
import com.carl.community.exception.ErrorMessage;

public enum NotificationType {

    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2, "回复了评论")
    ;

    private int type;
    private String action;

    NotificationType(int type, String action) {
        this.type = type;
        this.action = action;
    }

    public int getType() {
        return type;
    }

    public String getAction() {
        return action;
    }

    public static String getActionByType(int type) {
        for (NotificationType notificationType : NotificationType.values()) {
            if (notificationType.type == type) {
                return notificationType.action;
            }
        }
        throw new CustomizeException(ErrorMessage.SERVER_ERROR);
    }
}

package com.carl.community.enums;

/**
 * @author zhaoq
 */

public enum CommentType {
    /**
     *
     */
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentType(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer code) {
        for (CommentType value : CommentType.values()) {
            if (value.getType().equals(code)) {
                return true;
            }
        }
        return false;
    }


    public Integer getType() {
        return type;
    }
}

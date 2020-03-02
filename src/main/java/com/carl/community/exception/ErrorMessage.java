package com.carl.community.exception;

/**
 * @author zhaoq
 */

public enum ErrorMessage {

    /**
     *
     */
    QUESTION_NOT_FOUND("您要找的问题不存在或已删除"),
    SERVER_ERROR("服务器开小差了，请稍后再试吧"),
    CLIENT_ERROR("您发送的请求有问题，请重新再试");


    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

package com.carl.community.exception;

/**
 * @author zhaoq
 */

public enum ErrorMessage {

    /**
     *
     */
    SERVER_ERROR(1001, "服务器开小差了，请稍后再试吧"),
    NOT_LOGIN(1002, " 用户未登录，请登录后重试"),
    QUESTION_NOT_FOUND(1003, "您请求的问题不存在或已删除"),
    CLIENT_ERROR(1004, "您发送的请求有问题，请重新再试"),
    COMMENT_TYPE_WRONG(1005, "您请求的评论不存在"),
    COMMENT_TARGET_NOT_FOUND(1006, "你要评论的问题或回复不存在"),
    TARGET_PARAM_NOT_FOUND(1007, "未选择任何问题或评论"),
    UPDATE_FAILED(1008, "更新失败");


    private Integer errorCode;
    private String message;

    public Integer getErrorCode() {
        return errorCode;
    }

    ErrorMessage(Integer errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}

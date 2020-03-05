package com.carl.community.exception;

/**
 * @author zhaoq
 * @date 2020/3/2 23:41
 */
public class CustomizeException extends RuntimeException {

    private ErrorMessage errorMessage;

    public CustomizeException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}

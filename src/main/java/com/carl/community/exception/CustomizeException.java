package com.carl.community.exception;

/**
 * @author zhaoq
 * @date 2020/3/2 23:41
 */
public class CustomizeException extends RuntimeException {

    public CustomizeException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

}

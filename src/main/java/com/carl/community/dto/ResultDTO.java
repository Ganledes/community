package com.carl.community.dto;

import com.carl.community.exception.CustomizeException;
import com.carl.community.exception.ErrorMessage;
import lombok.Data;

/**
 * @author zhaoq
 * @date 2020/3/4 13:41
 */
@Data
public class ResultDTO<T> {

    private Integer code;
    private String message;
    private T data;

    public static <T> ResultDTO<T> ok() {
        return ok(null);
    }

    public static <T> ResultDTO<T> ok(T t) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(2000);
        resultDTO.setMessage("成功");
        if (t != null) {
            resultDTO.setData(t);
        }
        return resultDTO;
    }

    public static <T> ResultDTO<T> errorOf(Integer code, String message) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static <T> ResultDTO<T> errorOf(CustomizeException e) {
        ErrorMessage errorMessage = e.getErrorMessage();
        return errorOf(errorMessage.getErrorCode(), errorMessage.getMessage());
    }

}

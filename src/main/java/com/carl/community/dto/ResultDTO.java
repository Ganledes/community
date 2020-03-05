package com.carl.community.dto;

import com.carl.community.exception.CustomizeException;
import com.carl.community.exception.ErrorMessage;
import lombok.Data;

/**
 * @author zhaoq
 * @date 2020/3/4 13:41
 */
@Data
public class ResultDTO {

    private Integer code;
    private String message;

    public static ResultDTO ok() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(2000);
        resultDTO.setMessage("成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException e) {
        ErrorMessage errorMessage = e.getErrorMessage();
        return errorOf(errorMessage.getErrorCode(), errorMessage.getMessage());
    }
}

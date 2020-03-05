package com.carl.community.advice;

import com.alibaba.fastjson.JSON;
import com.carl.community.dto.ResultDTO;
import com.carl.community.exception.CustomizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhaoq
 * @date 2020/3/4 17:21
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CustomizeException.class)
    public String handleCustomizeException(CustomizeException e) {
        return JSON.toJSONString(ResultDTO.errorOf(e));
    }

}
